package com.example.examples.androidprojectsemester4;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqtt.MqttReceiver;

public class MainActivity extends AppCompatActivity implements MqttCallback
{
    private TextView mLivingRoom;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // toolbar
       // Toolbar mytoolbar=(Toolbar)findViewById(R.id.ToolBar);
       // setSupportActionBar(mytoolbar);
       // ActionBar ab=getSupportActionBar();
       // ab.setDisplayHomeAsUpEnabled(true);
        // mqtt
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber);
        mLivingRoom = (TextView) findViewById(R.id.living_room);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                mLivingRoom.setText(msg.obj.toString());
            }
        };
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage)
    {
        Message message = mHandler.obtainMessage(0, mqttMessage);
        message.sendToTarget();
    }
    @Override
    public void connectionLost(Throwable throwable) {
        Toast.makeText(this, "Connection lost with the broker",
                Toast.LENGTH_SHORT);
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // noop
    }

    @Override
    protected void onResume() {
        super.onResume();
        new MqttReceiver(this).execute();
    }
}
