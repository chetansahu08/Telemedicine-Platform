package com.project.telemedicine.controller;

import com.razorpay.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${razorpay.keyId}")
    private String razorpayKeyId;

    @Value("${razorpay.keySecret}")
    private String razorpayKeySecret;

    // ✅ Create Payment Order
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> paymentRequest) {
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            JSONObject options = new JSONObject();
            options.put("amount", paymentRequest.get("amount")); // Amount in paise
            options.put("currency", "INR");
            options.put("receipt", "order_rcptid_" + paymentRequest.get("patientId"));
            options.put("payment_capture", 1);

            Order order = razorpay.orders.create(options);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            return ResponseEntity.ok(response);
        } catch (RazorpayException e) {
            return ResponseEntity.badRequest().body("Error creating payment order: " + e.getMessage());
        }
    }

    // ✅ Verify Payment after success
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentData) {
        try {
            String orderId = paymentData.get("orderId");
            String paymentId = paymentData.get("paymentId");
            String razorpaySignature = paymentData.get("razorpaySignature");

            System.out.println("🔹 Received from frontend:");
            System.out.println("Order ID: " + orderId);
            System.out.println("Payment ID: " + paymentId);
            System.out.println("Signature: " + razorpaySignature);

            if (orderId == null || paymentId == null || razorpaySignature == null) {
                return ResponseEntity.badRequest().body("❌ Missing required fields for verification.");
            }

            // ✅ Compute HMAC SHA256 signature
            String generatedSignature = generateHmacSHA256(orderId + "|" + paymentId, razorpayKeySecret);

            System.out.println("🔹 Expected Signature: " + generatedSignature);

            // ✅ Compare computed signature with Razorpay's signature
            if (generatedSignature.equals(razorpaySignature)) {
                System.out.println("✅ Payment verification successful!");
                return ResponseEntity.ok(Map.of("message", "Payment verified successfully!", "paymentId", paymentId));
            } else {
                System.out.println("❌ Payment verification failed: Invalid signature.");
                return ResponseEntity.status(400).body("❌ Payment verification failed: Invalid signature.");
            }

        } catch (Exception e) {
            System.out.println("❌ Payment verification error: " + e.getMessage());
            return ResponseEntity.status(500).body("❌ Payment verification error: " + e.getMessage());
        }
    }


    // ✅ Generate HMAC SHA256 signature
    private String generateHmacSHA256(String data, String secret) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKey);
        return Hex.encodeHexString(sha256Hmac.doFinal(data.getBytes()));  // ✅ Correct encoding
    }
}
