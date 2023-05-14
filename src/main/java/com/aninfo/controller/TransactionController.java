package com.aninfo.controller;

import com.aninfo.model.Transaction;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/accounts/{cbu}/transactions")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Transaction> getTransactionsByCBU(@PathVariable Long cbu) {
        return transactionService.getAllTransactionByCBU(cbu);
    }

    @GetMapping("/transactions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @DeleteMapping("/transactions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
    }
}
