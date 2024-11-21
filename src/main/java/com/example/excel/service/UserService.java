package com.example.excel.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final Set<Long> authorizedUsers = new HashSet<>();
    private final String registrationPassword = "12345"; // Замените на ваш пароль

    public UserService() {
        // Добавляем разрешенных пользователей
        authorizedUsers.add(587827079L); // Пример ID пользователя
        authorizedUsers.add(1147602834L);
    }

    public boolean registerUser(Long userId, String password) {
        if (password.equals(registrationPassword)) {
            authorizedUsers.add(userId);
            return true;
        }
        return false;
    }

    public void addAuthorizedUser(Long userId) {
        authorizedUsers.add(userId);
    }

    public boolean isAuthorized(Long userId) {
        return authorizedUsers.contains(userId);
    }
}
