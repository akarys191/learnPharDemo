package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
