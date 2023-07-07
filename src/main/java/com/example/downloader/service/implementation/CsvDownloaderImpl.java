package com.example.downloader.service.implementation;

import com.example.downloader.entity.Transaction;
import com.example.downloader.service.CsvDownloader;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CsvDownloaderImpl implements CsvDownloader {

    private final TransactionService transactionService;
    private final String currentDateTime = getSimpleDateFormat().format(new Date());
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public ResponseEntity<ByteArrayResource> generateCsv1() {
        List<Transaction> transactions = transactionService.getAllTransactions();

        StringBuilder transactionDetails = new StringBuilder();
        transactionDetails.append("trxnReference, amount, dateCreated, trxnType, customerName \n");

        for (Transaction transaction : transactions) {
            transactionDetails.append(transaction.getTrxnReference()).append(",")
                    .append(transaction.getAmount()).append(",")
                    .append(transaction.getDateCreated().format(dateTimeFormatter)).append(",")
                    .append(transaction.getTrxnType()).append(",")
                    .append(transaction.getCustomerName()).append("\n");
        }


        byte[] transactionCsvBytes = transactionDetails.toString().getBytes();

        ByteArrayResource transactionCsv = new ByteArrayResource(transactionCsvBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=transactions" + currentDateTime + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(transactionCsvBytes.length)
                .body(transactionCsv);

    }

    @Override
    public void generateCsv2(HttpServletResponse response) throws IOException {
        List<Transaction> transactions = transactionService.getAllTransactions();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + currentDateTime + ".csv";
        response.setContentType("text/csv");
        response.setHeader(headerKey, headerValue);


        StringBuilder transactionDetails1 = new StringBuilder();
        transactionDetails1.append("trxnReference,amount,dateCreated,trxnType,customerName").append("\n");

        for (Transaction transaction : transactions) {
            transactionDetails1.append(transaction.getTrxnReference()).append(",")
                    .append(transaction.getAmount()).append(",")
                    .append(transaction.getDateCreated().format(dateTimeFormatter)).append(",")
                    .append(transaction.getTrxnType()).append(",")
                    .append(transaction.getCustomerName()).append("\n");
        }

        response.getWriter().write(transactionDetails1.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }


    public SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

}
