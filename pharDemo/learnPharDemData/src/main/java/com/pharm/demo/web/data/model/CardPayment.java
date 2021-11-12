package com.pharm.demo.web.data.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardPayment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cash_expense_id_generator")
    @SequenceGenerator(name = "cash_expense_id_generator", sequenceName = "cash_expense_id_seq", allocationSize = 50)
    private Long cardPaymentId;

    private Double paymentAmount = 0.0;
    private LocalDateTime paymentDate = LocalDateTime.now();
    @OneToOne
    private Card card;

    @ManyToOne
    Sales sales;
}
