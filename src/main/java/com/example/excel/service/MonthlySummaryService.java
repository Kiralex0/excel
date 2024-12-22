package com.example.excel.service;

import com.example.excel.model.Point;
import com.example.excel.telegramm.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MonthlySummaryService {

    private static final Logger logger = LoggerFactory.getLogger(MonthlySummaryService.class);

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private PointService pointService;

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(cron = "0 30 00 2 * ?") // Запускать в последний день каждого месяца в 23:00
    public void sendMonthlySummary() {
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        List<Point> points = pointService.getAllPoints();

        logger.info("Generating monthly summary for last day of month: {}", lastDayOfMonth);

        for (Point point : points) {
            BigDecimal totalIncome = incomeService.getTotalIncomeForMonth(lastDayOfMonth, point.getId());
            BigDecimal totalExpense = expenseService.getTotalExpenseForMonth(lastDayOfMonth, point.getId());

            String summaryMessage = "Ежемесячный отчет за " + lastDayOfMonth.getMonth() + " для точки " + point.getName() + ":\n" +
                    "Доходы: " + totalIncome + "\n" +
                    "Расходы: " + totalExpense + "\n" +
                    "Баланс: " + totalIncome.subtract(totalExpense);

            logger.info("Sending monthly summary for point {}: {}", point.getName(), summaryMessage);
            telegramBot.sendMessageToAllUsers(summaryMessage);
        }
    }
}