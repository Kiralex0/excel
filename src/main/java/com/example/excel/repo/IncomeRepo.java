package com.example.excel.repo;

import com.example.excel.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {
    @Query("SELECT i FROM Income i JOIN FETCH i.point")
    List<Income> findAllByOrderByDateTimeAsc();
    List<Income> findByDateTimeBetweenAndPointId(LocalDateTime start, LocalDateTime end, Long pointId);
    List<Income> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
