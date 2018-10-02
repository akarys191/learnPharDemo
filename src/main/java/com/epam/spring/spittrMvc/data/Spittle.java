package com.epam.spring.spittrMvc.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Spittle {
    private final Long id;
    private final String message;
    private final LocalDateTime time;
    private Double latitude;
    private Double longitude;

    public Spittle(Long id,String message, LocalDateTime time) {
        this(id,message, time, null, null);
    }

    public Spittle(Long id, String message, LocalDateTime time, Double longitude, Double latitude) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {        return id;    }

    public String getMessage() {        return message;    }

    public LocalDateTime getTime() {        return time;    }

    public Double getLatitude() {        return latitude;    }
    public void setLatitude(Double latitude) {        this.latitude = latitude;    }

    public Double getLongitude() {        return longitude;    }
    public void setLongitude(Double longitude) {        this.longitude = longitude;    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "id", "time");
    }
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id", "time");
    }
}