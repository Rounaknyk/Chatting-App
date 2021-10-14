package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity4 extends AppCompatActivity {
    FloatingActionButton save;
    FirebaseAuth auth  = FirebaseAuth.getInstance();
    EditText proname, prodes;
    TextView followers, followings;
    ImageView pic;
    active active;
    String n,d, fixname;
    String name, des, uid,url;
    DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference ref3, ref2 =FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getSupportActionBar().hide();
        pic = findViewById(R.id.uimg);
        proname = findViewById(R.id.proname);
        prodes = findViewById(R.id.prodes);
        followers = findViewById(R.id.followers);
        followings = findViewById(R.id.followings);

        fixname = proname.getText().toString();
        save = findViewById(R.id.save);
        ref2.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               url = snapshot.child("url").getValue().toString();
               name = snapshot.child("name").getValue().toString();
               des = snapshot.child("des").getValue().toString();
//               uid = snapshot.child(auth.getUid()).child("uid").getValue().toString();
                Picasso.get().load(url).into(pic);
                proname.setText(name);
                prodes.setText(des);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity4.this, "Saved Successfully !", Toast.LENGTH_SHORT).show();
                n = proname.getText().toString();
                d = prodes.getText().toString();
                DatabaseReference b = FirebaseDatabase.getInstance().getReference().child("Active");
                b.child(name).removeValue();
                active = new active(n, d, auth.getUid(), url);
                ref3 = FirebaseDatabase.getInstance().getReference().child("Users");
                ref3.child(auth.getUid()).child("des").setValue(d);
                ref2.child(auth.getUid()).child("name").setValue(n);
                DatabaseReference a = FirebaseDatabase.getInstance().getReference().child("Active");
                a.child(n).setValue(active);
            }
        });

    }
}