package ru.itis.shop.user.api;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findById();
            }
            break;
            case "4": {
                updateProfileDescription();
            }
            break;
            case "5": {
                showAllUsers();
            }
            break;
            case "6": {
                findByProfileDescription();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("\n1. Регистрация пользователя.");
        System.out.println("2. Вход в систему.");
        System.out.println("3. Найти пользователя по id.");
        System.out.println("4. Обновить данные пользователя.");
        System.out.println("5. Показать всех пользователей.");
        System.out.println("6. Показать информацию о пользователях с заданным описанием профиля.");
        System.out.println("0. Выход.\n");
    }

    private void signUp() {
        System.out.println("\nСейчас будем регистрировать пользователя...");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите password: ");
        String password = scanner.nextLine();
        System.out.print("Введите описание профиля: ");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);
    }


    private void signIn() {
        System.out.println("\nВы можете войти в приложение...");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите password: ");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение!");
        } else {
            System.out.println("Email или пароль не верны!");
        }
    }

    private void findById() {
        System.out.print("\nВведите id: ");
        String id = scanner.nextLine();
        Optional<UserDto> userDto = userService.findById(id);
        if (userDto.isPresent()) {
            System.out.println("Ниже представлены данные пользователя с указанным id.");
            userDto.get().print();
        } else {
            System.out.println("Пользователя с указанным id не существует!");
        }
    }

    private void updateProfileDescription() {
        System.out.println("\nОбновление данных пользователя...");
        System.out.print("Введите email пользователя, данные которого хотите обновить: ");
        String email = scanner.nextLine();

        System.out.print("Введите новое описание профиля: ");
        String newProfileDescription = scanner.nextLine();

        boolean updated = userService.updateProfileDescription(email, newProfileDescription);

        if (updated) {
            System.out.println("Данные пользователя успешно обновлены!");
        } else {
            System.out.println("Пользователь с email '" + email + "' не найден!");
        }
    }

    private void showAllUsers() {
        System.out.println("\n=== СПИСОК ВСЕХ ПОЛЬЗОВАТЕЛЕЙ ===");
        List<UserDto> usersDto = userService.findAll();

        if (usersDto.isEmpty()) {
            System.out.println("В базе данных нет пользователей.");
            return;
        }

        System.out.println("Всего пользователей: " + usersDto.size());
        System.out.println("----------------------------------------");

        for (int i = 0; i < usersDto.size(); i++) {
            UserDto userDto = usersDto.get(i);
            System.out.println((i + 1) + ". Электронная почта: " + userDto.getEmail());
            System.out.println("   Описание профиля: " + userDto.getProfileDescription());
            System.out.println("   ID: " + userDto.getId());
            System.out.println("----------------------------------------");
        }
    }

    private void findByProfileDescription() {
        System.out.print("\nВведите описание профиля для поиска пользователей: ");
        String profileDescription = scanner.nextLine();

        List<UserDto> usersDto = userService.findUsersByProfileDescription(profileDescription);

        if (usersDto.isEmpty()) {
            System.out.println("Пользователи с таким описанием профиля не найдены.");
        } else {
            System.out.println("Найдены пользователи с описанием профиля '" + profileDescription + "':");
            System.out.println("----------------------------------------");
            for (int i = 0; i < usersDto.size(); i++) {
                UserDto userDto = usersDto.get(i);
                System.out.println((i + 1) + ". Имя: " + userDto.getName());
                System.out.println("   Электронная почта: " + userDto.getEmail());
                System.out.println("   ID: " + userDto.getId());
                System.out.println("----------------------------------------");
            }
        }
    }
}
