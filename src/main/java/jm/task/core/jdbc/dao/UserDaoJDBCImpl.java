package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             statement.executeUpdate("""
                     CREATE TABLE IF NOT EXISTS userS (
                       id INT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(50) NULL,
                       lastName VARCHAR(50) NULL,
                       age INT(3) NULL,
                       PRIMARY KEY (id))""");
             connection.commit();
             connection.rollback();
        } catch (SQLException e){
             e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             statement.executeUpdate("DROP TABLE IF EXISTS userS");
             connection.commit();
             connection.rollback();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             statement.execute(String.format("INSERT INTO userS (name,lastName,age) " +
                    "VALUES ('%s', '%s', '%d')", name, lastName, age));
             connection.commit();
             connection.rollback();
             System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM userS WHERE id=%d", id));
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userS = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery("SELECT * FROM userS");
             while (resultSet.next()) {
                 User user = new User();
                 user.setId((long) resultSet.getInt("id"));
                 user.setName(resultSet.getString("name"));
                 user.setLastName(resultSet.getString("lastName"));
                 user.setAge((byte) resultSet.getInt("age"));

                 userS.add(user);
             }
             connection.commit();
             connection.rollback();
        } catch (SQLException e) {
             throw new RuntimeException(e);
        }
        return userS;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE userS");
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
