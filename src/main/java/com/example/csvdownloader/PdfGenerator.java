package com.example.csvdownloader;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {
    public static void generatePdf(List<Transaction> transactions, OutputStream outputStream) throws DocumentException {
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

    public static byte[] generatePdf1(List<Transaction> transactions) throws DocumentException, IOException {
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
        return outputStream.toByteArray();
    }


}
