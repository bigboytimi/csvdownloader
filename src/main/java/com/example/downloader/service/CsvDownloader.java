package com.example.downloader.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;


public interface CsvDownloader {
    public ResponseEntity<ByteArrayResource> generateCsv1();
    public void generateCsv2(HttpServletResponse response) throws IOException;
}
