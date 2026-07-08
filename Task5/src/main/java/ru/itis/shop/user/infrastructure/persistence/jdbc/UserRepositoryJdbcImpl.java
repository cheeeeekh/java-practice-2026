package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;
    public final String dbTable;

    private final RowMapper<User> userRowMapper = row -> new User(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("password"),
            row.getString("profileDescription")
    );

    public UserRepositoryJdbcImpl(DataSource dataSource, String dbTable) {
        this.dataSource = dataSource;
        this.dbTable = dbTable;
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
    public Optional<User> findById(Integer id) {
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
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from account")) {
                    while (resultSet.next()) {
                        users.add(userRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return users;
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String query = "select * from " + dbTable + " where profile_description = '" + profileDescription + "'";

                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        users.add(userRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при поиске пользователей по profileDescription: " + e.getMessage(), e);
        }
        return users;
    }
}
