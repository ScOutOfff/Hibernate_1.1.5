package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;


public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(40), " +
                "lastname VARCHAR(40), " +
                "age TINYINT)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO user (name, lastName, age) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            System.err.println("Adding error!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error at deleting. Incorrect id!");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT id, name, lastname, age FROM user";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }

            System.out.println(userList);
        } catch (SQLException e) {
            System.err.println("Getting error!");
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Cleaning Error!");
            e.printStackTrace();
        }
    }
}
