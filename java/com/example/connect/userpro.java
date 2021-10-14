package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class userpro extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference req = FirebaseDatabase.getInstance().getReference().child("Users");
    EditText uname, udes;
    DatabaseReference user2 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Active");
    DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users");
    ImageView userimg;
    Post post ;
    String rname, rurl;
    friend f ;
    Button con;
    String name,n, uid, url,des, rdes;
    pending pen;
    TextView ufollowers, ufollowings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpro);
        getSupportActionBar().hide();
        uname = findViewById(R.id.username);
        udes  = findViewById(R.id.userdes);
        con = findViewById(R.id.button3);
        userimg = findViewById(R.id.userimg);
        ufollowers = findViewById(R.id.ufollowers);
        ufollowings = findViewById(R.id.ufollowings);
        name = getIntent().getStringExtra("name");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n = snapshot.child(name).child("name").getValue().toString();
                url = snapshot.child(name).child("url").getValue().toString();
                des = snapshot.child(name).child("des").getValue().toString();
                uid = snapshot.child(name).child("uid").getValue().toString();
                uname.setText(n);
                udes.setText(des);
                Picasso.get().load(url).into(userimg);
                Toast.makeText(userpro.this, ""+ uid, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        user.child(uid).child("posts").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        con.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                con.setText("REQUESTED");
                user2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        rname = snapshot.child(auth.getUid()).child("name").getValue().toString();
                        rurl = snapshot.child(auth.getUid()).child("url").getValue().toString();
                        rdes = snapshot.child(auth.getUid()).child("des").getValue().toString();
                        pen = new pending(rname, rdes, auth.getUid(), rurl);
                        req.child(uid).child("pending").child(rname).setValue(pen);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                
//                DatabaseReference fdetails =FirebaseDatabase.getInstance().getReference().child("Users");
//                DatabaseReference connecters =FirebaseDatabase.getInstance().getReference().child("Users");
//                DatabaseReference u =FirebaseDatabase.getInstance().getReference().child("Users");
//                u.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String d = snapshot.child("des").getValue().toString();
//                        String n = snapshot.child("name").getValue().toString();
//                        String url = snapshot.child("url").getValue().toString();
//
//                        connectors c = new connectors(n, d, auth.getUid(), url);
//                        connecters.child(uid).child("connectors").child(auth.getUid()).setValue(c);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//                f = new friend(n , des, uid, url);
//                fdetails.child(auth.getUid()).child("connected").child("details").child(uid).setValue(f);
            }
        });

    }
}