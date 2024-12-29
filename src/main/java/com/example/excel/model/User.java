package com.example.excel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_user_id", unique = true, nullable = false)
    private Long telegramUserId; // ID пользователя в Telegram

    @Column(name = "password", nullable = false)
    private String password; // Пароль пользователя


//    если появятся роли раскоментировать
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role", nullable = false)
//    private Role role;

    public User() {
    }

    public User(Long telegramUserId, String password) {
        this.telegramUserId = telegramUserId;
        this.password = password;
    }

//    если появятся роли раскоментировать, а верхнее закоментировать
//    public User(Long telegramUserId, String password, Role role) {
//        this.telegramUserId = telegramUserId;
//        this.password = password;
//        this.role = role;
//    }

}
