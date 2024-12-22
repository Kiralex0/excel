package com.example.excel.telegramm;

import com.example.excel.dto.PointDTO;
import com.example.excel.model.*;
import com.example.excel.repo.UserRepo;
import com.example.excel.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("unused")
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    private final String botToken;
    private final String botUsername;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserStateService userStateService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ExcelService excelService;


    @Autowired
    private ReportService reportService;

    @Autowired
    private UserRepo userRepo;

    public TelegramBot(DefaultBotOptions options, String botToken, String botUsername) {
        super(options);
        this.botToken = botToken;
        this.botUsername = botUsername;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            long userId = update.getMessage().getFrom().getId();
            String userName = update.getMessage().getFrom().getFirstName();
            if (update.getMessage().getFrom().getLastName() != null) {
                userName += " " + update.getMessage().getFrom().getLastName();
            }

            // Проверка авторизации пользователя
            if (!userService.isAuthorized(userId)) {
                UserState userState = userStateService.getUserState(userId);

                if (userState == UserState.WAITING_FOR_REGISTRATION_PASSWORD) {
                    if (userService.registerUser(userId, messageText)) {
                        sendMessage(chatId, "Регистрация успешна! Теперь у вас есть доступ к боту.");
                        sendMainMenu(chatId); // Отправляем главное меню после успешной регистрации
                    } else {
                        sendMessage(chatId, "Неверный пароль. Попробуйте еще раз.");
                    }
                    userStateService.clearUserState(userId);
                } else {
                    sendMessage(chatId, "Для доступа к боту введите пароль:");
                    userStateService.setUserState(userId, UserState.WAITING_FOR_REGISTRATION_PASSWORD);
                }
                return;
            }

            // Если пользователь авторизован, обрабатываем команды
            if (messageText.equals("/start")) {
                sendMainMenu(chatId);
                userStateService.clearUserState(userId);
            } else {
                UserState userState = userStateService.getUserState(userId);

                // Обработка состояний пользователя
                if (userState == UserState.WAITING_FOR_POINT_NAME) {
                    Point point = pointService.savePoint(messageText);
                    sendMessage(chatId, "Точка '" + point.getName() + "' успешно создана с ID: " + point.getId());
                    userStateService.clearUserState(userId);
                    sendMainMenu(chatId);
                } else if (userState == UserState.WAITING_FOR_INCOME_WITH_DESCRIPTION) {
                    try {
                        String[] parts = messageText.split(" ", 2);
                        BigDecimal amount = new BigDecimal(parts[0]);
                        String description = parts.length > 1 ? parts[1] : "No description";
                        incomeService.saveIncome(String.valueOf(userId), userName, amount, description, userStateService.getPointId(userId));
                        sendMessage(chatId, "Доход успешно записан!");
                        userStateService.clearUserState(userId);
                        sendMainMenu(chatId);
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Пожалуйста, введите сумму и описание в формате 'сумма описание'.");
                    }
                } else if (userState == UserState.WAITING_FOR_EXPENSE_WITH_DESCRIPTION) {
                    try {
                        String[] parts = messageText.split(" ", 2);
                        BigDecimal amount = new BigDecimal(parts[0]);
                        String description = parts.length > 1 ? parts[1] : "No description";
                        expenseService.saveExpense(String.valueOf(userId), userName, amount, description, userStateService.getPointId(userId));
                        sendMessage(chatId, "Расход успешно записан!");
                        userStateService.clearUserState(userId);
                        sendMainMenu(chatId);
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Пожалуйста, введите сумму и описание в формате 'сумма описание'.");
                    }
                } else if (userState == UserState.WAITING_FOR_UPDATE_INCOME) {
                    try {
                        String[] parts = messageText.split(" ");
                        if (parts.length == 2) {
                            Long id = Long.parseLong(parts[0]);
                            BigDecimal newAmount = new BigDecimal(parts[1]);
                            Income updatedIncome = incomeService.updateIncome(id, newAmount);
                            if (updatedIncome != null) {
                                sendMessage(chatId, "Доход успешно обновлен!");
                            } else {
                                sendMessage(chatId, "Доход с указанным ID не найден.");
                            }
                        } else {
                            sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
                        }
                        userStateService.clearUserState(userId);
                        sendMainMenu(chatId);
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
                    }
                } else if (userState == UserState.WAITING_FOR_UPDATE_EXPENSE) {
                    try {
                        String[] parts = messageText.split(" ");
                        if (parts.length == 2) {
                            Long id = Long.parseLong(parts[0]);
                            BigDecimal newAmount = new BigDecimal(parts[1]);
                            Expense updatedExpense = expenseService.updateExpense(id, newAmount);
                            if (updatedExpense != null) {
                                sendMessage(chatId, "Расход успешно обновлен!");
                            } else {
                                sendMessage(chatId, "Расход с указанным ID не найден.");
                            }
                        } else {
                            sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
                        }
                        userStateService.clearUserState(userId);
                        sendMainMenu(chatId);
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
                    }
                } else if (userState == UserState.WAITING_FOR_DELETE_INCOME) {
                    try {
                        Long incomeId = Long.parseLong(messageText);
                        incomeService.deleteIncome(incomeId);
                        sendMessage(chatId, "Доход успешно удален.");
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Неверный формат ID.");
                    }
                    userStateService.clearUserState(userId);
                    sendMainMenu(chatId);
                } else if (userState == UserState.WAITING_FOR_DELETE_EXPENSE) {
                    try {
                        Long expenseId = Long.parseLong(messageText);
                        expenseService.deleteExpense(expenseId);
                        sendMessage(chatId, "Расход успешно удален.");
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Неверный формат ID.");
                    }
                    userStateService.clearUserState(userId);
                    sendMainMenu(chatId);
                } else if (userState == UserState.WAITING_FOR_DELETE_POINT) {
                    try {
                        Long pointId = Long.parseLong(messageText);
                        pointService.deletePoint(pointId);
                        sendMessage(chatId, "Точка успешно удалена.");
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Неверный формат ID.");
                    }
                    userStateService.clearUserState(userId);
                    sendMainMenu(chatId);
                } else {
                    sendMessage(chatId, "Неизвестная команда. Используйте /start для начала.");
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            long userId = callbackQuery.getFrom().getId();
            String userName = callbackQuery.getFrom().getFirstName();
            if (callbackQuery.getFrom().getLastName() != null) {
                userName += " " + callbackQuery.getFrom().getLastName();
            }

            if (!userService.isAuthorized(userId)) {
                sendMessage(chatId, "У вас нет доступа к этому боту.");
                return;
            }

            if (callbackData.equals("income")) {
                if (userStateService.getPointId(userId) == null) {
                    sendMessage(chatId, "Сначала выберите точку.");
                    sendSelectPointMenu(chatId);
                } else {
                    sendMessage(chatId, "Введите сумму и описание дохода (например, 112 зарплата):");
                    userStateService.setUserState(userId, UserState.WAITING_FOR_INCOME_WITH_DESCRIPTION);
                }
            } else if (callbackData.equals("expense")) {
                if (userStateService.getPointId(userId) == null) {
                    sendMessage(chatId, "Сначала выберите точку.");
                    sendSelectPointMenu(chatId);
                } else {
                    sendMessage(chatId, "Введите сумму и описание расхода (например, 50 продукты):");
                    userStateService.setUserState(userId, UserState.WAITING_FOR_EXPENSE_WITH_DESCRIPTION);
                }
            } else if (callbackData.equals("list")) {
                List<Income> incomes = incomeService.getAllIncomes();
                List<Expense> expenses = expenseService.getAllExpenses();
                if (incomes.isEmpty() && expenses.isEmpty()) {
                    sendMessage(chatId, "Нет записанных доходов и расходов.");
                } else {
                    sendDataAsFile(chatId, incomes, expenses);
                }
                sendMainMenu(chatId); // Отправляем главное меню после отправки файла
            } else if (callbackData.equals("update_income")) {
                handleUpdateIncome(chatId);
            } else if (callbackData.equals("update_expense")) {
                handleUpdateExpense(chatId);
            } else if (callbackData.equals("select_point")) {
                sendSelectPointMenu(chatId);
            } else if (callbackData.equals("create_point")) {
                handleCreatePoint(chatId);
            } else if (callbackData.startsWith("select_point_")) {
                Long pointId = Long.parseLong(callbackData.split("_")[2]);
                Point point = pointService.getPointById(pointId);
                if (point != null) {
                    userStateService.setPointId(userId, pointId);
                    sendMessage(chatId, "Выбрана точка: " + point.getName());
                    sendMainMenu(chatId);
                } else {
                    sendMessage(chatId, "Точка с указанным ID не найдена.");
                }
            } else if (callbackData.equals("export_points")) {
                handleExportPoints(chatId);
            } else if (callbackData.equals("export_monthly_summary")) {
                handleExportMonthlySummary(chatId);
            } else if ("monthly_report".equals(callbackData)) {
                try {
                    byte[] report = reportService.generateMonthlyReport();
                    SendDocument sendDocument = new SendDocument();
                    sendDocument.setChatId(callbackQuery.getMessage().getChatId());
                    sendDocument.setDocument(new InputFile(new ByteArrayInputStream(report), "monthly_report.xlsx"));
                    execute(sendDocument);

                    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                    answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
                    execute(answerCallbackQuery);

                    sendMainMenu(chatId); // Отправляем главное меню после отправки файла
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (callbackData.equals("show_daily_incomes_expenses")) {
                handleShowDailyIncomesExpenses(chatId);
            } else if (callbackData.equals("delete_income")) {
                handleDeleteIncome(chatId);
            } else if (callbackData.equals("delete_expense")) {
                handleDeleteExpense(chatId);
            } else if (callbackData.equals("delete_point")) {
                handleDeletePoint(chatId);
            }
        }
    }



    private void sendMainMenu(long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton incomeButton = new InlineKeyboardButton();
        incomeButton.setText("📈 Записать доход");
        incomeButton.setCallbackData("income");

        InlineKeyboardButton expenseButton = new InlineKeyboardButton();
        expenseButton.setText("📉 Записать расход");
        expenseButton.setCallbackData("expense");

        InlineKeyboardButton listButton = new InlineKeyboardButton();
        listButton.setText("📋 Показать все доходы и расходы");
        listButton.setCallbackData("list");

        InlineKeyboardButton updateIncomeButton = new InlineKeyboardButton();
        updateIncomeButton.setText("🔄 Обновить доход");
        updateIncomeButton.setCallbackData("update_income");

        InlineKeyboardButton updateExpenseButton = new InlineKeyboardButton();
        updateExpenseButton.setText("🔄 Обновить расход");
        updateExpenseButton.setCallbackData("update_expense");

        InlineKeyboardButton selectPointButton = new InlineKeyboardButton();
        selectPointButton.setText("🏷️ Выбрать точку");
        selectPointButton.setCallbackData("select_point");

        InlineKeyboardButton createPointButton = new InlineKeyboardButton();
        createPointButton.setText("➕ Создать точку");
        createPointButton.setCallbackData("create_point");

        InlineKeyboardButton exportPointsButton = new InlineKeyboardButton();
        exportPointsButton.setText("📤 Экспорт всех данных");
        exportPointsButton.setCallbackData("export_points");

        InlineKeyboardButton exportMonthlySummaryButton = new InlineKeyboardButton();
        exportMonthlySummaryButton.setText("📊 Месячный баланс точек");
        exportMonthlySummaryButton.setCallbackData("export_monthly_summary");

        InlineKeyboardButton monthlyReportButton = new InlineKeyboardButton();
        monthlyReportButton.setText("📅 Отчет точек за месяц");
        monthlyReportButton.setCallbackData("monthly_report");

        InlineKeyboardButton showDailyIncomesExpensesButton = new InlineKeyboardButton();
        showDailyIncomesExpensesButton.setText("📅 Показать доходы и расходы за день");
        showDailyIncomesExpensesButton.setCallbackData("show_daily_incomes_expenses");

        InlineKeyboardButton deleteIncomeButton = new InlineKeyboardButton();
        deleteIncomeButton.setText("🗑️ Удалить доход");
        deleteIncomeButton.setCallbackData("delete_income");

        InlineKeyboardButton deleteExpenseButton = new InlineKeyboardButton();
        deleteExpenseButton.setText("🗑️ Удалить расход");
        deleteExpenseButton.setCallbackData("delete_expense");

        InlineKeyboardButton deletePointButton = new InlineKeyboardButton();
        deletePointButton.setText("🗑️ Удалить точку");
        deletePointButton.setCallbackData("delete_point");



        rowsInline.add(List.of(incomeButton));
        rowsInline.add(List.of(expenseButton));
        rowsInline.add(List.of(listButton));
        rowsInline.add(List.of(updateIncomeButton));
        rowsInline.add(List.of(updateExpenseButton));
        rowsInline.add(List.of(selectPointButton));
        rowsInline.add(List.of(createPointButton));
        rowsInline.add(List.of(exportPointsButton));
        rowsInline.add(List.of(exportMonthlySummaryButton));
        rowsInline.add(List.of(monthlyReportButton));
        rowsInline.add(List.of(showDailyIncomesExpensesButton));
        rowsInline.add(List.of(deleteIncomeButton));
        rowsInline.add(List.of(deleteExpenseButton));
        rowsInline.add(List.of(deletePointButton));

        markupInline.setKeyboard(rowsInline);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите действие:");
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage());
        }
    }

    private void sendSelectPointMenu(long chatId) {
        List<Point> points = pointService.getAllPoints();
        if (points.isEmpty()) {
            sendMessage(chatId, "Нет доступных точек.");
        } else {
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            for (Point point : points) {
                InlineKeyboardButton pointButton = new InlineKeyboardButton();
                pointButton.setText(point.getName());
                pointButton.setCallbackData("select_point_" + point.getId());
                rowsInline.add(List.of(pointButton));
            }
            markupInline.setKeyboard(rowsInline);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Выберите точку:");
            message.setReplyMarkup(markupInline);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                logger.error("Error sending message: {}", e.getMessage());
            }
        }
    }

    private void handleUpdateIncome(long chatId) {
        sendMessage(chatId, "Введите ID и новую сумму для обновления дохода (например, 1 100.50):");
        userStateService.setUserState(chatId, UserState.WAITING_FOR_UPDATE_INCOME);
    }

    private void handleUpdateExpense(long chatId) {
        sendMessage(chatId, "Введите ID и новую сумму для обновления расхода (например, 1 100.50):");
        userStateService.setUserState(chatId, UserState.WAITING_FOR_UPDATE_EXPENSE);
    }

    private void handleCreatePoint(long chatId) {
        sendMessage(chatId, "Введите название новой точки:");
        userStateService.setUserState(chatId, UserState.WAITING_FOR_POINT_NAME);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
            logger.info("Sent message: {}", text);
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage());
        }
    }

    public void sendMessageToAllUsers(String text) {
        List<Long> userIds = userService.getAllUserIds();
        for (Long userId : userIds) {
            sendMessage(userId, text);
        }
    }


    private void handleExportPoints(long chatId) {
        try {
            List<PointDTO> points = pointService.getAllPointsWithIncomesAndExpenses();
            byte[] excelData = excelService.exportPointsToXls(points);
            sendExcelFile(chatId, excelData, "points.xlsx");
        } catch (IOException e) {
            logger.error("Error exporting points to Excel: {}", e.getMessage());
            sendMessage(chatId, "Произошла ошибка при экспорте данных.");
        }

    }

    private void sendExcelFile(long chatId, byte[] data, String fileName) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(new ByteArrayInputStream(data), fileName));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            logger.error("Error sending Excel file: {}", e.getMessage());
        }
    }

    private void handleExportMonthlySummary(long chatId) {
        try {
            LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
            List<Point> points = pointService.getAllPoints();
            byte[] excelData = excelService.exportMonthlySummaryToXls(points, lastDayOfMonth);
            sendExcelFile(chatId, excelData, "monthly_summary.xlsx");
        } catch (IOException | IllegalAccessException e) {
            logger.error("Error exporting monthly summary to Excel: {}", e.getMessage());
            sendMessage(chatId, "Произошла ошибка при экспорте данных.");
        }
    }

    private void sendDataAsFile(long chatId, List<Income> incomes, List<Expense> expenses) {
        try {
            byte[] excelData = excelService.exportIncomesAndExpensesToXls(incomes, expenses);
            sendExcelFile(chatId, excelData, "incomes_expenses.xlsx");
        } catch (IOException e) {
            logger.error("Error creating Excel file: {}", e.getMessage());
            sendMessage(chatId, "Произошла ошибка при создании файла.");
        }
    }

    private void handleShowDailyIncomesExpenses(long chatId) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        try {
            // Получаем данные за текущий день
            List<Income> todayIncomes = incomeService.getIncomesForDay(today);
            List<Expense> todayExpenses = expenseService.getExpensesForDay(today);

            // Генерируем файл для текущего дня
            byte[] todayData = excelService.exportDailyIncomesExpensesToCsv(today, todayIncomes, todayExpenses);
            sendCsvFile(chatId, todayData, "daily_incomes_expenses_сегодня.csv");

            // Получаем данные за предыдущий день
            List<Income> yesterdayIncomes = incomeService.getIncomesForDay(yesterday);
            List<Expense> yesterdayExpenses = expenseService.getExpensesForDay(yesterday);

            // Генерируем файл для предыдущего дня
            byte[] yesterdayData = excelService.exportDailyIncomesExpensesToCsv(yesterday, yesterdayIncomes, yesterdayExpenses);
            sendCsvFile(chatId, yesterdayData, "daily_incomes_expenses_вчера.csv");

        } catch (IOException e) {
            logger.error("Error generating daily incomes and expenses report: {}", e.getMessage());
            sendMessage(chatId, "Произошла ошибка при генерации отчета.");
        }
    }

    private void sendCsvFile(long chatId, byte[] data, String fileName) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(new ByteArrayInputStream(data), fileName));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            logger.error("Error sending CSV file: {}", e.getMessage());
        }
    }

    private void handleDeletePoint(long chatId) {
        sendMessage(chatId, "Введите ID точки, которую хотите удалить:");
        userStateService.setUserState(chatId, UserState.WAITING_FOR_DELETE_POINT);
    }

    private void handleDeleteExpense(long chatId) {
        sendMessage(chatId, "Введите ID расхода, который хотите удалить:");
        userStateService.setUserState(chatId, UserState.WAITING_FOR_DELETE_EXPENSE);
    }

    private void handleDeleteIncome(long chatId) {
        sendMessage(chatId, "Введите ID дохода, который хотите удалить:");
        userStateService.setUserState(chatId, UserState.WAITING_FOR_DELETE_INCOME);
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getBotToken() {
        return botToken;
    }
}