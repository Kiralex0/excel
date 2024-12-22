//package com.example.excel.service;
//
//
//import com.example.excel.model.Income;
//import com.example.excel.repo.IncomeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//    public class IncomeService {
//
//        @Autowired
//        private IncomeRepo incomeRepo;
//
//        public void saveIncome(String userId, BigDecimal amount) {
//            Income income = new Income();
//            income.setUserId(userId);
//            income.setAmount(amount);
//            income.setDateTime(LocalDateTime.now());
//            incomeRepo.save(income);
//        }
//
//    public List<Income> getAllIncomes() {
//        return incomeRepo.findAll();
//    }
//
//    public Income updateIncome(Long id, BigDecimal newAmount) {
//        Income income = incomeRepo.findById(id).orElse(null);
//        if (income != null) {
//            income.setAmount(newAmount);
//            income.setDateTime(LocalDateTime.now());
//            return incomeRepo.save(income);
//        }
//        return null;
//    }
//}

//package com.example.excel.service;
//
//import com.example.excel.model.Income;
//import com.example.excel.model.Point;
//import com.example.excel.repo.IncomeRepo;
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
//public class IncomeService {
//
////    private final DataService dataService;
////
////    public void saveIncome(String userId, String userName, BigDecimal amount, String description) {
////        dataService.saveIncome(userId, userName, amount,description);
////    }
////
////    public List<Income> getAllIncomes() {
////        return dataService.getAllIncomes();
////    }
////
////    public Income updateIncome(Long id, BigDecimal newAmount) {
////        return dataService.updateIncome(id, newAmount);
////    }
////
////    public void deleteIncome(Long id) {
////        dataService.deleteIncome(id);
//
//    @Autowired
//    private IncomeRepo incomeRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Income saveIncome(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Income income = new Income();
//        income.setUserId(userId);
//        income.setUserName(userName);
//        income.setAmount(amount);
//        income.setDescription(description);
//        income.setDateTime(LocalDateTime.now());
//        income.setPoint(point);
//        return incomeRepository.save(income);
//    }
//
//    public List<Income> getAllIncomes() {
//        return incomeRepository.findAll();
//    }
//
//    public Income updateIncome(Long id, BigDecimal newAmount) {
//        Income income = incomeRepository.findById(id).orElse(null);
//        if (income != null) {
//            income.setAmount(newAmount);
//            return incomeRepository.save(income);
//        }
//        return null;
//    }
//
//    }



//28.11
//package com.example.excel.service;
//
//import com.example.excel.model.Income;
//import com.example.excel.model.Point;
//import com.example.excel.repo.IncomeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class IncomeService {
//
//    @Autowired
//    private IncomeRepo incomeRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Income saveIncome(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Income income = new Income();
//        income.setUserId(userId);
//        income.setUserName(userName);
//        income.setAmount(amount);
//        income.setDescription(description);
//        income.setDateTime(LocalDateTime.now());
//        income.setPoint(point);
//        return incomeRepository.save(income);
//    }
//
//    public List<Income> getAllIncomes() {
//        return incomeRepository.findAllByOrderByDateTimeAsc();
//    }
//
//    public Income updateIncome(Long id, BigDecimal newAmount) {
//        Income income = incomeRepository.findById(id).orElse(null);
//        if (income != null) {
//            income.setAmount(newAmount);
//            return incomeRepository.save(income);
//        }
//        return null;
//    }
//
//
////    public BigDecimal getTotalIncomeForDay(LocalDate date, Long pointId) {
////        List<Income> incomes = incomeRepository.findByDateTimeBetweenAndPointId(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), pointId);
////        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
////    }
//}


//package com.example.excel.service;
//
//import com.example.excel.model.Income;
//import com.example.excel.model.Point;
//import com.example.excel.repo.IncomeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class IncomeService {
//
//    @Autowired
//    private IncomeRepo incomeRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Income saveIncome(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Income income = new Income();
//        income.setUserId(userId);
//        income.setUserName(userName);
//        income.setAmount(amount);
//        income.setDescription(description);
//        income.setDateTime(LocalDateTime.now());
//        income.setPoint(point);
//        return incomeRepository.save(income);
//    }
//
//    public List<Income> getAllIncomes() {
//        return incomeRepository.findAllByOrderByDateTimeAsc();
//    }
//
//    public Income updateIncome(Long id, BigDecimal newAmount) {
//        Income income = incomeRepository.findById(id).orElse(null);
//        if (income != null) {
//            income.setAmount(newAmount);
//            return incomeRepository.save(income);
//        }
//        return null;
//    }
//
//    public BigDecimal getTotalIncomeForDay(LocalDate date, Long pointId) {
//        List<Income> incomes = incomeRepository.findByDateTimeBetweenAndPointId(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), pointId);
//        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    public BigDecimal getTotalIncomeForMonth(LocalDate date, Long pointId) {
//        LocalDate startOfMonth = date.withDayOfMonth(1);
//        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
//        List<Income> incomes = incomeRepository.findByDateTimeBetweenAndPointId(startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay(), pointId);
//        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//}



//package com.example.excel.service;
//
//import com.example.excel.model.Income;
//import com.example.excel.model.Point;
//import com.example.excel.repo.IncomeRepo;
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
//public class IncomeService {
//
//    private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);
//
//    @Autowired
//    private IncomeRepo incomeRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    public Income saveIncome(String userId, String userName, BigDecimal amount, String description, Long pointId) {
//        Point point = pointService.getPointById(pointId);
//        if (point == null) {
//            throw new IllegalArgumentException("Point with id " + pointId + " not found");
//        }
//        Income income = new Income();
//        income.setUserId(userId);
//        income.setUserName(userName);
//        income.setAmount(amount);
//        income.setDescription(description);
//        income.setDateTime(LocalDateTime.now());
//        income.setPoint(point);
//        return incomeRepository.save(income);
//    }
//
//    public List<Income> getAllIncomes() {
//        return incomeRepository.findAllByOrderByDateTimeAsc();
//    }
//
//    public Income updateIncome(Long id, BigDecimal newAmount) {
//        Income income = incomeRepository.findById(id).orElse(null);
//        if (income != null) {
//            income.setAmount(newAmount);
//            return incomeRepository.save(income);
//        }
//        return null;
//    }
//
//    public BigDecimal getTotalIncomeForDay(LocalDate date, Long pointId) {
//        logger.info("Fetching incomes for date: {}, pointId: {}", date, pointId);
//        List<Income> incomes = incomeRepository.findByDateTimeBetweenAndPointId(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), pointId);
//        logger.info("Found {} incomes", incomes.size());
//        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    public BigDecimal getTotalIncomeForMonth(LocalDate date, Long pointId) {
//        LocalDate startOfMonth = date.withDayOfMonth(1);
//        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
//        List<Income> incomes = incomeRepository.findByDateTimeBetweenAndPointId(startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay(), pointId);
//        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//}


package com.example.excel.service;

import com.example.excel.model.Income;
import com.example.excel.model.Point;
import com.example.excel.repo.IncomeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomeService {

    private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);

    @Autowired
    private IncomeRepo incomeRepository;

    @Autowired
    private PointService pointService;

    public Income saveIncome(String userId, String userName, BigDecimal amount, String description, Long pointId) {
        Point point = pointService.getPointById(pointId);
        if (point == null) {
            throw new IllegalArgumentException("Point with id " + pointId + " not found");
        }
        Income income = new Income();
        income.setUserId(userId);
        income.setUserName(userName);
        income.setAmount(amount);
        income.setDescription(description);
        income.setDateTime(LocalDateTime.now());
        income.setPoint(point);
        return incomeRepository.save(income);
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAllByOrderByDateTimeAsc();
    }

    public Income updateIncome(Long id, BigDecimal newAmount) {
        Income income = incomeRepository.findById(id).orElse(null);
        if (income != null) {
            income.setAmount(newAmount);
            return incomeRepository.save(income);
        }
        return null;
    }

    public BigDecimal getTotalIncomeForDay(LocalDate date, Long pointId) {
        logger.info("Fetching incomes for date: {}, pointId: {}", date, pointId);
        List<Income> incomes = incomeRepository.findByDateTimeBetweenAndPointId(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), pointId);
        logger.info("Found {} incomes", incomes.size());
        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalIncomeForMonth(LocalDate date, Long pointId) {
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
        List<Income> incomes = incomeRepository.findByDateTimeBetweenAndPointId(startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay(), pointId);
        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Income> getIncomesForDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return incomeRepository.findByDateTimeBetween(startOfDay, endOfDay);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }
}