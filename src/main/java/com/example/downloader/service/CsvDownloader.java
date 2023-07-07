package com.example.downloader.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface CsvDownloader {
    public ResponseEntity<ByteArrayResource> generateCsvFileV1();
    public void generateCsvFileV2(HttpServletResponse response) throws IOException;
}
