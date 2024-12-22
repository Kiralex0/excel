package com.example.excel.service;

import com.example.excel.dto.ExpenseDTO;
import com.example.excel.dto.IncomeDTO;
import com.example.excel.dto.PointDTO;
import com.example.excel.model.Point;
import com.example.excel.repo.PointRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {

    @Autowired
    private PointRepo pointRepository;

    public Point savePoint(String name) {
        Point point = new Point();
        point.setName(name);
        return pointRepository.save(point);
    }

    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    public Point getPointById(Long id) {
        return pointRepository.findById(id).orElse(null);
    }

    public void deletePoint(Long id) {
        pointRepository.deleteById(id);
    }





    @Transactional(readOnly = true)
    public List<PointDTO> getAllPointsWithIncomesAndExpenses() {
        List<Point> points = pointRepository.findAllPoints();
        return points.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PointDTO convertToDTO(Point point) {
        List<IncomeDTO> incomeDTOs = point.getIncomes().stream()
                .map(income -> new IncomeDTO(income.getId(), income.getAmount(), income.getDescription(), income.getDateTime()))
                .collect(Collectors.toList());

        List<ExpenseDTO> expenseDTOs = point.getExpenses().stream()
                .map(expense -> new ExpenseDTO(expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDateTime()))
                .collect(Collectors.toList());

        return new PointDTO(point.getId(), point.getName(), incomeDTOs, expenseDTOs);
    }

}