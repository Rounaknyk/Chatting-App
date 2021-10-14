package com.example.connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class stview extends AppCompatActivity {
    Timer timer;
    String img, tit,des;
    TextView loadview;
    ImageView pic;
    TextView t, d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stview);
        getSupportActionBar().hide();
        t = findViewById(R.id.vtit);
        d = findViewById(R.id.vdes);
        loadview= findViewById(R.id.textView19);
        pic = findViewById(R.id.vimg);
        img = getIntent().getStringExtra("vimg");
        tit = getIntent().getStringExtra("vtit");
        des = getIntent().getStringExtra("vdes");
        Picasso.get().load(img).resize(335,320).into(pic);
        t.setText(tit);
        d.setText(des);

            timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        startActivity(new Intent(stview.this, frag1.class));

                    }
                }, 5000);
       // Toast.makeText(this, ""+load, Toast.LENGTH_SHORT).show();
    }
}