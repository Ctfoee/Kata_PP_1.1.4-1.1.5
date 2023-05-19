package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private long userId = 0;

    Connection connection;

    {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = """
                    CREATE TABLE IF NOT EXISTS `Users`.`Users` (
                      `id` BIGINT AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` TINYINT NOT NULL,
                      PRIMARY KEY (`id`),
                      UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException ignore) {}
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO Users(id, name, lastName, age) VALUES(?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            userId++;
            statement.setLong(1, userId);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setByte(4, age);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM Users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            List<User> userList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE Users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
