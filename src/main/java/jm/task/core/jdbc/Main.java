package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Мурзик", "РадикОвич", (byte) 12);
        user.saveUser("Богдан", "Какишев", (byte) 45);
        user.saveUser("Феофан", "Кустов", (byte) 74);
        user.saveUser("Андрюша", "Покатаев", (byte) 43);
        System.out.println(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
