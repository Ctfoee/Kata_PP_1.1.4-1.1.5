package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        user.dropUsersTable();
//        user.createUsersTable();
//        user.saveUser("Мурзик", "РадикОвич", (byte) 12);
//        List<User> userList = user.getAllUsers();
//        user.saveUser("Богдан", "Какишев", (byte) 45);
//        user.saveUser("Феофан", "Кустов", (byte) 74);
//        System.out.println(userList);
//        System.out.println(userList.size());
    }
}
