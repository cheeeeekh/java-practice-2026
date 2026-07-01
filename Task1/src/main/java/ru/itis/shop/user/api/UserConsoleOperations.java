package ru.itis.shop.user.api;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.Scanner;

public class UserConsoleOperations {

    private final UserRepository userRepository;
    private final Scanner scanner;

    public UserConsoleOperations(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n1. Регистрация пользователя.");
        System.out.println("2. Вход в систему.");
        System.out.println("3. Найти пользователя по id.");
        System.out.println("0. Выход.\n");

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                System.out.println("\nСейчас будем регистрировать пользователя.");
                System.out.print("Введите email:");
                String email = scanner.nextLine();
                System.out.print("Введите password:");
                String password = scanner.nextLine();
                System.out.print("Введите описание профиля:");
                String profileDescription = scanner.nextLine();
                User user = new User(email, password, profileDescription);
                userRepository.save(user);
            }
            break;
            case "2": {
                System.out.println("\nВы можете войти в приложение!");
            }
            break;
            case "3": {
                System.out.print("\nВведите id: ");
                String id = scanner.nextLine();
                User user = userRepository.findById(id);
                if (user != null) {
                    System.out.println("Ниже представлены данные пользователя с указанным id.");
                    user.print();
                } else {
                    System.out.println("Пользователя с указанным id не существует!");
                }

            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }
}
