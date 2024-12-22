//package com.example.excel.service;
//
//import com.example.excel.model.Point;
//import com.example.excel.telegramm.ExcelColumn;
//import lombok.AllArgsConstructor;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class ExcelService {
//
//    private static IncomeService incomeService;
//    private static ExpenseService expenseService;
//
//    public static <T> byte[] exportToXls(List<T> list, String sheetName) throws IOException, IllegalAccessException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet(sheetName);
//
//        if (list.isEmpty()) {
//            return new byte[0];
//        }
//
//        T firstItem = list.get(0);
//        Class<?> clazz = firstItem.getClass();
//
//        // Создание заголовков столбцов
//        Row headerRow = sheet.createRow(0);
//        int columnIndex = 0;
//        for (Field field : clazz.getDeclaredFields()) {
//            if (field.isAnnotationPresent(ExcelColumn.class)) {
//                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
//                headerRow.createCell(columnIndex++).setCellValue(excelColumn.value());
//            }
//        }
//
//        // Заполнение данными
//        int rowNum = 1;
//        for (T item : list) {
//            Row row = sheet.createRow(rowNum++);
//            columnIndex = 0;
//            for (Field field : clazz.getDeclaredFields()) {
//                if (field.isAnnotationPresent(ExcelColumn.class)) {
//                    field.setAccessible(true);
//                    Object value = field.get(item);
//                    row.createCell(columnIndex++).setCellValue(value != null ? value.toString() : "");
//                }
//            }
//        }
//
//        // Автоматическое изменение размера столбцов
//        for (int i = 0; i < columnIndex; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Запись данных в байтовый массив
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            workbook.write(byteArrayOutputStream);
//            return byteArrayOutputStream.toByteArray();
//        } finally {
//            workbook.close();
//        }
//    }
//
//    public static byte[] exportPointsToXls(List<Point> points) throws IOException, IllegalAccessException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Points");
//
//        if (points.isEmpty()) {
//            return new byte[0];
//        }
//
//        // Создание заголовков столбцов
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("ID");
//        headerRow.createCell(1).setCellValue("Name");
//
//        // Заполнение данными
//        int rowNum = 1;
//        for (Point point : points) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(point.getId());
//            row.createCell(1).setCellValue(point.getName());
//        }
//
//        // Автоматическое изменение размера столбцов
//        for (int i = 0; i < 2; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Запись данных в байтовый массив
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            workbook.write(byteArrayOutputStream);
//            return byteArrayOutputStream.toByteArray();
//        } finally {
//            workbook.close();
//        }
//    }
//
//    public static byte[] exportMonthlySummaryToXls(List<Point> points, LocalDate date) throws IOException, IllegalAccessException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Monthly Summary");
//
//        if (points.isEmpty()) {
//            return new byte[0];
//        }
//
//        // Создание заголовков столбцов
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Point Name");
//        headerRow.createCell(1).setCellValue("Total Income");
//        headerRow.createCell(2).setCellValue("Total Expense");
//        headerRow.createCell(3).setCellValue("Balance");
//
//        // Заполнение данными
//        int rowNum = 1;
//        for (Point point : points) {
//            BigDecimal totalIncome = incomeService.getTotalIncomeForMonth(date, point.getId());
//            BigDecimal totalExpense = expenseService.getTotalExpenseForMonth(date, point.getId());
//            BigDecimal balance = totalIncome.subtract(totalExpense);
//
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(point.getName());
//            row.createCell(1).setCellValue(totalIncome.doubleValue());
//            row.createCell(2).setCellValue(totalExpense.doubleValue());
//            row.createCell(3).setCellValue(balance.doubleValue());
//        }
//
//        // Автоматическое изменение размера столбцов
//        for (int i = 0; i < 4; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Запись данных в байтовый массив
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            workbook.write(byteArrayOutputStream);
//            return byteArrayOutputStream.toByteArray();
//        } finally {
//            workbook.close();
//        }
//    }
//

//7
package com.example.excel.service;

import com.example.excel.dto.ExpenseDTO;
import com.example.excel.dto.IncomeDTO;
import com.example.excel.dto.PointDTO;
import com.example.excel.model.Expense;
import com.example.excel.model.Income;
import com.example.excel.model.Point;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    private PointService pointService;

    public byte[] exportMonthlySummaryToXls(List<Point> points, LocalDate date) throws IOException, IllegalAccessException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ежемесячный отчет");

        if (points.isEmpty()) {
            return new byte[0];
        }

        // Создание заголовков столбцов
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Название точки");
        headerRow.createCell(1).setCellValue("Доходы");
        headerRow.createCell(2).setCellValue("Расходы");
        headerRow.createCell(3).setCellValue("Баланс");

        // Заполнение данными
        int rowNum = 1;
        for (Point point : points) {
            BigDecimal totalIncome = incomeService.getTotalIncomeForMonth(date, point.getId());
            BigDecimal totalExpense = expenseService.getTotalExpenseForMonth(date, point.getId());
            BigDecimal balance = totalIncome.subtract(totalExpense);

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(point.getName());
            row.createCell(1).setCellValue(totalIncome.doubleValue());
            row.createCell(2).setCellValue(totalExpense.doubleValue());
            row.createCell(3).setCellValue(balance.doubleValue());
        }

        // Автоматическое изменение размера столбцов
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // Запись данных в байтовый массив
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }

    public byte[] exportPointsToXls(List<PointDTO> points) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Точки");

        // Создание заголовков столбцов
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID точки");
        headerRow.createCell(1).setCellValue("Название точки");
        headerRow.createCell(2).setCellValue("Тип");
        headerRow.createCell(3).setCellValue("Сумма");
        headerRow.createCell(4).setCellValue("Описание");
        headerRow.createCell(5).setCellValue("Дата и время");

        // Заполнение данными
        int rowNum = 1;
        for (PointDTO point : points) {
            // Добавление доходов
            for (IncomeDTO income : point.getIncomes()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(point.getId());
                row.createCell(1).setCellValue(point.getName());
                row.createCell(2).setCellValue("Доход");
                row.createCell(3).setCellValue(income.getAmount().doubleValue());
                row.createCell(4).setCellValue(income.getDescription());
                row.createCell(5).setCellValue(income.getDateTime().toString());
            }

            // Добавление расходов
            for (ExpenseDTO expense : point.getExpenses()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(point.getId());
                row.createCell(1).setCellValue(point.getName());
                row.createCell(2).setCellValue("Расход");
                row.createCell(3).setCellValue(expense.getAmount().doubleValue());
                row.createCell(4).setCellValue(expense.getDescription());
                row.createCell(5).setCellValue(expense.getDateTime().toString());
            }
        }

        // Автоматическое изменение размера столбцов
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Запись данных в байтовый массив
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }


    public byte[] exportPointsToXlsForCurrentMonth(List<PointDTO> points) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет за текущий месяц");

        // Создание заголовков столбцов
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID точки");
        headerRow.createCell(1).setCellValue("Название точки");
        headerRow.createCell(2).setCellValue("Тип");
        headerRow.createCell(3).setCellValue("Сумма");
        headerRow.createCell(4).setCellValue("Описание");
        headerRow.createCell(5).setCellValue("Дата и время");

        // Заполнение данными
        int rowNum = 1;
        for (PointDTO point : points) {
            for (IncomeDTO income : point.getIncomes()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(point.getId());
                row.createCell(1).setCellValue(point.getName());
                row.createCell(2).setCellValue("Доход");
                row.createCell(3).setCellValue(income.getAmount().doubleValue());
                row.createCell(4).setCellValue(income.getDescription());
                row.createCell(5).setCellValue(income.getDateTime().toString());
            }

            for (ExpenseDTO expense : point.getExpenses()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(point.getId());
                row.createCell(1).setCellValue(point.getName());
                row.createCell(2).setCellValue("Расход");
                row.createCell(3).setCellValue(expense.getAmount().doubleValue());
                row.createCell(4).setCellValue(expense.getDescription());
                row.createCell(5).setCellValue(expense.getDateTime().toString());
            }
        }

        // Автоматическое изменение размера столбцов
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Запись данных в байтовый массив
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }

    public byte[] exportDailyIncomesExpensesToCsv(LocalDate date, List<Income> incomes, List<Expense> expenses) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(outputStream), CSVFormat.DEFAULT.withHeader("Type", "ID", "User Name", "Amount", "Description", "Date", "Point"))) {
            // Добавляем доходы
            for (Income income : incomes) {
                printer.printRecord("Доход", income.getId(), income.getUserName(), income.getAmount(), income.getDescription(), income.getDateTime(), income.getPoint().getName());
            }
            // Добавляем расходы
            for (Expense expense : expenses) {
                printer.printRecord("Расход", expense.getId(), expense.getUserName(), expense.getAmount(), expense.getDescription(), expense.getDateTime(), expense.getPoint().getName());
            }
        }
        return outputStream.toByteArray();
    }

    public byte[] exportIncomesAndExpensesToXls(List<Income> incomes, List<Expense> expenses) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Доходы и расходы");

        // Создание заголовков столбцов
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Тип");
        headerRow.createCell(1).setCellValue("ID");
        headerRow.createCell(2).setCellValue("Имя пользователя");
        headerRow.createCell(3).setCellValue("Сумма");
        headerRow.createCell(4).setCellValue("Описание");
        headerRow.createCell(5).setCellValue("Дата и время");
        headerRow.createCell(6).setCellValue("Точка");

        // Заполнение данными
        int rowNum = 1;
        for (Income income : incomes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("Доход");
            row.createCell(1).setCellValue(income.getId());
            row.createCell(2).setCellValue(income.getUserName());
            row.createCell(3).setCellValue(income.getAmount().doubleValue());
            row.createCell(4).setCellValue(income.getDescription());
            row.createCell(5).setCellValue(income.getDateTime().toString());
            row.createCell(6).setCellValue(income.getPoint().getName());
        }

        for (Expense expense : expenses) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("Расход");
            row.createCell(1).setCellValue(expense.getId());
            row.createCell(2).setCellValue(expense.getUserName());
            row.createCell(3).setCellValue(expense.getAmount().doubleValue());
            row.createCell(4).setCellValue(expense.getDescription());
            row.createCell(5).setCellValue(expense.getDateTime().toString());
            row.createCell(6).setCellValue(expense.getPoint().getName());
        }

        // Автоматическое изменение размера столбцов
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        // Запись данных в байтовый массив
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }
}

