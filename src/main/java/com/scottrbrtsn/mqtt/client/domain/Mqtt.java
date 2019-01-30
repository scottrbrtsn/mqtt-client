package com.scottrbrtsn.mqtt.client.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
@Data
public class Mqtt {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column
    private long id;

    @Column
    private String message;

}
