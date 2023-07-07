package com.example.downloader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    private String trxnReference;
    private double amount;
    private LocalDateTime dateCreated;
    private String trxnType;
    private String customerName;
}
