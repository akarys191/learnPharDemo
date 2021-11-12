package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardPaymentRepository extends JpaRepository<CardPayment, Long> {
}
