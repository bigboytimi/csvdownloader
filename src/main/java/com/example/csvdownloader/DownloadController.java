package com.example.csvdownloader;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

    private final TransactionService transactionService;

    private final String currentDateTime = getSimpleDateFormat().format(new Date());


    // using ResponseEntity.
    @GetMapping("/csv-1")
    public ResponseEntity<ByteArrayResource> downloadCsv1() {
        List<Transaction> transactions = transactionService.getAllTransactions();

        StringBuilder transactionDetails = new StringBuilder();
        transactionDetails.append("trxnReference, amount, dateCreated, trxnType, customerName \n");

        for (Transaction transaction : transactions) {
            transactionDetails.append(transaction.getTrxnReference()).append(",")
                    .append(transaction.getAmount()).append(",")
                    .append(getSimpleDateFormat().format(transaction.getDateCreated())).append(",")
                    .append(transaction.getTrxnType()).append(",")
                    .append(transaction.getCustomerName()).append("\n");
        }


            byte[] transactionCsvBytes = transactionDetails.toString().getBytes();

            ByteArrayResource transactionCsv = new ByteArrayResource(transactionCsvBytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .contentLength(transactionCsvBytes.length)
                    .body(transactionCsv);
        }


        //using HttpServletResponse
        @GetMapping("/csv-2")
        public void downloadCsv2(HttpServletResponse response) throws IOException {
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
                        .append(getSimpleDateFormat().format(transaction.getDateCreated())).append(",")
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

