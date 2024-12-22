package com.example.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PointDTO {
        private Long id;
        private String name;
        private List<IncomeDTO> incomes;
        private List<ExpenseDTO> expenses;
}
