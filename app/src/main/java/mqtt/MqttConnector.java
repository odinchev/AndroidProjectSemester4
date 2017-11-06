package mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by PC on 31.10.2017 г..
 */

public class MqttConnector
{
    private final static String BROKER_URL =
            "tcp://m20.cloudmqtt.com:12483";
    private static final String USERNAME = "abcdefgh";
    private static final String PASSWORD = "aB1CDEfGHiLM";
    private MqttConnectOptions mOptions;
    private MqttClient mClient;
    private String mTopic;

    public MqttConnector(String clientId) throws MqttException {
        mOptions = new MqttConnectOptions();
        mOptions.setCleanSession(true);
        mOptions.setUserName(USERNAME);
        mOptions.setPassword(PASSWORD.toCharArray());
        MemoryPersistence persistence = new MemoryPersistence();
        mClient = new MqttClient(BROKER_URL, clientId, persistence);
    }




    public void connect() throws MqttException {
        mClient.connect(mOptions);
    }
    public void disconnect() throws MqttException {
        mClient.disconnect();
    }



    public void setTopic(String topic) {
        mTopic = topic;
    }
    public void setCallback(MqttCallback callback) {
        mClient.setCallback(callback);
    }


    public void publish(byte[] message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message);
        mqttMessage.setQos(0);
        mClient.publish(mTopic, mqttMessage);
    }

    public void subscribe() throws MqttException {
        mClient.subscribe(mTopic);
    }

}

