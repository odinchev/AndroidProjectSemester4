package com.example.examples.androidprojectsemester4;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class Menu extends AppCompatActivity implements  CursorWheelLayout.OnMenuSelectedListener {
 private Humidity humidity;
    private MainActivity temperature;
    //@Override


    CursorWheelLayout wheel_text,wheel_image;
   // List<MenuItemData> lstText;
    List<ImageData> lstImage;

    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
/*
        Toolbar mytoolbar=(Toolbar)findViewById(R.id.ToolBar);
        setSupportActionBar(mytoolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
*/
        initViews();
        loadData();
       // wheel_text.setOnMenuSelectedListener(this);
        wheel_image.setOnMenuSelectedListener(this);


    }
    //start of init view and load data methods
    private void initViews()
    {
        wheel_image = (CursorWheelLayout)findViewById(R.id.wheel_image);
       // wheel_text=(CursorWheelLayout)findViewById(R.id.wheel_text);

    }
    private void loadData(){
        /*
        lstText = new ArrayList<>();
        for(int i=0; i<9;i++)
            lstText.add(new MenuItemData(""+i));
        lstText.add(new MenuItemData("OFF"));
        WheelTextAdapter adapter = new WheelTextAdapter(getBaseContext(),lstText);
        wheel_text.setAdapter(adapter);
*/
        lstImage = new ArrayList<>();

        lstImage.add(new ImageData(R.drawable.black," "));
        lstImage.add(new ImageData(R.drawable.fireflame,"Weather"));
        lstImage.add(new ImageData(R.drawable.black,"test"));
        lstImage.add(new ImageData(R.drawable.raindrop,"humidity"));


        WheelImageAdapter imgAdapter = new WheelImageAdapter(getBaseContext(),lstImage);
        wheel_image.setAdapter(imgAdapter);
    }






    //init views and load data methods

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    /*
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.view1:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast humidity= Toast.makeText(this,"Android Toast",Toast.LENGTH_LONG);
                humidity.show();
               Intent humidityIntent=new Intent (this,Humidity.class);
               startActivity(humidityIntent);
                return true;

            case R.id.view2:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast temperature= Toast.makeText(this,"Android Toast",Toast.LENGTH_LONG);
                temperature.show();
               Intent temperatureIntent=new Intent (this,MainActivity.class);
               startActivity(temperatureIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }


    }
*/

    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
        /*
        if(parent.getId()== R.id.wheel_text) {
            Toast.makeText(getBaseContext(), "Selected:" + lstText.get(pos).mTitle, Toast.LENGTH_SHORT).show();

            }

            */

        if(parent.getId() == R.id.wheel_image) {


            if(lstImage.get(pos).imageDescription.equals("Weather"))
            {

                Intent weather = new Intent(Menu.this, MainActivity.class);
                startActivity(weather);

                //Toast.makeText(getBaseContext(), "WEATHER:" + lstImage.get(pos).imageDescription, Toast.LENGTH_SHORT).show();

            }
            //
            if(lstImage.get(pos).imageDescription.equals("humidity"))
            {

                Intent Humidity = new Intent(Menu.this, Humidity.class);
                startActivity(Humidity);

                //Toast.makeText(getBaseContext(), "WEATHER:" + lstImage.get(pos).imageDescription, Toast.LENGTH_SHORT).show();

            }


        }


    }
}
