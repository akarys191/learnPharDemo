package com.pharm.demo.repositories;

import com.pharm.demo.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
