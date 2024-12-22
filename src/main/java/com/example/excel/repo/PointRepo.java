package com.example.excel.repo;


import com.example.excel.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepo extends JpaRepository<Point, Long> {
        @Query("SELECT p FROM Point p")
        List<Point> findAllPoints();
}

