package com.example.excel.controller;

import com.example.excel.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
@AllArgsConstructor
public class ExcelController {
    private final ExpenseService expenseService;

//    @GetMapping("/get")
//    public ResponseEntity<byte[]> exportTest() throws IOException {
//        List<Expense> allExpenses = expenseService.getAllExpenses();
//        byte[] tables = ExcelService.exportExpenseXls(allExpenses, "Table");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//        httpHeaders.setContentDispositionFormData("attachment", "sasha.xlsx");
//        httpHeaders.setContentLength(tables.length);
//        return new ResponseEntity<>(tables, httpHeaders, HttpStatus.OK);
//    }

//    @GetMapping("/get")
//    public ResponseEntity<byte[]> exportTest() throws IOException, IllegalAccessException {
//        List<Expense> allExpenses = expenseService.getAllExpenses();
//        byte[] tables = ExcelService.exportToXls(allExpenses, "Table");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//        httpHeaders.setContentDispositionFormData("attachment", "sasha.xlsx");
//        httpHeaders.setContentLength(tables.length);
//        return new ResponseEntity<>(tables, httpHeaders, HttpStatus.OK);
//    }
}
