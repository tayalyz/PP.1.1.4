package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("N1", "LN1", (byte) 126);
        userService.saveUser("N2", "LN2", (byte) 3);
        userService.saveUser("N3", "LN3", (byte) 3);
        userService.saveUser("N3", "LN4", (byte) 2);

        userService.getAllUsers();
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
