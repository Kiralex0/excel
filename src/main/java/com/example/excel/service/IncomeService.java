package com.example.excel.service;


import com.example.excel.model.Income;
import com.example.excel.repo.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
    public class IncomeService {

        @Autowired
        private IncomeRepo incomeRepo;

        public void saveIncome(String userId, BigDecimal amount) {
            Income income = new Income();
            income.setUserId(userId);
            income.setAmount(amount);
            income.setDateTime(LocalDateTime.now());
            incomeRepo.save(income);
        }
    }

