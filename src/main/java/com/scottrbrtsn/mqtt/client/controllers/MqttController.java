package com.scottrbrtsn.mqtt.client.controllers;

import com.scottrbrtsn.mqtt.client.ras.IMqttRepository;
import com.scottrbrtsn.mqtt.client.services.IMqttService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/mqtt")
public class MqttController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttController.class);

    @Autowired
    private IMqttService mqttService;

    @Autowired
    private IMqttRepository logsRepository;

    @GetMapping(value = "/receiveMessage")
    public ResponseEntity<List<String>> getMessage() {
        LOGGER.debug("getMessage");
        return new ResponseEntity<>(mqttService.receiveMessage(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/sendMessage")
    public ResponseEntity<String> sendMessage() {
        LOGGER.debug("getMessage");
        return new ResponseEntity<>(mqttService.sendMessage(), new HttpHeaders(), HttpStatus.OK);
    }
}