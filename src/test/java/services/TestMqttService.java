package services;

import com.scottrbrtsn.mqtt.client.ras.IMqttRepository;
import com.scottrbrtsn.mqtt.client.services.impl.MqttService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class TestMqttService {

    @Mock
    IMqttRepository logsRepository;

    @InjectMocks
    MqttService mqttService;

    @Test
    public void testReadFile() {
        String test = mqttService.sendMessage();
        assertEquals("SUCCESS", test);
    }

}
