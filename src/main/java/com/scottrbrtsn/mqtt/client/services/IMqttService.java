package com.scottrbrtsn.mqtt.client.services;

import java.util.List;

public interface IMqttService {

    String sendMessage();

    List<String> receiveMessage();
}
