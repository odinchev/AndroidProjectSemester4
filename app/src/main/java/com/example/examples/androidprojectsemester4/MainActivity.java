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

import mqtt.MqttReceiver;

public class MainActivity extends AppCompatActivity implements MqttCallback
{
    private TextView mLivingRoom;
    private ProgressBar SpinView;
    private ImageView view;
    private Handler mHandler;
    private int level;
private TextView humidityview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // toolbar
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subscriber);
        Toolbar mytoolbar=(Toolbar)findViewById(R.id.ToolBar);
        setSupportActionBar(mytoolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        // mqtt
humidityview = (TextView)findViewById(R.id.humidityview);
        mLivingRoom = (TextView) findViewById(R.id.living_room);
        SpinView=(ProgressBar)findViewById(R.id.progressBar);
          view=(ImageView) findViewById(R.id.imageView);
        SpinView.setMax(100);


        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(msg.toString().length()>=4) {
                    String temperature = msg.obj.toString().substring(0, 4);
                    int graphTemperature = Integer.parseInt(temperature.substring(0,2));
                    mLivingRoom.setText(temperature);
                    SpinView.setProgress(graphTemperature,true);
                    view.getBackground().setLevel(graphTemperature*100);
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
    public void messageArrived2(String topic2, MqttMessage mqttMessage2)
    {
        Message message2 = mHandler.obtainMessage(0, mqttMessage2);
        message2.sendToTarget();
      humidityview = (TextView) findViewById(R.id.humidityview);

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
