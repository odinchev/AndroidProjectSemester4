package mqtt;

import android.os.AsyncTask;

import com.example.examples.androidprojectsemester4.Humidity;
import com.example.examples.androidprojectsemester4.MainActivity;

import org.eclipse.paho.client.mqttv3.MqttException;

    /**
     * Created by PC on 31.10.2017 г..
     */

    public class MqttHumidity extends AsyncTask<Void, Void, Void> {

        private Humidity Caller;


        public MqttHumidity(Humidity caller) {
            this.Caller = caller;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MqttConnector client = new MqttConnector("receiver");
                client.setTopic("sensor");
                client.connect();
                client.setCallback(Caller);
                client.subscribe();
            }
            catch (MqttException e) {
                // manage connection errors
            }
            // humidity topic
            try {
                MqttConnector client = new MqttConnector("receiver");
                client.setTopic("humidity");
                client.connect();
                client.setCallback(Caller);
                client.subscribe();
            }
            catch (MqttException e) {
                // manage connection errors
            }
            return null;
        }


    }


