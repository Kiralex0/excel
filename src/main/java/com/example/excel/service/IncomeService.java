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

package com.example.excel.service;

import com.example.excel.model.Income;
import com.example.excel.repo.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepo incomeRepo;

    public void saveIncome(String userId, String userName, BigDecimal amount, String description) {
        Income income = new Income();
        income.setUserId(userId);
        income.setUserName(userName);
        income.setAmount(amount);
        income.setDescription(description);
        income.setDateTime(LocalDateTime.now());
        incomeRepo.save(income);
    }

    public List<Income> getAllIncomes() {
        return incomeRepo.findAll();
    }

    public Income updateIncome(Long id, BigDecimal newAmount) {
        Income income = incomeRepo.findById(id).orElse(null);
        if (income != null) {
            income.setAmount(newAmount);
            income.setDateTime(LocalDateTime.now());
            return incomeRepo.save(income);
        }
        return null;
    }

    public void deleteIncome(Long id) {
        incomeRepo.deleteById(id);
    }

}
