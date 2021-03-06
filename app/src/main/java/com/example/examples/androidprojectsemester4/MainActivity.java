package com.example.examples.androidprojectsemester4;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;
import mqtt.MqttReceiver;

import static com.example.examples.androidprojectsemester4.R.id.graph;

public class MainActivity extends AppCompatActivity implements MqttCallback
{
    private TextView mLivingRoom;
    private ProgressBar SpinView;
    private ImageView view;
    private Handler mHandler;
    private int level;
    int graphTemperature;
    private NotificationCompat.Builder notificationBuilder;

private int t;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber);





        Toolbar mytoolbar=(Toolbar)findViewById(R.id.ToolBar);
        setSupportActionBar(mytoolbar);



        ActionBar ab=getSupportActionBar();
       ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
//wheel


        //graph
        final GraphView graphView = (GraphView) findViewById(graph);

        final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
        series.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        series.setDrawBackground(true);


        // mqtt

        mLivingRoom = (TextView) findViewById(R.id.living_room);
        SpinView=(ProgressBar)findViewById(R.id.progressBar);
          view=(ImageView) findViewById(R.id.imageView);
        SpinView.setMax(100);


        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(msg.toString().length()>=4) {
                    String temperature = msg.obj.toString().substring(0, 4);
                     graphTemperature = Integer.parseInt(temperature.substring(0,2));
                    mLivingRoom.setText(temperature);
                    SpinView.setProgress(graphTemperature,true);
                    view.getBackground().setLevel(graphTemperature*100);

                    t++;
                    series.appendData(new DataPoint(t, graphTemperature), true, 5);
                    graphView.addSeries(series);

                    Notification();
                }
            }
        };
    }







    public void Notification() {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.fireflame)
                        .setContentTitle("Temperature")
                        .setContentText("Temperature is " + graphTemperature);
        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.view1:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
               // Toast humidity= Toast.makeText(this,"Android Toast",Toast.LENGTH_LONG);
              //  humidity.show();
               Intent humidityIntent=new Intent (this,Humidity.class);
               startActivity(humidityIntent);
                return true;

            case R.id.view2:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
               // Toast temperature= Toast.makeText(this,"Android Toast",Toast.LENGTH_LONG);
               // temperature.show();
              Intent temperatureIntent=new Intent (this,MainActivity.class);
               startActivity(temperatureIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }


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
        new MqttReceiver(this).execute();
        startService(new Intent(this, Starter.class));

    }

    private DataPoint[] getDataPoint () {
        DataPoint[] dp = new DataPoint[]{

                new DataPoint(t,graphTemperature)


        };
        return (dp);
    }

}



