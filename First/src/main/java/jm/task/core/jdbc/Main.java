package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Oleg", "Shmatko", (byte) 25);
        userService.saveUser("Zhenya", "Fedorov", (byte) 31);
        userService.saveUser("Igor", "Denisov", (byte) 38);

//        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.getAllUsers();
        userService.dropUsersTable();
    }
}
