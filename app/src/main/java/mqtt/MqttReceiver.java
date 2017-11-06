package mqtt;

import android.os.AsyncTask;

import com.example.examples.androidprojectsemester4.MainActivity;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by PC on 31.10.2017 г..
 */

public class MqttReceiver extends AsyncTask<Void, Void, Void> {
    private MainActivity mCaller;

    public MqttReceiver(MainActivity caller) {
        this.mCaller = caller;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            MqttConnector client = new MqttConnector("receiver");
            client.setTopic("/home/living-room/temperature");
            client.connect();
            client.setCallback(mCaller);
            client.subscribe();
        }
        catch (MqttException e) {
            // manage connection errors
        }
        return null;
    }


}
