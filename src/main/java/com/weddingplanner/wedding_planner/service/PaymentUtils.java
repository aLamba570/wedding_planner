package com.weddingplanner.wedding_planner.service;

import com.razorpay.RazorpayException;
import org.json.JSONObject;

public class PaymentUtils {

    public static JSONObject createOrderRequest(Double amount, String currency, String receipt) {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Convert to paisa
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receipt);
        orderRequest.put("payment_capture", 1);
        return orderRequest;
    }

    public static String generateReceiptId(String bookingId) {
        return "rcpt_" + bookingId + "_" + System.currentTimeMillis();
    }
}