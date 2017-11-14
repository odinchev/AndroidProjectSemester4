package com.example.examples.androidprojectsemester4;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by PC on 14.11.2017 г..
 */

public class Starter extends Service {


        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            // do your jobs here
            return super.onStartCommand(intent, flags, startId);
        }
    }

