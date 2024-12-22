package com.example.excel.service;

import com.example.excel.dto.PointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private PointService pointService;

    @Autowired
    private ExcelService excelService;

    public byte[] generateMonthlyReport() throws IOException {
        // Получаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Получаем все точки с доходами и расходами за текущий месяц
        List<PointDTO> points = pointService.getAllPointsWithIncomesAndExpenses();

        // Фильтруем доходы и расходы за текущий месяц
        for (PointDTO point : points) {
            point.setIncomes(point.getIncomes().stream()
                    .filter(income -> income.getDateTime().getMonth() == currentDate.getMonth()
                            && income.getDateTime().getYear() == currentDate.getYear())
                    .collect(Collectors.toList()));

            point.setExpenses(point.getExpenses().stream()
                    .filter(expense -> expense.getDateTime().getMonth() == currentDate.getMonth()
                            && expense.getDateTime().getYear() == currentDate.getYear())
                    .collect(Collectors.toList()));
        }

        // Генерируем Excel-файл
        return excelService.exportPointsToXlsForCurrentMonth(points);
    }
}
