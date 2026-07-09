package ru.itis.shop.user.api.dto;

import ru.itis.shop.user.domain.User;

public class UserDto {

    private final Integer id;
    private final String name;
    private final String email;
    private final String profileDescription;

    public UserDto(Integer id, String name, String email, String profileDescription) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileDescription = profileDescription;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileDescription = user.getProfileDescription();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void print() {
        System.out.println("ID: " + id);
        System.out.println("Имя: " + name);
        System.out.println("Электронная почта: " + email);
        System.out.println("Описание профиля: " + profileDescription);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profileDescription='" + profileDescription + '\'' +
                '}';
    }
}