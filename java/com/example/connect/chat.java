package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.solver.ArrayLinkedVariables;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class chat extends AppCompatActivity implements  View.OnClickListener{
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ImageView cimg;
    ImageButton cs;
    EditText input;
    CardView cd;
    TextView cname, cdes;
    RecyclerView rec;
    cladapter ca;
    List<chatlist> cl;
    String n, des, url, uid,name;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Active");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        cd = findViewById(R.id.c2);
        cname = findViewById(R.id.cname);
        cdes = findViewById(R.id.cdes);
        rec = findViewById(R.id.crec);
        cimg = findViewById(R.id.cimg);
        cs = findViewById(R.id.s2);
        input = findViewById(R.id.input2);
//        cd.setVisibility(View.GONE);
        cd.setOnClickListener(this);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        cl= new ArrayList<>();
        ref3.child(auth.getUid()).child("chatlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    chatlist list = s.getValue(chatlist.class);
                    cl.add(list);
                }
                ca = new cladapter(chat.this, cl);
                rec.setAdapter(ca);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = input.getText().toString();
                cd.setVisibility(View.VISIBLE);
                ref.child(n).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         name = snapshot.child("name").getValue().toString();
                         des = snapshot.child("des").getValue().toString();
                         url  = snapshot.child("url").getValue().toString();
                         uid = snapshot.child("uid").getValue().toString();
                         //setting values
                         cname.setText(name);
                         cdes.setText(des);
                        Picasso.get().load(url).into(cimg);

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
        switch(v.getId()){
            case R.id.c2:
                new AlertDialog.Builder(this)
                        .setTitle("Add this user to the Chat List ?")
                        .setMessage("You will be able to chat with the user only if he is connected to you !")
                        .setIcon(android.R.drawable.ic_input_add)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(chat.this, n +" Added to Chat List !", Toast.LENGTH_SHORT).show();
                                chatlist cl= new chatlist(name, des, uid, url);
                                ref2.child(auth.getUid()).child("chatlist").child(name).setValue(cl);
                            }
                        }).setNegativeButton("No", null).show();

        }
    }
}