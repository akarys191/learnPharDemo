package com.pharm.demo.web.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class AbstractEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;
}
