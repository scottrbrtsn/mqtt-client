package com.scottrbrtsn.mqtt.client.services.impl;

import com.scottrbrtsn.mqtt.client.domain.Mqtt;
import com.scottrbrtsn.mqtt.client.ras.IMqttRepository;
import com.scottrbrtsn.mqtt.client.sensors.EngineTemperatureSensor;
import com.scottrbrtsn.mqtt.client.services.IMqttService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class MqttService implements IMqttService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    IMqttRepository mqttRepository;

    @Override
    public String sendMessage(){
        IMqttClient publisher = this.newClient("tcp://0.0.0.0:1883");
        if (publisher != null){
            EngineTemperatureSensor engineTemperatureSensor = new EngineTemperatureSensor(publisher);
            try {
                engineTemperatureSensor.call();
            }catch (Exception e){
                LOGGER.error("Unable to read sensor");
            } finally {
                try {
                    publisher.disconnect();
                    publisher.close();
                }catch (MqttException me){
                    LOGGER.error("Couldn't close the client.");
                    LOGGER.error(me.getMessage());
                }
            }
            return "SUCCESS";
        }
        return "FAILED to create publisher";
    }

    @Override
    public List<String> receiveMessage(){
        IMqttClient subscriber = this.newClient("tcp://0.0.0.0:1883");
        CountDownLatch receivedSignal = new CountDownLatch(10);
        List<String> toReturn = new ArrayList<>();
        Mqtt mqttMsg;
        if(subscriber != null) {
            try {
                subscriber.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
                    byte[] payload = msg.getPayload();
                    LOGGER.info("PAYLOAD[]: {}", payload);
                    LOGGER.info("PAYLOAD: {}", msg.toString());
                    receivedSignal.countDown();
                });
                boolean result = !receivedSignal.await(1, TimeUnit.MINUTES);
                if(result){
                   LOGGER.info("the count reached zero ");
                }else{
                    LOGGER.info("the waiting time elapsed before the count reached zero");
                }
            } catch (MqttException me) {
                LOGGER.error("Mqtt failed to subsribe");
            } catch (InterruptedException ie){
                Thread.currentThread().interrupt();
                LOGGER.error("The signal was interrupted");
            } finally {
                try {
                    subscriber.disconnect();
                    subscriber.close();
                }catch (MqttException me){
                    LOGGER.error("Couldn't close the client.");
                    LOGGER.error(me.getMessage());
                }
            }

        }
        return toReturn;
    }

    private IMqttClient newClient(String serverURI){
        String publisherId = UUID.randomUUID().toString();
        IMqttClient client = null;
        try {
            client = new MqttClient(serverURI, publisherId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
        }catch (MqttException e){
            LOGGER.error("Error connecting the MqttClient");
        }
        return client;
    }

}