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

//
//package com.example.excel.service;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Point;
//import com.example.excel.repo.ExpenseRepo;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class ExpenseService {
//
////    private final DataService dataService;
////
////    public void saveExpense(String userId, String userName, BigDecimal amount, String description) {
////        dataService.saveExpense(userId,userName,amount,description);
////    }
////
////    public List<Expense> getAllExpenses() {
////        return dataService.getAllExpenses();
////    }
////
////    public Expense updateExpense(Long id, BigDecimal newAmount) {
////        return dataService.updateExpense(id, newAmount);
////    }
////
////    public void deleteExpense(Long id) {
////        dataService.deleteExpense(id);
////    }
//
//    @Autowired
//    private ExpenseRepo expenseRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Expense saveExpense(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Expense expense = new Expense();
//        expense.setUserId(userId);
//        expense.setUserName(userName);
//        expense.setAmount(amount);
//        expense.setDescription(description);
//        expense.setDateTime(LocalDateTime.now());
//        expense.setPoint(point);
//        return expenseRepository.save(expense);
//    }
//
//    public List<Expense> getAllExpenses() {
//        return expenseRepository.findAll();
//    }
//
//    public Expense updateExpense(Long id, BigDecimal newAmount) {
//        Expense expense = expenseRepository.findById(id).orElse(null);
//        if (expense != null) {
//            expense.setAmount(newAmount);
//            return expenseRepository.save(expense);
//        }
//        return null;
//    }
//
//
//}


//28.11

//package com.example.excel.service;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Point;
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
//    private ExpenseRepo expenseRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Expense saveExpense(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Expense expense = new Expense();
//        expense.setUserId(userId);
//        expense.setUserName(userName);
//        expense.setAmount(amount);
//        expense.setDescription(description);
//        expense.setDateTime(LocalDateTime.now());
//        expense.setPoint(point);
//        return expenseRepository.save(expense);
//    }
//
//    public List<Expense> getAllExpenses() {
//        return expenseRepository.findAllByOrderByDateTimeAsc();
//    }
//
//    public Expense updateExpense(Long id, BigDecimal newAmount) {
//        Expense expense = expenseRepository.findById(id).orElse(null);
//        if (expense != null) {
//            expense.setAmount(newAmount);
//            return expenseRepository.save(expense);
//        }
//        return null;
//    }
//}




//package com.example.excel.service;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Point;
//import com.example.excel.repo.ExpenseRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class ExpenseService {
//
//    @Autowired
//    private ExpenseRepo expenseRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Expense saveExpense(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Expense expense = new Expense();
//        expense.setUserId(userId);
//        expense.setUserName(userName);
//        expense.setAmount(amount);
//        expense.setDescription(description);
//        expense.setDateTime(LocalDateTime.now());
//        expense.setPoint(point);
//        return expenseRepository.save(expense);
//    }
//
//    public List<Expense> getAllExpenses() {
//        return expenseRepository.findAllByOrderByDateTimeAsc();
//    }
//
//    public Expense updateExpense(Long id, BigDecimal newAmount) {
//        Expense expense = expenseRepository.findById(id).orElse(null);
//        if (expense != null) {
//            expense.setAmount(newAmount);
//            return expenseRepository.save(expense);
//        }
//        return null;
//    }
//
//    public BigDecimal getTotalExpenseForDay(LocalDate date, Long pointId) {
//        List<Expense> expenses = expenseRepository.findByDateTimeBetweenAndPointId(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), pointId);
//        return expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    public BigDecimal getTotalExpenseForMonth(LocalDate date, Long pointId) {
//        LocalDate startOfMonth = date.withDayOfMonth(1);
//        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
//        List<Expense> expenses = expenseRepository.findByDateTimeBetweenAndPointId(startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay(), pointId);
//        return expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//}


//package com.example.excel.service;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Point;
//import com.example.excel.repo.ExpenseRepo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class ExpenseService {
//
//    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);
//
//    @Autowired
//    private ExpenseRepo expenseRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Expense saveExpense(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Expense expense = new Expense();
//        expense.setUserId(userId);
//        expense.setUserName(userName);
//        expense.setAmount(amount);
//        expense.setDescription(description);
//        expense.setDateTime(LocalDateTime.now());
//        expense.setPoint(point);
//        return expenseRepository.save(expense);
//    }
//
//    public List<Expense> getAllExpenses() {
//        return expenseRepository.findAllByOrderByDateTimeAsc();
//    }
//
//    public Expense updateExpense(Long id, BigDecimal newAmount) {
//        Expense expense = expenseRepository.findById(id).orElse(null);
//        if (expense != null) {
//            expense.setAmount(newAmount);
//            return expenseRepository.save(expense);
//        }
//        return null;
//    }
//
//    public BigDecimal getTotalExpenseForDay(LocalDate date, Long pointId) {
//        logger.info("Fetching expenses for date: {}, pointId: {}", date, pointId);
//        List<Expense> expenses = expenseRepository.findByDateTimeBetweenAndPointId(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), pointId);
//        logger.info("Found {} expenses", expenses.size());
//        return expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    public BigDecimal getTotalExpenseForMonth(LocalDate date, Long pointId) {
//        LocalDate startOfMonth = date.withDayOfMonth(1);
//        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
//        List<Expense> expenses = expenseRepository.findByDateTimeBetweenAndPointId(startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay(), pointId);
//        return expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//}


package com.example.excel.service;

import com.example.excel.model.Expense;
import com.example.excel.model.Point;
import com.example.excel.repo.ExpenseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private ExpenseRepo expenseRepository;

    @Autowired
    private PointService pointService;

    public Expense saveExpense(String userId, String userName, BigDecimal amount, String description, Long pointId) {
        Point point = pointService.getPointById(pointId);
        if (point == null) {
            throw new IllegalArgumentException("Point with id " + pointId + " not found");
        }
        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setUserName(userName);
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setDateTime(LocalDateTime.now());
        expense.setPoint(point);
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllByOrderByDateTimeAsc();
    }

    public Expense updateExpense(Long id, BigDecimal newAmount) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense != null) {
            expense.setAmount(newAmount);
            return expenseRepository.save(expense);
        }
        return null;
    }

    public BigDecimal getTotalExpenseForDay(LocalDate date, Long pointId) {
        logger.info("Fetching expenses for date: {}, pointId: {}", date, pointId);
        List<Expense> expenses = expenseRepository.findByDateTimeBetweenAndPointId(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), pointId);
        logger.info("Found {} expenses", expenses.size());
        return expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalExpenseForMonth(LocalDate date, Long pointId) {
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
        List<Expense> expenses = expenseRepository.findByDateTimeBetweenAndPointId(startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay(), pointId);
        return expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Expense> getExpensesForDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return expenseRepository.findByDateTimeBetween(startOfDay, endOfDay);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}