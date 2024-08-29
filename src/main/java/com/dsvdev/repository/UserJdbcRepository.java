package com.dsvdev.repository;

import com.dsvdev.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.dsvdev.config.Util.connection;

public class UserJdbcRepository implements UserRepository {


    @Override
    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();

            String query = "CREATE TABLE USERS" +
                    "(id bigint GENERATED ALWAYS AS IDENTITY NOT NULL," +
                    " name varchar NOT NULL, " +
                    " lastname varchar NOT NULL, " +
                    " age smallint NOT NULL, " +
                    " CONSTRAINT users_pk PRIMARY KEY (id))";

            statement.executeUpdate(query);
        } catch (SQLException e) {

        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();

            String query = "DROP TABLE USERS";

            statement.executeUpdate(query);
        } catch (SQLException e) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO USERS(name,lastname,age) VALUES ('" + name + "','" + lastName + "'," + age + ")";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("User с именем — " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM USERS WHERE id = " + id;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM USERS";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                System.out.println(user.toString());
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();

            String query = "TRUNCATE USERS";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
