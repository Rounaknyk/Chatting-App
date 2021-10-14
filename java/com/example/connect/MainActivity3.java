package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity3 extends AppCompatActivity {
    TextView regtxt;
    EditText lemail, lpass;
    Button login;
    String e, p;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();
        lemail = findViewById(R.id.lemail);
        lpass = findViewById(R.id.lpass);
        login = findViewById(R.id.login);
        regtxt = findViewById(R.id.lreg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = lpass.getText().toString();
                e = lemail.getText().toString();
                if(p.length() < 8 )
                {
                    lpass.setError("Password should be atleast of 8 Characters !");
                }
                if(e.isEmpty()){
                   lemail.setError("Email cannot be empty !");
                }
                auth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(MainActivity3.this, MainActivity4.class));

                        }
                    }
                });
            }
        });

    }
}