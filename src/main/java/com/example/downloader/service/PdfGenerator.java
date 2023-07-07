package com.example.downloader.service;

import com.example.downloader.entity.Transaction;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;

@Component
public interface PdfGenerator {
    public void generatePdfWithHttpServletResponse(List<Transaction> transactions, OutputStream outputStream) throws DocumentException;
    public ByteArrayResource generatePdfWithResponseEntity(List<Transaction> transactions) throws DocumentException;
}
