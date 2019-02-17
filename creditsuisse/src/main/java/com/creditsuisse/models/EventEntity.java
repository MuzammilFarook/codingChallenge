package com.creditsuisse.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "EVENT")
public class EventEntity {
    @Id
    @Column(name = "ID")
    private String  id;
    @Column(name = "DURATION")
    private Long duration;
    @Column(name = "TYPE")
    private String  type;
    @Column(name = "HOST")
    private String  host;
    @Column(name = "ALERT")
    private boolean alert;
}
