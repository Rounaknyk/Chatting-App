package com.example.connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer, timer2;
    CardView cd1, cd2,cd3,cd4,cd5,cd6,cd7,cd8;
    ImageView logo;
    int i =  0;
    int r = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        timer2 = new Timer();
        timer = new Timer();


        logo = findViewById(R.id.logo);
//        cd1 = findViewById(R.id.cd1);
//        cd2 = findViewById(R.id.cd3);
//        cd3 = findViewById(R.id.cd4);
//        cd4 = findViewById(R.id.cd5);
//        cd5 = findViewById(R.id.cd6);
//        cd6 = findViewById(R.id.cd7);
//        cd7 = findViewById(R.id.cd8);
//        cd8 = findViewById(R.id.cd2);
//        cd1.setVisibility(View.GONE);
//        cd2.setVisibility(View.GONE);
//        cd3.setVisibility(View.GONE);
//        cd4.setVisibility(View.GONE);
//        cd5.setVisibility(View.GONE);
//        cd6.setVisibility(View.GONE);
//        cd6.setVisibility(View.GONE);
//        cd7.setVisibility(View.GONE);
//        cd8.setVisibility(View.GONE);
    for(i=0;i<=2000;i+=100) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //      Toast.makeText(MainActivity.this, ""+ r, Toast.LENGTH_SHORT).show();

                logo.setRotation(r);
                r += 20;
                if(r==400){
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                }
                //Toast.makeText(MainActivity.this, "h", Toast.LENGTH_SHORT).show();

            }
        }, i);

    }

    }
}