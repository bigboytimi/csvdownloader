package com.example.downloader.service;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface PdfDownloader {
    public ResponseEntity<ByteArrayResource> downloadPdf1() throws DocumentException, IOException;
    public void downloadPdf2(HttpServletResponse response) throws DocumentException, IOException;

}
