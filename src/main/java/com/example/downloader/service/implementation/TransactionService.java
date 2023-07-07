package com.example.downloader.service.implementation;

import com.example.downloader.entity.Transaction;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("TXN001", 100.00, LocalDateTime.now(), "CREDIT", "Timi Olowookere"));
        transactions.add(new Transaction("TXN002", 50.00, LocalDateTime.now(), "DEBIT", "Muhammad Gambo"));
        transactions.add(new Transaction("TXN003", 75.00, LocalDateTime.now(), "CREDIT", "Victor Gregory"));
        return transactions;
    }
}