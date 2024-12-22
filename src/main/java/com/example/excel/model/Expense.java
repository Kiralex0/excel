package com.example.excel.model;

import com.example.excel.telegramm.ExcelColumn;
import jakarta.persistence.*;
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
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @ExcelColumn("Имя пользователя")
    private String userName;

    @ExcelColumn("Сумма")
    private BigDecimal amount;

    @ExcelColumn("Описание")
    private String description;

    @ExcelColumn("Время")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;

}