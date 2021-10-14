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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class userpro2 extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText uname, udes;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Active");
    DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users");
    ImageView userimg;
    Post post ;
    friend f ;
    Button con;
    String name,n, uid, url,des;
    TextView ufollowers, ufollowings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpro2);
        getSupportActionBar().hide();
        uname = findViewById(R.id.username2);
        udes  = findViewById(R.id.userdes2);
        con = findViewById(R.id.button32);
        userimg = findViewById(R.id.userimg2);
        ufollowers = findViewById(R.id.ufollowers2);
        ufollowings = findViewById(R.id.ufollowings2);
        name = getIntent().getStringExtra("uname");
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
                con.setText("CONNECTED");
                DatabaseReference fdetails =FirebaseDatabase.getInstance().getReference().child("Users");
                DatabaseReference connecters =FirebaseDatabase.getInstance().getReference().child("Users");
                DatabaseReference u =FirebaseDatabase.getInstance().getReference().child("Users");
//                connecters.child(uid).child("connecters").child(auth.getUid()).child()
                u.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String d = snapshot.child("des").getValue().toString();
                        String n = snapshot.child("name").getValue().toString();
                        String url = snapshot.child("url").getValue().toString();
//                        String d = snapshot.child("des").getValue().toString();

                        connectors c = new connectors(n, d, auth.getUid(), url);
                        connecters.child(uid).child("connectors").child(auth.getUid()).setValue(c);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                DatabaseReference fposts =FirebaseDatabase.getInstance().getReference().child("Users");
//                DatabaseReference fstories =FirebaseDatabase.getInstance().getReference().child("Users");
                f = new friend(n , des, uid, url);
                fdetails.child(auth.getUid()).child("connected").child("details").child(uid).setValue(f);
                //fdetails.child(auth.getUid()).child("friends").child("posts").child(uid).setValue(f);
                //fdetails.child(auth.getUid()).child("friends").child("details").child(uid).setValue(f);
            }
        });

    }
}