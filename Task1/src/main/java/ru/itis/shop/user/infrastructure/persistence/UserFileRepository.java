package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    public UserFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(user.getId() + "|" +
                    user.getEmail() + "|" +
                    user.getPassword() + "|" +
                    user.getProfileDescription());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User findById(String id) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] currentUser = scanner.nextLine().split("\\|");
                if (currentUser.length == 4 && currentUser[0].equals(id)) {
                    return new User(currentUser[0], currentUser[1], currentUser[2], currentUser[3]);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден!");
        }
        return null;
    }
}
