package com.project.telemedicine.service;

import com.project.telemedicine.model.Billing;

import java.util.List;

public interface BillingService {

    List<Billing> getAllBillings();

    Billing getBillingById(String id);

    Billing createBilling(Billing Billing);

    Billing updateBilling(String id, Billing Billing);

    void deleteBilling(String id);
}
