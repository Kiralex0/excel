package com.example.excel.repo;

import com.example.excel.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e JOIN FETCH e.point")
    List<Expense> findAllByOrderByDateTimeAsc();
    List<Expense> findByDateTimeBetweenAndPointId(LocalDateTime start, LocalDateTime end, Long pointId);
    List<Expense> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
