package com.example.examples.androidprojectsemester4;

import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqtt.MqttHumidity;
import mqtt.MqttReceiver;

public class Humidity extends AppCompatActivity implements MqttCallback
{
    private TextView mLivingRoom;
    private ProgressBar SpinView;
    private ImageView view;
    private Handler mHandler;
    private int level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        Toolbar mytoolbar=(Toolbar)findViewById(R.id.ToolBar);
        setSupportActionBar(mytoolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        view=(ImageView) findViewById(R.id.imageView);

        // mqtt
/*

        view=(ImageView) findViewById(R.id.imageView);
        */


        mLivingRoom = (TextView) findViewById(R.id.living_room);
        SpinView=(ProgressBar)findViewById(R.id.progressBar);
        SpinView.setMax(100);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(msg.toString().length()>=4) {
                    String humidity = msg.obj.toString().substring(0, 4);
                    int humiditylevel = Integer.parseInt(humidity.substring(0,2));
                    mLivingRoom.setText(humidity);
                    SpinView.setProgress(humiditylevel,true);
                    view.getBackground().setLevel(humiditylevel*100);
                }
            }
        };
    }
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage)
    {
        Message message = mHandler.obtainMessage(0, mqttMessage);
        message.sendToTarget();
        mLivingRoom = (TextView) findViewById(R.id.living_room);

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
        new MqttHumidity(this).execute();
    }
}
