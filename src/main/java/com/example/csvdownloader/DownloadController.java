package com.example.csvdownloader;

import com.itextpdf.text.DocumentException;
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
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/download")
@RequiredArgsConstructor
public class DownloadController {

        private final TransactionService transactionService;

         // using ResponseEntity for csv.
        @GetMapping("/csv-1")
        public ResponseEntity<ByteArrayResource> downloadCsv1() {
            return transactionService.generateCsvFileV1();
        }


        //using HttpServletResponse for csv
        @GetMapping("/csv-2")
        public void downloadCsv2(HttpServletResponse response) throws IOException {
            transactionService.generateCsvFileV2(response);
        }

        //using HttpServlet for Pdf
        @GetMapping("/pdf-1")
        public void downloadPdf1(HttpServletResponse response) throws IOException, DocumentException {
            transactionService.generatePdf1(response);


        }

        //using ResponseEntity for Pdf
        @GetMapping("/pdf-2")
        public ResponseEntity<byte[]> downloadPdf2() throws DocumentException, IOException {
            return transactionService.generatePdf2();
        }




}

