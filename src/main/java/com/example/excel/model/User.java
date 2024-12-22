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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role; // Роль пользователя

    public User() {
    }

    public User(Long telegramUserId, String password) {
        this.telegramUserId = telegramUserId;
        this.password = password;
    }
}
