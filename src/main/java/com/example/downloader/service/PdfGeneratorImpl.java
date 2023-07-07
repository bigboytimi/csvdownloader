package com.example.downloader.service;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.example.downloader.entity.Transaction;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class PdfGeneratorImpl implements PdfGenerator {
    @Override
    public void generatePdfWithHttpServletResponse(List<Transaction> transactions, OutputStream outputStream) throws DocumentException {
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

    @Override
    public ByteArrayResource generatePdfWithResponseEntity(List<Transaction> transactions) throws DocumentException {
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
