package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session = Util.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = session.beginTransaction();
        String query = """
                    CREATE TABLE IF NOT EXISTS `Users`.`Users` (
                      `id` BIGINT AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` TINYINT NOT NULL,
                      PRIMARY KEY (`id`),
                      UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""";
        session.createSQLQuery(query).executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = session.beginTransaction();
        String query = "DROP TABLE IF EXISTS Users";
        session.createSQLQuery(query).executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        session.save(user);
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = session.beginTransaction();
        User user = session.load(User.class, id);
        try {
            session.delete(user);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT user FROM User user";
        return session.createQuery(query, User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("truncate table Users").executeUpdate();
        transaction.commit();
    }

}
