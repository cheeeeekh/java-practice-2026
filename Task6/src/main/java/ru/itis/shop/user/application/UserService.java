package ru.itis.shop.user.application;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional.map(user -> user.getPassword().equals(password)).orElse(false);
    }

    public Optional<UserDto> findById(String id) {
        try {
            Optional<User> userOptional = userRepository.findById(Integer.valueOf(id));
            return userOptional.map(UserDto::new);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean updateProfileDescription(String email, String newProfileDescription) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfileDescription(newProfileDescription);
            return userRepository.update(user);
        }

        return false;
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public List<UserDto> findUsersByProfileDescription(String profileDescription) {
        List<User> users = userRepository.findAllByProfileDescription(profileDescription);
        return users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }
}
