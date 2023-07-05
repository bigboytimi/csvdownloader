package com.example.csvdownloader;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("TXN001", 100.00, new Date(), "CREDIT", "Timi Olowookere"));
        transactions.add(new Transaction("TXN002", 50.00, new Date(), "DEBIT", "Muhammad Gambo"));
        transactions.add(new Transaction("TXN003", 75.00, new Date(), "CREDIT", "Victor Gregory"));
        return transactions;
    }
}