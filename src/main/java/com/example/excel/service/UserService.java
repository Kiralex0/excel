package com.example.excel.service;

import com.example.excel.model.User;
import com.example.excel.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final Set<Long> authorizedUsers = new HashSet<>();
    private final String password = "КУЛАК202420031971"; // Замените на ваш пароль


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean registerUser(Long userId, String password) {
        // Проверяем, существует ли пользователь
        Optional<User> existingUser = userRepo.findByTelegramUserId(userId);
        if (existingUser.isPresent()) {
            // Если пользователь уже существует, проверяем пароль
            return existingUser.get().getPassword().equals(password);
        } else {
            // Если пользователь не существует, проверяем, совпадает ли введенный пароль с изначально заданным
            if (this.password.equals(password)) {
                // Если пароль совпадает, создаем нового пользователя
                User user = new User(userId, password);
                userRepo.save(user);
                return true;
            } else {
                // Если пароль не совпадает, возвращаем false
                return false;
            }
        }
    }


    public boolean isAuthorized(Long userId) {
        // Проверяем, существует ли пользователь в базе данных
        return userRepo.findByTelegramUserId(userId).isPresent();
    }

    public List<Long> getAllUserIds() {
        return authorizedUsers.stream().collect(Collectors.toList());
    }

}





//        если появятся роли раскоментировать
//    private final Map<Role, String> rolePasswords = Map.of(
//            Role.LOW, "пароль_низкой_роли",
//            Role.MEDIUM, "пароль_средней_роли",
//            Role.HIGH, "пароль_высшей_роли"
//    );


//    public UserService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    public boolean registerUser(Long userId, String password) {
//        Optional<User> existingUser = userRepo.findByTelegramUserId(userId);
//        if (existingUser.isPresent()) {
//            return existingUser.get().getPassword().equals(password);
//        } else {
//            for (Map.Entry<Role, String> entry : rolePasswords.entrySet()) {
//                if (entry.getValue().equals(password)) {
//                    User user = new User(userId, password, entry.getKey());
//                    userRepo.save(user);
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//    public boolean isAuthorized(Long userId) {
//        return userRepo.findByTelegramUserId(userId).isPresent();
//    }
//
//    public Role getUserRole(Long userId) {
//        return userRepo.findByTelegramUserId(userId).map(User::getRole).orElse(null);
//    }
//
//    public List<Long> getAllUserIds() {
//        return authorizedUsers.stream().collect(Collectors.toList());
//    }
