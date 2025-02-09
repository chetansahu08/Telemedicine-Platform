package com.project.telemedicine.service;


import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${razorpay.key_id}")
    private String razorpayKey;

    @Value("${razorpay.key_secret}")
    private String razorpaySecret;

    public String createPaymentOrder(double amount) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int) (amount * 100)); // Razorpay requires amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }
}
