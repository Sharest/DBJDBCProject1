package com.dsvdev;

import com.dsvdev.repository.UserJdbcRepository;

public class Main {
    public static void main(String[] args) {
        UserJdbcRepository userRepository = new UserJdbcRepository();

        userRepository.createUsersTable();

        userRepository.saveUser("Dmitrii_One", "Test1", (byte) 19);
        userRepository.saveUser("Dmitrii_Two", "Test2", (byte) 50);
        userRepository.saveUser("Dmitrii_Three", "Test3", (byte) 100);
        userRepository.saveUser("Dmitrii_Four", "Test4", (byte) 1);

        userRepository.getAllUsers();

        userRepository.cleanUsersTable();

        userRepository.dropUsersTable();
    }
}