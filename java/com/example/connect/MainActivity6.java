package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity6 extends AppCompatActivity implements  View.OnClickListener {
    ImageButton search;
    EditText input;
        ImageView simg;
        TextView sname, sdes;
        CardView cd;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Active");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String name, n , uid, url, des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        getSupportActionBar().hide();
        input = findViewById(R.id.input);
       search = findViewById(R.id.s);
       cd = findViewById(R.id.cd);
       sname = findViewById(R.id.sname);
       sdes = findViewById(R.id.sdes);
       simg = findViewById(R.id.simg);
       cd.setVisibility(View.GONE);
       cd.setOnClickListener(this);
       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               name = input.getText().toString();
               cd.setVisibility(View.VISIBLE);
               ref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       n = snapshot.child(name).child("name").getValue().toString();
                       url = snapshot.child(name).child("url").getValue().toString();
                       des = snapshot.child(name).child("des").getValue().toString();
                       uid = snapshot.child(name).child("uid").getValue().toString();
                       Picasso.get().load(url).into(simg);
                       sname.setText(n);
                       sdes.setText(des);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

           }
       });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cd:
                startActivity(new Intent(MainActivity6.this,  userpro.class).putExtra("name", name));
        }
    }
}