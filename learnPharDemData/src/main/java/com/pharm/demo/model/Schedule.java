package com.pharm.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
//TODO how to make dynamic creation or static assignin
public class Schedule extends CommonProperties{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_generator")
    @SequenceGenerator(name="schedule_id_generator", sequenceName = "schedule_id_seq", allocationSize=50)
    private Long scheduleId;
    private String scheduleDay;
}
