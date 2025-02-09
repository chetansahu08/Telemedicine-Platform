package com.project.telemedicine.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${razorpay.keyId}")
    private String razorpayKeyId;

    @Value("${razorpay.keySecret}")
    private String razorpayKeySecret;

    @Value("${razorpay.currency}")
    private String currency;

    public String createPaymentOrder(double amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // Amount in paise
        options.put("currency", currency);
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1);

        Order order = razorpay.orders.create(options);
        return order.toString(); // Returning order details
    }
}
