package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final Connection connection;
    private final String tableName;

    public UserRepositoryJdbcImpl(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    @Override
    public void save(User user) {
        System.out.println("Метод save() не реализован в JDBC версии. Используйте файловую версию.");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println("Метод findByEmail() не реализован в JDBC версии. Используйте файловую версию.");
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        System.out.println("Метод findById() не реализован в JDBC версии. Используйте файловую версию.");
        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        System.out.println("Метод update() не реализован в JDBC версии. Используйте файловую версию.");
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from " + tableName;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("profile_description")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех пользователей из базы данных!", e);
        }

        return users;
    }
}