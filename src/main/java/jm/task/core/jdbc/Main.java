package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {

        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("N1", "LN1", (byte) 5);
        userDao.saveUser("N2", "LN2", (byte) 3);
        userDao.saveUser("N3", "LN3", (byte) 3);
        userDao.saveUser("N4", "LN4", (byte) 2);

        userDao.removeUserById(1);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();


    }
}
