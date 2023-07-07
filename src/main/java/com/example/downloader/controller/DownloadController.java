package com.example.downloader.controller;

import com.example.downloader.service.TransactionService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/api/v1/download")
@RequiredArgsConstructor
public class DownloadController {

        private final TransactionService transactionService;

         // using ResponseEntity
        @GetMapping("/method1")
        public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("type") String fileType) throws DocumentException, IOException {
            if(fileType.equalsIgnoreCase("csv")) {
                return transactionService.generateCsvFileV1();
            } else if(fileType.equalsIgnoreCase("pdf")){
                return transactionService.generatePdf2();
            } else{
                return ResponseEntity.badRequest().body(null);
            }
        }


        //using HttpServletResponse
        @GetMapping("/method2")
        public void downloadCsv2(@RequestParam("type") String fileType, HttpServletResponse response) throws IOException, DocumentException {
            if(fileType.equalsIgnoreCase("csv")){
                transactionService.generateCsvFileV2(response);
            } else if (fileType.equalsIgnoreCase("pdf")) {
                transactionService.generatePdf1(response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file type");
            }
        }

}

