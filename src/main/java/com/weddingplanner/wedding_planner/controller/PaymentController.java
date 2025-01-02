package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.PaymentDTO;
import com.weddingplanner.wedding_planner.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentDTO> createPaymentOrder(
            @RequestParam String bookingId,
            @RequestParam Double amount) {
        return ResponseEntity.ok(paymentService.createPaymentOrder(bookingId, amount));
    }

    @PostMapping("/verify")
    public ResponseEntity<PaymentDTO> verifyPayment(
            @RequestParam String paymentId,
            @RequestParam String razorpayPaymentId,
            @RequestParam String razorpaySignature) {
        return ResponseEntity.ok(paymentService.verifyAndUpdatePayment(
                paymentId, razorpayPaymentId, razorpaySignature));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
}