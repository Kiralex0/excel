package com.example.excel.service;

import com.example.excel.model.Point;
import com.example.excel.telegramm.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DailySummaryService {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private PointService pointService;

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(cron = "0 18 00 * * ?") // Запускать каждый день в 23:00 (c м ч)
    public void sendDailySummary() {
        LocalDate today = LocalDate.now();
        List<Point> points = pointService.getAllPoints();

        for (Point point : points) {
            BigDecimal totalIncome = incomeService.getTotalIncomeForDay(today, point.getId());
            BigDecimal totalExpense = expenseService.getTotalExpenseForDay(today, point.getId());

            String summaryMessage = "Ежедневный отчет за " + today + " для точки " + point.getName() + ":\n" +
                    "Доходы: " + totalIncome + "\n" +
                    "Расходы: " + totalExpense + "\n" +
                    "Баланс: " + totalIncome.subtract(totalExpense);

            telegramBot.sendMessageToAllUsers(summaryMessage);
        }
    }
}