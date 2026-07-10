package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
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
        String sql = "insert into " + dbTable + " (name, email, password, profile_description) values (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getProfileDescription());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Пользователь успешно зарегистрирован!");
            } else {
                System.err.println("Не удалось зарегистрировать пользователя!");
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при сохранении пользователя: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from " + dbTable + " where email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userRowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при поиске пользователя по email: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "select * from " + dbTable + " where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userRowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при поиске пользователя по id: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        String sql = "update " + dbTable + " set name = ?, email = ?, password = ?, profile_description = ? " +
                "where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getProfileDescription());
            preparedStatement.setInt(5, user.getId());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при обновлении пользователя: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from " + dbTable;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                users.add(userRowMapper.mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при получении всех пользователей: " + e.getMessage(), e);
        }

        return users;
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        List<User> users = new ArrayList<>();
        String sql = "select * from " + dbTable + " where profile_description = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, profileDescription);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(userRowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при поиске пользователей по описанию профиля: " + e.getMessage(), e);
        }

        return users;
    }
}
