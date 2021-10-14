package com.example.connect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
    EditText email,pass,username;
    active active;
    Button reg;
    String n,e,p,uid,url;
    TextView txt,regtxt;
    ImageView profile;
    ProgressBar pb;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference change = FirebaseDatabase.getInstance().getReference().child("Users");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Uri imguri;
    User user;
    StorageReference sref  = FirebaseStorage.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        pb = findViewById(R.id.progressBar2);
        reg = findViewById(R.id.reg);
        txt = findViewById(R.id.textView8);
        profile = findViewById(R.id.profile);
        uid = auth.getUid();
        regtxt = findViewById(R.id.logintext);
        pb.setVisibility(View.GONE);
        DatabaseReference s = FirebaseDatabase.getInstance().getReference().child("pksd");
        s.child("asd").setValue(12);
        regtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, MainActivity5.class));
            }
        });
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity2.this, MainActivity5.class));
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,1);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = username.getText().toString();
                p = pass.getText().toString();
                e = email.getText().toString();
                if (p.length() < 8) {
                    pass.setError("Password should be atleast of 8 Characters !");
                }
                if (e.isEmpty()) {
                    email.setError("Email cannot be empty !");
                }
                if (n.isEmpty()) {
                    username.setError("Username cannot be empty !");
                }

                auth.createUserWithEmailAndPassword(e, p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        StorageReference sref2 = sref.child(UUID.randomUUID().toString() + "_IMG");
                        sref2.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                sref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        pb.setVisibility(View.GONE);
                                        url = uri.toString();
                                        user = new User(n, e,  FirebaseAuth.getInstance().getUid(), url);
                                        ref.child( FirebaseAuth.getInstance().getUid()).setValue(user);
                                        active = new active(n, "Available", FirebaseAuth.getInstance().getUid(), url);
                                        // change.child(auth.getUid()).child("change").setValue("0");
                                        DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Active");
                                        r.child(n).setValue(active);
                                        ref2.child( FirebaseAuth.getInstance().getUid()).child("des").setValue("Available");
                                        startActivity(new Intent(MainActivity2.this, MainActivity5.class).putExtra("n", n));
                                    }
                                });

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                pb.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(MainActivity2.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                imguri = data.getData();
                profile.setImageURI(imguri);
                txt.setVisibility(View.GONE);


    }
}