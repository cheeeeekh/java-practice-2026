package ru.itis.shop.app;

import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.UserRepositoryJdbcImpl;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Параметры подключения к базе данных - измените под свои
        String url = "jdbc:postgresql://localhost:5432/users_db";
        String username = "postgres";
        String password = "12345";
        String tableName = "users";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            UserRepository userRepository = new UserRepositoryJdbcImpl(connection, tableName);
            UserService userService = new UserService(userRepository);
            UserConsoleOperations operations = new UserConsoleOperations(userService);

            while (true) {
                operations.showMenu();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных!");
            e.printStackTrace();
        }
    }
}
