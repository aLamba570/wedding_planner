package com.weddingplanner.wedding_planner.repository;

import com.weddingplanner.wedding_planner.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByBookingId(String bookingId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByPaymentMethod(Payment.PaymentMethod paymentMethod);
}