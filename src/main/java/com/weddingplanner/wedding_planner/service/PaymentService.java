package com.weddingplanner.wedding_planner.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.weddingplanner.wedding_planner.dto.PaymentDTO;
import com.weddingplanner.wedding_planner.exception.PaymentProcessingException;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.mapper.PaymentMapper;
import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.model.Payment;
import com.weddingplanner.wedding_planner.repository.BookingRepository;
import com.weddingplanner.wedding_planner.repository.PaymentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Value("${razorpay.currency}")
    private String currency;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;


    @Transactional
    public PaymentDTO createPaymentOrder(String bookingId, Double amount) {
        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Convert to paisa
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", "rcpt_" + bookingId);
            orderRequest.put("payment_capture", 1);

            Order order = razorpayClient.orders.create(orderRequest);

            // Create payment record
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setAmount(amount);
            payment.setStatus(Payment.PaymentStatus.PENDING);
            payment.setTransactionId(order.get("id"));
            payment.setPaymentDate(LocalDateTime.now());

            Payment.PaymentDetails details = new Payment.PaymentDetails();
            details.setCurrency(currency);
            details.setPaymentGatewayResponse(order.toString());
            payment.setDetails(details);

            Payment savedPayment = paymentRepository.save(payment);

            PaymentDTO paymentDTO = mapToDTO(savedPayment);
            paymentDTO.setRazorpayOrderId(order.get("id"));
            return paymentDTO;

        } catch (RazorpayException e) {
            throw new PaymentProcessingException("Failed to create payment order: " + e.getMessage());
        }
    }

    @Transactional
    public PaymentDTO verifyAndUpdatePayment(String paymentId, String razorpayPaymentId, String razorpaySignature) {
        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

            // Create the signature verification data
            String orderId = payment.getTransactionId(); // This should be the Razorpay order ID
            String data = orderId + "|" + razorpayPaymentId;

            // Verify the payment signature
            boolean isValidSignature = Utils.verifySignature(
                    data,
                    razorpaySignature,
                    razorpayKeySecret
            );

            if (isValidSignature) {
                payment.setStatus(Payment.PaymentStatus.COMPLETED);
                payment.getDetails().setPaymentGatewayResponse("Payment Verified");
                payment.setUpdatedAt(LocalDateTime.now());
                payment.getDetails().setPaymentGatewayId(razorpayPaymentId);
                return mapToDTO(paymentRepository.save(payment));
            } else {
                throw new PaymentProcessingException("Invalid payment signature");
            }

        } catch (Exception e) {
            throw new PaymentProcessingException("Payment verification failed: " + e.getMessage());
        }
    }

    @Transactional
    public PaymentDTO getPaymentById(String id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        return mapToDTO(payment);
    }

    private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setBookingId(payment.getBookingId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(String.valueOf(payment.getStatus()));
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }
}