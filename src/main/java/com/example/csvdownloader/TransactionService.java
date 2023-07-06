package com.example.csvdownloader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
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

    private static int random = 0;

    private final String currentDateTime = getSimpleDateFormat().format(new Date());
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("TXN001", 100.00, LocalDateTime.now(), "CREDIT", "Timi Olowookere"));
        transactions.add(new Transaction("TXN002", 50.00, LocalDateTime.now(), "DEBIT", "Muhammad Gambo"));
        transactions.add(new Transaction("TXN003", 75.00, LocalDateTime.now(), "CREDIT", "Victor Gregory"));
        return transactions;
    }

    public ResponseEntity<ByteArrayResource> generateCsvFileV1(){
        List<Transaction> transactions = getAllTransactions();

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

    public void generateCsvFileV2(HttpServletResponse response) throws IOException {
        List<Transaction> transactions = getAllTransactions();

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

    public void generatePdf1(HttpServletResponse response) throws DocumentException, IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        List<Transaction> transactions = getAllTransactions();
        String fileType = "attachment; filename=transaction_details_" + dateFormat.format(new Date()) + ".pdf";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType("application/pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            PdfGenerator.generatePdf(transactions, outputStream);
        }
    }

    public ResponseEntity<byte[]> generatePdf2() throws DocumentException, IOException {
        List<Transaction> transactions = getAllTransactions();

        // Generate PDF content as byte array
        byte[] pdfContent = PdfGenerator.generatePdf1(transactions);

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "transactions.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);

    }
    public SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}