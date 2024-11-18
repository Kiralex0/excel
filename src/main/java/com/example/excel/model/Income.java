package com.example.excel.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Entity
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public class Income {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String userId;
        private BigDecimal amount;
        private LocalDateTime dateTime;

    }
