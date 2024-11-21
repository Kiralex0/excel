//package com.example.excel.telegramm;
//
//import com.example.excel.model.Income;
//import com.example.excel.service.IncomeService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Component
//@SuppressWarnings("unused")
//public class TelegramBot extends TelegramLongPollingBot {
//
//    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
//
//    private final String botToken;
//    private final String botUsername;
//
//    @Autowired
//    private IncomeService incomeService;
//
//    public TelegramBot(DefaultBotOptions options, String botToken, String botUsername) {
//        super(options);
//        this.botToken = botToken;
//        this.botUsername = botUsername;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            String userId = String.valueOf(update.getMessage().getFrom().getId());
//
//            logger.info("Received message: {}", messageText);
//
//            if (messageText.equals("/start")) {
//                sendMessage(chatId, "Привет! Я бот для записи доходов. Просто отправь мне сумму дохода, и я сохраню её.");
//            } else if (messageText.equals("/list")) {
//                List<Income> incomes = incomeService.getAllIncomes();
//                if (incomes.isEmpty()) {
//                    sendMessage(chatId, "Нет записанных доходов.");
//                } else {
//                    StringBuilder sb = new StringBuilder("Записанные доходы:\n");
//                    for (Income income : incomes) {
//                        sb.append("ID: ").append(income.getId())
//                                .append(", User ID: ").append(income.getUserId())
//                                .append(", Amount: ").append(income.getAmount())
//                                .append(", Date: ").append(income.getDateTime())
//                                .append("\n");
//                    }
//                    sendMessage(chatId, sb.toString());
//                }
//            } else if (messageText.startsWith("/update ")) {
//                String[] parts = messageText.split(" ");
//                if (parts.length == 3) {
//                    try {
//                        Long incomeId = Long.parseLong(parts[1]);
//                        BigDecimal newAmount = new BigDecimal(parts[2]);
//                        Income updatedIncome = incomeService.updateIncome(incomeId, newAmount);
//                        if (updatedIncome != null) {
//                            sendMessage(chatId, "Доход успешно обновлен!");
//                        } else {
//                            sendMessage(chatId, "Доход с указанным ID не найден.");
//                        }
//                    } catch (NumberFormatException e) {
//                        sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
//                    }
//                } else {
//                    sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
//                }
//            } else {
//                try {
//                    BigDecimal amount = new BigDecimal(messageText);
//                    incomeService.saveIncome(userId, amount);
//                    sendMessage(chatId, "Доход успешно записан!");
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
//                }
//            }
//        }
//    }
//
//    private void sendMessage(long chatId, String text) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        try {
//            execute(message);
//            logger.info("Sent message: {}", text);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public String getBotToken() {
//        return botToken;
//    }
//}

//package com.example.excel.telegramm;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Income;
//import com.example.excel.service.ExpenseService;
//import com.example.excel.service.IncomeService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Component
//@SuppressWarnings("unused")
//public class TelegramBot extends TelegramLongPollingBot {
//
//    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
//
//    private final String botToken;
//    private final String botUsername;
//
//    @Autowired
//    private IncomeService incomeService;
//
//    @Autowired
//    private ExpenseService expenseService;
//
//    public TelegramBot(DefaultBotOptions options, String botToken, String botUsername) {
//        super(options);
//        this.botToken = botToken;
//        this.botUsername = botUsername;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            String userId = String.valueOf(update.getMessage().getFrom().getId());
//
//            logger.info("Received message: {}", messageText);
//
//            if (messageText.equals("/start")) {
//                sendMessage(chatId, "Привет! Я бот для записи доходов и расходов. Используйте команды /income для записи дохода и /expense для записи расхода.");
//            } else if (messageText.equals("/list")) {
//                List<Income> incomes = incomeService.getAllIncomes();
//                List<Expense> expenses = expenseService.getAllExpenses();
//                if (incomes.isEmpty() && expenses.isEmpty()) {
//                    sendMessage(chatId, "Нет записанных доходов и расходов.");
//                } else {
//                    StringBuilder sb = new StringBuilder("Записанные доходы и расходы:\n");
//                    if (!incomes.isEmpty()) {
//                        sb.append("Доходы:\n");
//                        for (Income income : incomes) {
//                            sb.append("ID: ").append(income.getId())
//                                    .append(", User ID: ").append(income.getUserId())
//                                    .append(", Amount: ").append(income.getAmount())
//                                    .append(", Date: ").append(income.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    if (!expenses.isEmpty()) {
//                        sb.append("Расходы:\n");
//                        for (Expense expense : expenses) {
//                            sb.append("ID: ").append(expense.getId())
//                                    .append(", User ID: ").append(expense.getUserId())
//                                    .append(", Amount: ").append(expense.getAmount())
//                                    .append(", Date: ").append(expense.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    sendMessage(chatId, sb.toString());
//                }
//            } else if (messageText.startsWith("/update ")) {
//                String[] parts = messageText.split(" ");
//                if (parts.length == 3) {
//                    try {
//                        Long id = Long.parseLong(parts[1]);
//                        BigDecimal newAmount = new BigDecimal(parts[2]);
//                        Income updatedIncome = incomeService.updateIncome(id, newAmount);
//                        if (updatedIncome != null) {
//                            sendMessage(chatId, "Доход успешно обновлен!");
//                        } else {
//                            Expense updatedExpense = expenseService.updateExpense(id, newAmount);
//                            if (updatedExpense != null) {
//                                sendMessage(chatId, "Расход успешно обновлен!");
//                            } else {
//                                sendMessage(chatId, "Доход или расход с указанным ID не найден.");
//                            }
//                        }
//                    } catch (NumberFormatException e) {
//                        sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
//                    }
//                } else {
//                    sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
//                }
//            } else if (messageText.startsWith("/income ")) {
//                try {
//                    BigDecimal amount = new BigDecimal(messageText.substring(8));
//                    incomeService.saveIncome(userId, amount);
//                    sendMessage(chatId, "Доход успешно записан!");
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
//                }
//            } else if (messageText.startsWith("/expense ")) {
//                try {
//                    BigDecimal amount = new BigDecimal(messageText.substring(9));
//                    expenseService.saveExpense(userId, amount);
//                    sendMessage(chatId, "Расход успешно записан!");
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
//                }
//            } else {
//                sendMessage(chatId, "Неизвестная команда. Используйте /start для начала.");
//            }
//        }
//    }
//
//    private void sendMessage(long chatId, String text) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        try {
//            execute(message);
//            logger.info("Sent message: {}", text);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public String getBotToken() {
//        return botToken;
//    }
//}


//package com.example.excel.telegramm;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Income;
//import com.example.excel.service.ExpenseService;
//import com.example.excel.service.IncomeService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@SuppressWarnings("unused")
//public class TelegramBot extends TelegramLongPollingBot {
//
//    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
//
//    private final String botToken;
//    private final String botUsername;
//
//    @Autowired
//    private IncomeService incomeService;
//
//    @Autowired
//    private ExpenseService expenseService;
//
//    public TelegramBot(DefaultBotOptions options, String botToken, String botUsername) {
//        super(options);
//        this.botToken = botToken;
//        this.botUsername = botUsername;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            String userId = String.valueOf(update.getMessage().getFrom().getId());
//
//            logger.info("Received message: {}", messageText);
//
//            if (messageText.equals("/start")) {
//                sendMainMenu(chatId);
//            } else {
//                sendMessage(chatId, "Неизвестная команда. Используйте /start для начала.");
//            }
//        } else if (update.hasCallbackQuery()) {
//            CallbackQuery callbackQuery = update.getCallbackQuery();
//            String callbackData = callbackQuery.getData();
//            long chatId = callbackQuery.getMessage().getChatId();
//            String userId = String.valueOf(callbackQuery.getFrom().getId());
//
//            if (callbackData.equals("income")) {
//                sendMessage(chatId, "Введите сумму дохода:");
//            } else if (callbackData.equals("expense")) {
//                sendMessage(chatId, "Введите сумму расхода:");
//            } else if (callbackData.equals("list")) {
//                List<Income> incomes = incomeService.getAllIncomes();
//                List<Expense> expenses = expenseService.getAllExpenses();
//                if (incomes.isEmpty() && expenses.isEmpty()) {
//                    sendMessage(chatId, "Нет записанных доходов и расходов.");
//                } else {
//                    StringBuilder sb = new StringBuilder("Записанные доходы и расходы:\n");
//                    if (!incomes.isEmpty()) {
//                        sb.append("Доходы:\n");
//                        for (Income income : incomes) {
//                            sb.append("ID: ").append(income.getId())
//                                    .append(", User ID: ").append(income.getUserId())
//                                    .append(", Amount: ").append(income.getAmount())
//                                    .append(", Date: ").append(income.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    if (!expenses.isEmpty()) {
//                        sb.append("Расходы:\n");
//                        for (Expense expense : expenses) {
//                            sb.append("ID: ").append(expense.getId())
//                                    .append(", User ID: ").append(expense.getUserId())
//                                    .append(", Amount: ").append(expense.getAmount())
//                                    .append(", Date: ").append(expense.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    sendMessage(chatId, sb.toString());
//                }
//            } else if (callbackData.startsWith("update_")) {
//                String[] parts = callbackData.split("_");
//                if (parts.length == 3) {
//                    try {
//                        Long id = Long.parseLong(parts[1]);
//                        BigDecimal newAmount = new BigDecimal(parts[2]);
//                        Income updatedIncome = incomeService.updateIncome(id, newAmount);
//                        if (updatedIncome != null) {
//                            sendMessage(chatId, "Доход успешно обновлен!");
//                        } else {
//                            Expense updatedExpense = expenseService.updateExpense(id, newAmount);
//                            if (updatedExpense != null) {
//                                sendMessage(chatId, "Расход успешно обновлен!");
//                            } else {
//                                sendMessage(chatId, "Доход или расход с указанным ID не найден.");
//                            }
//                        }
//                    } catch (NumberFormatException e) {
//                        sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
//                    }
//                } else {
//                    sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
//                }
//            }
//        }
//    }
//
//    private void sendMainMenu(long chatId) {
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//
//        InlineKeyboardButton incomeButton = new InlineKeyboardButton();
//        incomeButton.setText("Записать доход");
//        incomeButton.setCallbackData("income");
//
//        InlineKeyboardButton expenseButton = new InlineKeyboardButton();
//        expenseButton.setText("Записать расход");
//        expenseButton.setCallbackData("expense");
//
//        InlineKeyboardButton listButton = new InlineKeyboardButton();
//        listButton.setText("Показать все доходы и расходы");
//        listButton.setCallbackData("list");
//
//        rowInline.add(incomeButton);
//        rowInline.add(expenseButton);
//        rowsInline.add(rowInline);
//        rowsInline.add(List.of(listButton));
//
//        markupInline.setKeyboard(rowsInline);
//
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Выберите действие:");
//        message.setReplyMarkup(markupInline);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    private void sendMessage(long chatId, String text) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        try {
//            execute(message);
//            logger.info("Sent message: {}", text);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public String getBotToken() {
//        return botToken;
//    }
//}


//package com.example.excel.telegramm;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Income;
//import com.example.excel.model.UserState;
//import com.example.excel.service.ExpenseService;
//import com.example.excel.service.IncomeService;
//import com.example.excel.service.UserStateService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@SuppressWarnings("unused")
//public class TelegramBot extends TelegramLongPollingBot {
//
//    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
//
//    private final String botToken;
//    private final String botUsername;
//
//    @Autowired
//    private IncomeService incomeService;
//
//    @Autowired
//    private ExpenseService expenseService;
//
//    @Autowired
//    private UserStateService userStateService;
//
//    public TelegramBot(DefaultBotOptions options, String botToken, String botUsername) {
//        super(options);
//        this.botToken = botToken;
//        this.botUsername = botUsername;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            long userId = update.getMessage().getFrom().getId();
//
//            logger.info("Received message: {}", messageText);
//
//            UserState userState = userStateService.getUserState(userId);
//
//            if (messageText.equals("/start")) {
//                sendMainMenu(chatId);
//                userStateService.clearUserState(userId);
//            } else if (userState == UserState.WAITING_FOR_INCOME) {
//                try {
//                    BigDecimal amount = new BigDecimal(messageText);
//                    incomeService.saveIncome(String.valueOf(userId), amount);
//                    sendMessage(chatId, "Доход успешно записан!");
//                    userStateService.clearUserState(userId);
//                    sendMainMenu(chatId);
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
//                }
//            } else if (userState == UserState.WAITING_FOR_EXPENSE) {
//                try {
//                    BigDecimal amount = new BigDecimal(messageText);
//                    expenseService.saveExpense(String.valueOf(userId), amount);
//                    sendMessage(chatId, "Расход успешно записан!");
//                    userStateService.clearUserState(userId);
//                    sendMainMenu(chatId);
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
//                }
//            } else {
//                sendMessage(chatId, "Неизвестная команда. Используйте /start для начала.");
//            }
//        } else if (update.hasCallbackQuery()) {
//            CallbackQuery callbackQuery = update.getCallbackQuery();
//            String callbackData = callbackQuery.getData();
//            long chatId = callbackQuery.getMessage().getChatId();
//            long userId = callbackQuery.getFrom().getId();
//
//            if (callbackData.equals("income")) {
//                sendMessage(chatId, "Введите сумму дохода:");
//                userStateService.setUserState(userId, UserState.WAITING_FOR_INCOME);
//            } else if (callbackData.equals("expense")) {
//                sendMessage(chatId, "Введите сумму расхода:");
//                userStateService.setUserState(userId, UserState.WAITING_FOR_EXPENSE);
//            } else if (callbackData.equals("list")) {
//                List<Income> incomes = incomeService.getAllIncomes();
//                List<Expense> expenses = expenseService.getAllExpenses();
//                if (incomes.isEmpty() && expenses.isEmpty()) {
//                    sendMessage(chatId, "Нет записанных доходов и расходов.");
//                } else {
//                    StringBuilder sb = new StringBuilder("Записанные доходы и расходы:\n");
//                    if (!incomes.isEmpty()) {
//                        sb.append("Доходы:\n");
//                        for (Income income : incomes) {
//                            sb.append("ID: ").append(income.getId())
//                                    .append(", User ID: ").append(income.getUserId())
//                                    .append(", Amount: ").append(income.getAmount())
//                                    .append(", Date: ").append(income.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    if (!expenses.isEmpty()) {
//                        sb.append("Расходы:\n");
//                        for (Expense expense : expenses) {
//                            sb.append("ID: ").append(expense.getId())
//                                    .append(", User ID: ").append(expense.getUserId())
//                                    .append(", Amount: ").append(expense.getAmount())
//                                    .append(", Date: ").append(expense.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    sendMessage(chatId, sb.toString());
//                }
//            } else if (callbackData.startsWith("update_")) {
//                String[] parts = callbackData.split("_");
//                if (parts.length == 3) {
//                    try {
//                        Long id = Long.parseLong(parts[1]);
//                        BigDecimal newAmount = new BigDecimal(parts[2]);
//                        Income updatedIncome = incomeService.updateIncome(id, newAmount);
//                        if (updatedIncome != null) {
//                            sendMessage(chatId, "Доход успешно обновлен!");
//                        } else {
//                            Expense updatedExpense = expenseService.updateExpense(id, newAmount);
//                            if (updatedExpense != null) {
//                                sendMessage(chatId, "Расход успешно обновлен!");
//                            } else {
//                                sendMessage(chatId, "Доход или расход с указанным ID не найден.");
//                            }
//                        }
//                    } catch (NumberFormatException e) {
//                        sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
//                    }
//                } else {
//                    sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
//                }
//            }
//        }
//    }
//
//    private void sendMainMenu(long chatId) {
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//
//        InlineKeyboardButton incomeButton = new InlineKeyboardButton();
//        incomeButton.setText("Записать доход");
//        incomeButton.setCallbackData("income");
//
//        InlineKeyboardButton expenseButton = new InlineKeyboardButton();
//        expenseButton.setText("Записать расход");
//        expenseButton.setCallbackData("expense");
//
//        InlineKeyboardButton listButton = new InlineKeyboardButton();
//        listButton.setText("Показать все доходы и расходы");
//        listButton.setCallbackData("list");
//
//        rowInline.add(incomeButton);
//        rowInline.add(expenseButton);
//        rowsInline.add(rowInline);
//        rowsInline.add(List.of(listButton));
//
//        markupInline.setKeyboard(rowsInline);
//
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Выберите действие:");
//        message.setReplyMarkup(markupInline);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    private void sendMessage(long chatId, String text) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        try {
//            execute(message);
//            logger.info("Sent message: {}", text);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public String getBotToken() {
//        return botToken;
//    }
//}


//package com.example.excel.telegramm;
//
//import com.example.excel.model.Expense;
//import com.example.excel.model.Income;
//import com.example.excel.model.UserState;
//import com.example.excel.service.ExpenseService;
//import com.example.excel.service.IncomeService;
//import com.example.excel.service.UserService;
//import com.example.excel.service.UserStateService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@SuppressWarnings("unused")
//public class TelegramBot extends TelegramLongPollingBot {
//
//    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
//
//    private final String botToken;
//    private final String botUsername;
//
//    @Autowired
//    private IncomeService incomeService;
//
//    @Autowired
//    private ExpenseService expenseService;
//
//    @Autowired
//    private UserStateService userStateService;
//
//    @Autowired
//    private UserService userService;
//
//    public TelegramBot(DefaultBotOptions options, String botToken, String botUsername) {
//        super(options);
//        this.botToken = botToken;
//        this.botUsername = botUsername;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            long userId = update.getMessage().getFrom().getId();
//
//            logger.info("Received message: {}", messageText);
//
//            if (!userService.isAuthorized(userId)) {
//                sendMessage(chatId, "У вас нет доступа к этому боту.");
//                return;
//            }
//
//            UserState userState = userStateService.getUserState(userId);
//
//            if (messageText.equals("/start")) {
//                sendMainMenu(chatId);
//                userStateService.clearUserState(userId);
//            } else if (userState == UserState.WAITING_FOR_INCOME) {
//                try {
//                    BigDecimal amount = new BigDecimal(messageText);
//                    incomeService.saveIncome(String.valueOf(userId), amount);
//                    sendMessage(chatId, "Доход успешно записан!");
//                    userStateService.clearUserState(userId);
//                    sendMainMenu(chatId);
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
//                }
//            } else if (userState == UserState.WAITING_FOR_EXPENSE) {
//                try {
//                    BigDecimal amount = new BigDecimal(messageText);
//                    expenseService.saveExpense(String.valueOf(userId), amount);
//                    sendMessage(chatId, "Расход успешно записан!");
//                    userStateService.clearUserState(userId);
//                    sendMainMenu(chatId);
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
//                }
//            } else if (userState == UserState.WAITING_FOR_UPDATE) {
//                try {
//                    String[] parts = messageText.split(" ");
//                    if (parts.length == 2) {
//                        Long id = Long.parseLong(parts[0]);
//                        BigDecimal newAmount = new BigDecimal(parts[1]);
//                        Income updatedIncome = incomeService.updateIncome(id, newAmount);
//                        if (updatedIncome != null) {
//                            sendMessage(chatId, "Доход успешно обновлен!");
//                        } else {
//                            Expense updatedExpense = expenseService.updateExpense(id, newAmount);
//                            if (updatedExpense != null) {
//                                sendMessage(chatId, "Расход успешно обновлен!");
//                            } else {
//                                sendMessage(chatId, "Доход или расход с указанным ID не найден.");
//                            }
//                        }
//                    } else {
//                        sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
//                    }
//                    userStateService.clearUserState(userId);
//                    sendMainMenu(chatId);
//                } catch (NumberFormatException e) {
//                    sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
//                }
//            } else {
//                sendMessage(chatId, "Неизвестная команда. Используйте /start для начала.");
//            }
//        } else if (update.hasCallbackQuery()) {
//            CallbackQuery callbackQuery = update.getCallbackQuery();
//            String callbackData = callbackQuery.getData();
//            long chatId = callbackQuery.getMessage().getChatId();
//            long userId = callbackQuery.getFrom().getId();
//
//            if (!userService.isAuthorized(userId)) {
//                sendMessage(chatId, "У вас нет доступа к этому боту.");
//                return;
//            }
//
//            if (callbackData.equals("income")) {
//                sendMessage(chatId, "Введите сумму дохода:");
//                userStateService.setUserState(userId, UserState.WAITING_FOR_INCOME);
//            } else if (callbackData.equals("expense")) {
//                sendMessage(chatId, "Введите сумму расхода:");
//                userStateService.setUserState(userId, UserState.WAITING_FOR_EXPENSE);
//            } else if (callbackData.equals("list")) {
//                List<Income> incomes = incomeService.getAllIncomes();
//                List<Expense> expenses = expenseService.getAllExpenses();
//                if (incomes.isEmpty() && expenses.isEmpty()) {
//                    sendMessage(chatId, "Нет записанных доходов и расходов.");
//                } else {
//                    StringBuilder sb = new StringBuilder("Записанные доходы и расходы:\n");
//                    if (!incomes.isEmpty()) {
//                        sb.append("Доходы:\n");
//                        for (Income income : incomes) {
//                            sb.append("ID: ").append(income.getId())
//                                    .append(", User ID: ").append(income.getUserId())
//                                    .append(", Amount: ").append(income.getAmount())
//                                    .append(", Date: ").append(income.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    if (!expenses.isEmpty()) {
//                        sb.append("Расходы:\n");
//                        for (Expense expense : expenses) {
//                            sb.append("ID: ").append(expense.getId())
//                                    .append(", User ID: ").append(expense.getUserId())
//                                    .append(", Amount: ").append(expense.getAmount())
//                                    .append(", Date: ").append(expense.getDateTime())
//                                    .append("\n");
//                        }
//                    }
//                    sendMessage(chatId, sb.toString());
//                }
//            } else if (callbackData.equals("update")) {
//                sendMessage(chatId, "Введите ID и новую сумму для обновления (например, 1 100.50):");
//                userStateService.setUserState(userId, UserState.WAITING_FOR_UPDATE);
//            }
//        }
//    }
//
//    private void sendMainMenu(long chatId) {
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//
//        InlineKeyboardButton incomeButton = new InlineKeyboardButton();
//        incomeButton.setText("Записать доход");
//        incomeButton.setCallbackData("income");
//
//        InlineKeyboardButton expenseButton = new InlineKeyboardButton();
//        expenseButton.setText("Записать расход");
//        expenseButton.setCallbackData("expense");
//
//        InlineKeyboardButton listButton = new InlineKeyboardButton();
//        listButton.setText("Показать все доходы и расходы");
//        listButton.setCallbackData("list");
//
//        InlineKeyboardButton updateButton = new InlineKeyboardButton();
//        updateButton.setText("Обновить запись");
//        updateButton.setCallbackData("update");
//
//        rowInline.add(incomeButton);
//        rowInline.add(expenseButton);
//        rowsInline.add(rowInline);
//        rowsInline.add(List.of(listButton));
//        rowsInline.add(List.of(updateButton));
//
//        markupInline.setKeyboard(rowsInline);
//
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Выберите действие:");
//        message.setReplyMarkup(markupInline);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    private void sendMessage(long chatId, String text) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        try {
//            execute(message);
//            logger.info("Sent message: {}", text);
//        } catch (TelegramApiException e) {
//            logger.error("Error sending message: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public String getBotToken() {
//        return botToken;
//    }
//}


package com.example.excel.telegramm;

import com.example.excel.model.Expense;
import com.example.excel.model.Income;
import com.example.excel.model.UserState;
import com.example.excel.service.ExpenseService;
import com.example.excel.service.IncomeService;
import com.example.excel.service.UserService;
import com.example.excel.service.UserStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
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

            logger.info("Received message: {}", messageText);

            if (!userService.isAuthorized(userId)) {
                if (userStateService.getUserState(userId) == UserState.WAITING_FOR_PASSWORD) {
                    if (userService.registerUser(userId, messageText)) {
                        sendMessage(chatId, "Регистрация успешна! Теперь у вас есть доступ к боту.");
                        sendMainMenu(chatId);
                    } else {
                        sendMessage(chatId, "Неверный пароль. Попробуйте еще раз.");
                    }
                    userStateService.clearUserState(userId);
                } else {
                    sendMessage(chatId, "Для доступа к боту введите пароль:");
                    userStateService.setUserState(userId, UserState.WAITING_FOR_PASSWORD);
                }
                return;
            }

            UserState userState = userStateService.getUserState(userId);

            if (messageText.equals("/start")) {
                sendMainMenu(chatId);
                userStateService.clearUserState(userId);
            } else if (userState == UserState.WAITING_FOR_INCOME_WITH_DESCRIPTION) {
                try {
                    String[] parts = messageText.split(" ", 2);
                    BigDecimal amount = new BigDecimal(parts[0]);
                    String description = parts.length > 1 ? parts[1] : "No description";
                    incomeService.saveIncome(String.valueOf(userId), userName, amount, description);
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
                    expenseService.saveExpense(String.valueOf(userId), userName, amount, description);
                    sendMessage(chatId, "Расход успешно записан!");
                    userStateService.clearUserState(userId);
                    sendMainMenu(chatId);
                } catch (NumberFormatException e) {
                    sendMessage(chatId, "Пожалуйста, введите сумму и описание в формате 'сумма описание'.");
                }
            } else if (userState == UserState.WAITING_FOR_UPDATE) {
                try {
                    String[] parts = messageText.split(" ");
                    if (parts.length == 2) {
                        Long id = Long.parseLong(parts[0]);
                        BigDecimal newAmount = new BigDecimal(parts[1]);
                        Income updatedIncome = incomeService.updateIncome(id, newAmount);
                        if (updatedIncome != null) {
                            sendMessage(chatId, "Доход успешно обновлен!");
                        } else {
                            Expense updatedExpense = expenseService.updateExpense(id, newAmount);
                            if (updatedExpense != null) {
                                sendMessage(chatId, "Расход успешно обновлен!");
                            } else {
                                sendMessage(chatId, "Доход или расход с указанным ID не найден.");
                            }
                        }
                    } else {
                        sendMessage(chatId, "Неверный формат команды. Используйте /update <ID> <Amount>.");
                    }
                    userStateService.clearUserState(userId);
                    sendMainMenu(chatId);
                } catch (NumberFormatException e) {
                    sendMessage(chatId, "Пожалуйста, введите корректные данные для обновления.");
                }
            } else {
                sendMessage(chatId, "Неизвестная команда. Используйте /start для начала.");
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
                sendMessage(chatId, "Введите сумму и описание дохода (например, 112 зарплата):");
                userStateService.setUserState(userId, UserState.WAITING_FOR_INCOME_WITH_DESCRIPTION);
            } else if (callbackData.equals("expense")) {
                sendMessage(chatId, "Введите сумму и описание расхода (например, 50 продукты):");
                userStateService.setUserState(userId, UserState.WAITING_FOR_EXPENSE_WITH_DESCRIPTION);
            } else if (callbackData.equals("list")) {
                List<Income> incomes = incomeService.getAllIncomes();
                List<Expense> expenses = expenseService.getAllExpenses();
                if (incomes.isEmpty() && expenses.isEmpty()) {
                    sendMessage(chatId, "Нет записанных доходов и расходов.");
                } else {
                    StringBuilder sb = new StringBuilder("Записанные доходы и расходы:\n");
                    if (!incomes.isEmpty()) {
                        sb.append("Доходы:\n");
                        for (Income income : incomes) {
                            sb.append("ID: ").append(income.getId())
                                    .append(", User Name: ").append(income.getUserName())
                                    .append(", Amount: ").append(income.getAmount())
                                    .append(", Description: ").append(income.getDescription())
                                    .append(", Date: ").append(income.getDateTime())
                                    .append("\n");
                        }
                    }
                    if (!expenses.isEmpty()) {
                        sb.append("Расходы:\n");
                        for (Expense expense : expenses) {
                            sb.append("ID: ").append(expense.getId())
                                    .append(", User Name: ").append(expense.getUserName())
                                    .append(", Amount: ").append(expense.getAmount())
                                    .append(", Description: ").append(expense.getDescription())
                                    .append(", Date: ").append(expense.getDateTime())
                                    .append("\n");
                        }
                    }
                    sendMessage(chatId, sb.toString());
                }
            } else if (callbackData.equals("update")) {
                sendMessage(chatId, "Введите ID и новую сумму для обновления (например, 1 100.50):");
                userStateService.setUserState(userId, UserState.WAITING_FOR_UPDATE);
            }
        }
    }

    private void sendMainMenu(long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton incomeButton = new InlineKeyboardButton();
        incomeButton.setText("Записать доход");
        incomeButton.setCallbackData("income");

        InlineKeyboardButton expenseButton = new InlineKeyboardButton();
        expenseButton.setText("Записать расход");
        expenseButton.setCallbackData("expense");

        InlineKeyboardButton listButton = new InlineKeyboardButton();
        listButton.setText("Показать все доходы и расходы");
        listButton.setCallbackData("list");

        InlineKeyboardButton updateButton = new InlineKeyboardButton();
        updateButton.setText("Обновить запись");
        updateButton.setCallbackData("update");

        rowInline.add(incomeButton);
        rowInline.add(expenseButton);
        rowsInline.add(rowInline);
        rowsInline.add(List.of(listButton));
        rowsInline.add(List.of(updateButton));

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
