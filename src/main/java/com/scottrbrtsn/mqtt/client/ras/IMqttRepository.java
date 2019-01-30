package com.scottrbrtsn.mqtt.client.ras;

import com.scottrbrtsn.mqtt.client.domain.Mqtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMqttRepository extends JpaRepository<Mqtt, String> {

    List<Mqtt> findById(long id);

}
