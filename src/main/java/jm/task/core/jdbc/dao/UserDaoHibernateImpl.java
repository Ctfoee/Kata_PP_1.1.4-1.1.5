package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
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
            } catch (Exception ex) {
                System.out.println("Произошло исключение при создании таблицы: " + ex.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String query = "DROP TABLE IF EXISTS Users";
                session.createSQLQuery(query).executeUpdate();
                transaction.commit();
            } catch (Exception ex) {
                System.out.println("Произошло исключение при удалении таблицы: " + ex.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = new User();
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                session.save(user);
                transaction.commit();
            } catch (Exception ex) {
                System.out.println("Произошло исключение при добавлении пользователя: " + ex.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.load(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (Exception ex) {
                System.out.println("Произошло исключение при удалении пользователя: " + ex.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String query = "SELECT user FROM User user";
                userList = session.createQuery(query, User.class).getResultList();
                transaction.commit();
            } catch (Exception ex) {
                System.out.println("Произошло исключение при получении списка пользователей: " + ex.getMessage());
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("truncate table Users").executeUpdate();
                transaction.commit();
            } catch (Exception ex) {
                System.out.println("Произошло исключение при очистке таблицы: " + ex.getMessage());
                transaction.rollback();
            }
        }
    }

}
