package com.project.telemedicine.service.impl;

import com.project.telemedicine.exception.ResourceNotFoundException;
import com.project.telemedicine.model.Billing;
import com.project.telemedicine.repository.BillingRepository;
import com.project.telemedicine.service.BillingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository BillingRepository;

    @Override
    public List<Billing> getAllBillings() {
        return BillingRepository.findAll();
    }

    @Override
    public Billing getBillingById(String id) {
        return BillingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billing not found with id: " + id));
    }

    @Override
    public Billing createBilling(Billing Billing) {
        return BillingRepository.save(Billing);
    }

    @Override
    public Billing updateBilling(String id, Billing Billing) {
        Billing existingBilling = getBillingById(id);
        existingBilling.setAmount(Billing.getAmount());
        existingBilling.setPaymentStatus(Billing.getPaymentStatus());
        return BillingRepository.save(existingBilling);
    }

    @Override
    public void deleteBilling(String id) {
        Billing existingBilling = getBillingById(id);
        BillingRepository.delete(existingBilling);
    }
}

