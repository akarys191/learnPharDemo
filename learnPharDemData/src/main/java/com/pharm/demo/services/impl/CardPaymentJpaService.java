package com.pharm.demo.services.impl;

import com.pharm.demo.model.CardPayment;
import com.pharm.demo.repositories.CardPaymentRepository;
import com.pharm.demo.services.CardPaymentService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CardPaymentJpaService implements CardPaymentService {

    private final CardPaymentRepository cardPaymentRepository;

    public CardPaymentJpaService(CardPaymentRepository cardPaymentRepository) {
        this.cardPaymentRepository = cardPaymentRepository;
    }

    @Override
    public CardPayment findById(Long aLong) {
        return this.cardPaymentRepository.findById(aLong).orElse(null);
    }

    @Override
    public CardPayment save(CardPayment cardPayment) {
        return cardPaymentRepository.save(cardPayment);
    }

    @Override
    public CardPayment saveFlush(CardPayment object) {
        return this.cardPaymentRepository.saveAndFlush(object);
    }

    @Override
    public Set<CardPayment> findAll() {
        System.out.println("ALL categories of Med  in JPA found@@@@@@@@ ");
        Set<CardPayment> cardPaymentSet = new HashSet<>();
        cardPaymentRepository.findAll().forEach(cardPaymentSet::add);

        return cardPaymentSet;
    }

    @Override
    public void delete(CardPayment object) {
        cardPaymentRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        cardPaymentRepository.deleteById(aLong);
    }
}
