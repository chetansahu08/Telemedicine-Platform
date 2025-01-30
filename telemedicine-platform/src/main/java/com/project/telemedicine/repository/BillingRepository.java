package com.project.telemedicine.repository;

import com.project.telemedicine.model.Billing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillingRepository extends MongoRepository<Billing, String> {}
