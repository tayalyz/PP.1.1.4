package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
             connection.setAutoCommit(false);
             statement.executeUpdate("""
                     CREATE TABLE IF NOT EXISTS userS (
                       id INT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(50) NULL,
                       lastName VARCHAR(50) NULL,
                       age TINYINT CHECK (age>0 and age<128) NULL,
                       PRIMARY KEY (id))""");
             connection.commit();
        } catch (SQLException e){
            getConnection().rollback();
            e.printStackTrace();
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
             connection.setAutoCommit(false);
             statement.executeUpdate("DROP TABLE IF EXISTS userS");
             connection.commit();
        } catch (SQLException e) {
            getConnection().rollback();
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
             connection.setAutoCommit(false);
             statement.execute(String.format("INSERT INTO userS (name,lastName,age) " +
                    "VALUES ('%s', '%s', '%d')", name, lastName, age));
             connection.commit();
             System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            getConnection().rollback();
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
             connection.setAutoCommit(false);
             statement.executeUpdate(String.format("DELETE FROM userS WHERE id=%d", id));
             connection.commit();
        } catch (SQLException e) {
            getConnection().rollback();
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userS = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
             connection.setAutoCommit(false);
             ResultSet resultSet = statement.executeQuery("SELECT * FROM userS");
             while (resultSet.next()) {
                 User user = new User();
                 user.setId((long) resultSet.getInt("id"));
                 user.setName(resultSet.getString("name"));
                 user.setLastName(resultSet.getString("lastName"));
                 user.setAge((byte) resultSet.getInt("age"));

                 userS.add(user);
             }
             System.out.println(userS);
             connection.commit();
        } catch (SQLException e) {
            getConnection().rollback();
            e.printStackTrace();
        }
        return userS;
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
             connection.setAutoCommit(false);
             statement.executeUpdate("TRUNCATE TABLE userS");
             connection.commit();
        } catch (SQLException e) {
            getConnection().rollback();
            e.printStackTrace();
        }
    }
}
