//package com.example.excel.service;
//
//import com.example.excel.model.User;
//import com.example.excel.repo.UserRepo;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class UserService {
//
//    private final UserRepo userRepo;
//
//    private final Set<Long> authorizedUsers = new HashSet<>();
//    private final String registrationPassword = "12345"; // Замените на ваш пароль
//
//    public UserService(UserRepo userRepo) {
//        // Добавляем разрешенных пользователей
////        authorizedUsers.add(587827079L); // Пример ID пользователя
////        authorizedUsers.add(1147602834L);
////        authorizedUsers.add(138612967L);
//        this.userRepo = userRepo;
//    }
//
//    public boolean registerUser(Long userId, String password) {
//        if (password.equals(registrationPassword)) {
//            // Проверяем, существует ли пользователь
//            if (userRepo.findByTelegramUserId(userId).isEmpty()) {
//                User user = new User(userId);
//                userRepo.save(user);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isAuthorized(Long userId) {
//        return userRepo.findByTelegramUserId(userId).isPresent();
//    }
//
//
//    public void addAuthorizedUser(Long userId) {
//        authorizedUsers.add(userId);
//    }
//
//
//    public List<Long> getAllUserIds() {
//        return authorizedUsers.stream().collect(Collectors.toList());
//    }
//}


package com.example.excel.service;

import com.example.excel.model.Permission;
import com.example.excel.model.Role;
import com.example.excel.model.User;
import com.example.excel.repo.PermissionRepo;
import com.example.excel.repo.RoleRepo;
import com.example.excel.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PermissionRepo permissionRepo;
    private RoleRepo roleRepo;

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
            // Если пользователь не существует, создаем нового
            User user = new User(userId, password);
            userRepo.save(user);
            return true;
        }
    }

//    private static final String ADMIN_ACCESS_PASSWORD = "98765"; // Замените на ваш пароль
//
//    @Autowired
//    public UserService(UserRepo userRepo, RoleRepo roleRepo, PermissionRepo permissionRepo) {
//        this.userRepo = userRepo;
//        this.roleRepo = roleRepo;
//        this.permissionRepo = permissionRepo;
//    }
//
//    public void registerUser(Long telegramUserId, String password) {
//        Optional<User> existingUser = userRepo.findByTelegramUserId(telegramUserId);
//        if (existingUser.isPresent()) {
//            // Если пользователь уже существует, проверяем пароль
//            if (existingUser.get().getPassword().equals(password)) {
//                return;
//            } else {
//                throw new RuntimeException("Неверный пароль");
//            }
//        } else {
//            // Создаем нового пользователя и назначаем роль по умолчанию (USER)
//            Role defaultRole = roleRepo.findByName("USER").orElseThrow(() -> new RuntimeException("Default role not found"));
//
//            User newUser = new User();
//            newUser.setTelegramUserId(telegramUserId);
//            newUser.setPassword(password);
//            newUser.setRole(defaultRole); // Назначаем роль по умолчанию
//            userRepo.save(newUser);
//        }
//    }
//
//    public void assignAdminRole(Long userId, String userPassword, String adminAccessPassword) {
//        // Проверяем, существует ли пользователь
//        User user = userRepo.findByTelegramUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Проверяем, совпадает ли пароль пользователя
//        if (!user.getPassword().equals(userPassword)) {
//            throw new RuntimeException("Неверный пароль пользователя");
//        }
//
//        // Проверяем, совпадает ли пароль для получения роли администратора
//        if (!ADMIN_ACCESS_PASSWORD.equals(adminAccessPassword)) {
//            throw new RuntimeException("Неверный пароль для получения роли администратора");
//        }
//
//        // Назначаем роль ADMIN
//        Role adminRole = roleRepo.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found"));
//        user.setRole(adminRole);
//        userRepo.save(user);
//    }

//    public void registerUser(Long telegramUserId, String password) {
//        Optional<User> existingUser = userRepo.findByTelegramUserId(telegramUserId);
//        if (existingUser.isPresent()) {
//            // Если пользователь уже существует, проверяем пароль
//            if (existingUser.get().getPassword().equals(password)) {
//                return;
//            } else {
//                throw new RuntimeException("Неверный пароль");
//            }
//        } else {
//            // Создаем нового пользователя и назначаем роль по умолчанию (USER)
//            Role defaultRole = roleRepo.findByName("USER").orElseThrow(() -> new RuntimeException("Default role not found"));
//
//            User newUser = new User();
//            newUser.setTelegramUserId(telegramUserId);
//            newUser.setPassword(password);
//            newUser.setRole(defaultRole); // Назначаем роль по умолчанию
//            userRepo.save(newUser);
//        }
//    }
//
//    public void assignAdminRole(Long userId, String userPassword, String adminAccessPassword) {
//        // Проверяем, существует ли пользователь
//        User user = userRepo.findByTelegramUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Проверяем, совпадает ли пароль пользователя
//        if (!user.getPassword().equals(userPassword)) {
//            throw new RuntimeException("Неверный пароль пользователя");
//        }
//
//        // Проверяем, совпадает ли пароль для получения роли администратора
//        if (!ADMIN_ACCESS_PASSWORD.equals(adminAccessPassword)) {
//            throw new RuntimeException("Неверный пароль для получения роли администратора");
//        }
//
//        // Назначаем роль ADMIN
//        Role adminRole = roleRepo.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found"));
//        user.setRole(adminRole);
//        userRepo.save(user);
//    }

    public boolean isAuthorized(Long userId) {
        // Проверяем, существует ли пользователь в базе данных
        return userRepo.findByTelegramUserId(userId).isPresent();
    }

        public List<Long> getAllUserIds() {
        return authorizedUsers.stream().collect(Collectors.toList());
    }

    public boolean hasPermission(Long userId, String action) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole().getName().equals("ADMIN")) {
            return true; // Администратор имеет все разрешения
        }
        Permission permission = permissionRepo.findByUserIdAndAction(userId, action);
        return permission != null && permission.isGranted();
    }



}