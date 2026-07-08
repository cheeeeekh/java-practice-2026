package ru.itis.shop.app;

import ru.itis.shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.jdbc.UserRepositoryJdbcImpl;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/users_db";
        String username = "postgres";
        String password = "12345";
        String tableName = "users_with_names";

        DataSource dataSource = new DriverManagerDataSource(url, username, password, tableName);

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource, tableName);
        UserService userService = new UserService(userRepository);
        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
