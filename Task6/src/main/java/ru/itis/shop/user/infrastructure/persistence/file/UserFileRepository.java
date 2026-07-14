package ru.itis.shop.user.infrastructure.persistence.file;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.infrastructure.persistence.file.mapper.UserMapper;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.*;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            int newId = generateNewId();
            user.setId(newId);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line = reader.readLine();

            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }

                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                User user = userMapper.fromLine(scanner.nextLine());
                if (user.getId().equals(id)) {
                    return Optional.of(user);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден!");
        }
        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        try {
            List<String> lines = new ArrayList<>();
            boolean updated = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = reader.readLine();

                while (line != null) {
                    User existingUser = userMapper.fromLine(line);

                    if (existingUser.getId().equals(user.getId())) {
                        lines.add(userMapper.toLine(user));
                        updated = true;
                    } else {
                        lines.add(line);
                    }

                    line = reader.readLine();
                }
            }

            if (!updated) {
                return false;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            return true;

        } catch (IOException e) {
            throw new IllegalStateException("Ошибка при обновлении пользователя " + user.getId() + "!", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();

            while (line != null) {
                User user = userMapper.fromLine(line);
                users.add(user);
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            // Если файл не существует, возвращаем пустой список
            System.err.println("Файл пользователей не найден: " + fileName + "!");
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка при чтении файла пользователей!", e);
        }

        return users;
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        System.out.println("Метод findAllByProfileDescription() не реализован в файловой версии. Используйте JDBC версию.");
        return List.of();
    }

    private int generateNewId() {
        List<User> allUsers = findAll();
        int maxId = allUsers.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }
}
