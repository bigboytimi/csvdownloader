package com.example.downloader.service.implementation;

import com.example.downloader.entity.Transaction;
import com.example.downloader.service.PdfDownloader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PdfDownloaderImpl implements PdfDownloader {

    private final TransactionService transactionService;

    @Override
    public void downloadPdf2(HttpServletResponse response) throws DocumentException, IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        List<Transaction> transactions = transactionService.getAllTransactions();
        String fileType = "attachment; filename=transaction_details_" + dateFormat.format(new Date()) + ".pdf";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType("application/pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            generatePdfWithHttpServletResponse(transactions, outputStream);
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadPdf1() throws DocumentException, IOException {
        List<Transaction> transactions = transactionService.getAllTransactions();

        // Generate PDF content as byte array
        ByteArrayResource pdfContent = generatePdfWithResponseEntity(transactions);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "transactions.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);

    }


    //Helper Methods

    // Generate PDF for HttpServletResponse.
    private void generatePdfWithHttpServletResponse(List<Transaction> transactions, OutputStream outputStream) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        for (Transaction transaction : transactions) {
            document.add(new Paragraph("Transaction Reference: " + transaction.getTrxnReference()));
            document.add(new Paragraph("Amount: " + transaction.getAmount()));
            document.add(new Paragraph("Date Created: " + transaction.getDateCreated()));
            document.add(new Paragraph("Transaction Type: " + transaction.getTrxnType()));
            document.add(new Paragraph("Customer Name: " + transaction.getCustomerName()));
            document.add(new Paragraph("\n"));
        }

        document.close();
    }

    // Generate PDF for ResponseEntity.
    private ByteArrayResource generatePdfWithResponseEntity(List<Transaction> transactions) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        for (Transaction transaction : transactions) {
            document.add(new Paragraph("Transaction Reference: " + transaction.getTrxnReference()));
            document.add(new Paragraph("Amount: " + transaction.getAmount()));
            document.add(new Paragraph("Date Created: " + transaction.getDateCreated()));
            document.add(new Paragraph("Transaction Type: " + transaction.getTrxnType()));
            document.add(new Paragraph("Customer Name: " + transaction.getCustomerName()));
            document.add(new Paragraph("\n"));
        }

        document.close();
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
