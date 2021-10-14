package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.ArrayList;
import java.util.List;

public class mainchat extends AppCompatActivity {
    String name, des, uid, url;
    TextView chatn, chatd;
    chats c ;
    EditText input;
    ImageView chaturl, info;
    RecyclerView rec;
    String rname;
    ca ca;
    List<chats> cl;
    String suid, ruid, sroom, rroom, txt;
    FloatingActionButton send;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chats");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("chats");
    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("chats");
    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchat);
        getSupportActionBar().hide();
        name = getIntent().getStringExtra("chatn");
        des = getIntent().getStringExtra("chatd");
        url = getIntent().getStringExtra("chaturl");
        uid = getIntent().getStringExtra("chatuid");
        chatn = findViewById(R.id.chatn);
        send = findViewById(R.id.send);
        info = findViewById(R.id.info);
        chaturl = findViewById(R.id.chaturl);
        chatd = findViewById(R.id.textView16);
        input = findViewById(R.id.chatinput);
        rec = findViewById(R.id.rec);
        chatd.setText(des);
        suid = auth.getUid();
        ruid = uid;
        sroom = suid + ruid;
        rroom = ruid + suid ;
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        cl = new ArrayList<>();
        ca = new ca(mainchat.this, cl);
        rec.setAdapter(ca);
        Picasso.get().load(url).into(chaturl);
        chatn.setText(name);
        ref4.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             rname = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        input.drawable
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = input.getText().toString();
                if(txt.isEmpty()) {
                    Toast.makeText(mainchat.this, "Text cannot be Empty !", Toast.LENGTH_SHORT).show();
                }
                else {
                   MediaPlayer mp = MediaPlayer.create(mainchat.this, R.raw.beep);
                   mp.start();
                    c = new chats(txt, rname, auth.getUid(), "10:00 AM");
                    input.setText("");
                    ref.child(sroom).child("sent").push().setValue(c);
                    ref2.child(rroom).child("sent").push().setValue(c);
                }
            }
        });
        ref3.child(sroom).child("sent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    chats cs = s.getValue(chats.class);
                    cl.add(cs);
                }
                ca.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainchat.this, userpro2.class);
                i.putExtra("uname", name);
                i.putExtra("udes", des);
                i.putExtra("uuid", uid);
                i.putExtra("uurl", url);
                startActivity(i);

            }
        });


    }
}