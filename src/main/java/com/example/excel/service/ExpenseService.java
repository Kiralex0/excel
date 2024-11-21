//package com.example.excel.service;
//
//import com.example.excel.model.Expense;
//import com.example.excel.repo.ExpenseRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class ExpenseService {
//
//    @Autowired
//    private ExpenseRepo expenseRepo;
//
//    public void saveExpense(String userId, BigDecimal amount) {
//        Expense expense = new Expense();
//        expense.setUserId(userId);
//        expense.setAmount(amount);
//        expense.setDateTime(LocalDateTime.now());
//        expenseRepo.save(expense);
//    }
//
//    public List<Expense> getAllExpenses() {
//        return expenseRepo.findAll();
//    }
//
//    public Expense updateExpense(Long id, BigDecimal newAmount) {
//        Expense expense = expenseRepo.findById(id).orElse(null);
//        if (expense != null) {
//            expense.setAmount(newAmount);
//            expense.setDateTime(LocalDateTime.now());
//            return expenseRepo.save(expense);
//        }
//        return null;
//    }
//}


package com.example.excel.service;

import com.example.excel.model.Expense;
import com.example.excel.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    public void saveExpense(String userId, String userName, BigDecimal amount, String description) {
        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setUserName(userName);
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setDateTime(LocalDateTime.now());
        expenseRepo.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public Expense updateExpense(Long id, BigDecimal newAmount) {
        Expense expense = expenseRepo.findById(id).orElse(null);
        if (expense != null) {
            expense.setAmount(newAmount);
            expense.setDateTime(LocalDateTime.now());
            return expenseRepo.save(expense);
        }
        return null;
    }

    public void deleteExpense(Long id) {
        expenseRepo.deleteById(id);
    }

}