package com.weddingplanner.wedding_planner.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private String id;
    private String bookingId;
    private Double amount;
    private String status;
    private String transactionId;
    private LocalDateTime paymentDate;
    private String razorpayOrderId;
    private String currency;
}