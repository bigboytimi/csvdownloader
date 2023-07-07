package com.example.downloader.controller;

import com.example.downloader.service.CsvDownloader;
import com.example.downloader.service.PdfDownloader;
import com.example.downloader.service.implementation.TransactionService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/download")
public class DownloadController {
    private final PdfDownloader pdfDownloader;
    private final CsvDownloader csvDownloader;

    public DownloadController(PdfDownloader pdfDownloader, CsvDownloader csvDownloader) {
        this.pdfDownloader = pdfDownloader;
        this.csvDownloader = csvDownloader;
    }

    // using ResponseEntity
    @GetMapping("/method1")
    public ResponseEntity<ByteArrayResource> downloadMethod1(@RequestParam("type") String fileType) throws DocumentException, IOException {
        if(fileType.equalsIgnoreCase("csv")) {
            return csvDownloader.generateCsvFileV1();
        } else if(fileType.equalsIgnoreCase("pdf")){
            return pdfDownloader.downloadPdf1();
        } else{
            return ResponseEntity.badRequest().body(null);
        }
    }


    //using HttpServletResponse
    @GetMapping("/method2")
    public void downloadMethod2(@RequestParam("type") String fileType, HttpServletResponse response) throws IOException, DocumentException {
        if(fileType.equalsIgnoreCase("csv")){
            csvDownloader.generateCsvFileV2(response);
        } else if (fileType.equalsIgnoreCase("pdf")) {
            pdfDownloader.downloadPdf2(response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file type");
        }
    }

}

