package com.example.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ExpenseDTO {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDateTime dateTime;
}
