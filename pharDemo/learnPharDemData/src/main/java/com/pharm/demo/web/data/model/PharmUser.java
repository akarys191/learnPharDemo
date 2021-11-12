package com.pharm.demo.web.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PHARM_USER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE")
public class PharmUser extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name="user_id_generator", sequenceName = "user_id_seq", allocationSize=50)
    @Column(name = "USER_ID")
    private Long id;
    private String userName;
    private String email;
    private String password;
    private String roles;
    private LocalDate birthday;
    private String firstName;
    private String lastName;
    private String address;


    public String getName() {
        return firstName + " " + lastName;
    }
}
