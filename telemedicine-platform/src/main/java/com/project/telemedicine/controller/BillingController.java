package com.project.telemedicine.controller;

import com.project.telemedicine.model.Billing;
import com.project.telemedicine.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    @Autowired
    private BillingService BillingService;

    @GetMapping
    public List<Billing> getAllBillings() {
        return BillingService.getAllBillings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillingById(@PathVariable String id) {
        return ResponseEntity.ok(BillingService.getBillingById(id));
    }

    @PostMapping
    public ResponseEntity<Billing> createBilling(@RequestBody Billing Billing) {
        return ResponseEntity.ok(BillingService.createBilling(Billing));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Billing> updateBilling(@PathVariable String id, @RequestBody Billing Billing) {
        return ResponseEntity.ok(BillingService.updateBilling(id, Billing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@PathVariable String id) {
        BillingService.deleteBilling(id);
        return ResponseEntity.noContent().build();
    }
}

