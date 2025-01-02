package com.weddingplanner.wedding_planner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "payments")
public class Payment {
    @Id
    private String id;
    private String bookingId;
    private Double amount;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;
    private PaymentDetails details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }

    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, CASH, CRYPTO
    }

    @Data
    public static class PaymentDetails {
        private String currency;
        private String paymentGatewayResponse;
        private String paymentGatewayId;  // To store Razorpay Payment ID
        private Map<String, String> metadata;
        private String receiptUrl;
    }
}