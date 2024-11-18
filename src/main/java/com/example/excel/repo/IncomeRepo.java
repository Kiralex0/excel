package com.example.excel.repo;

import com.example.excel.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {
}
