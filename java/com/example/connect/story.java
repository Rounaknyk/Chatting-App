package com.example.connect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class story extends AppCompatActivity {
    ImageView stimg;
    String url;
    EditText sttitle, stdes;
    int i =0;
    Uri imguri;
    FloatingActionButton add;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("story");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users");
    StorageReference sref = FirebaseStorage.getInstance().getReference().child("story");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        stimg = findViewById(R.id.stimg);
        stdes = findViewById(R.id.stdes);
        sttitle = findViewById(R.id.sttitle);
        add = findViewById(R.id.stadd);
        getSupportActionBar().hide();
//        while(i<2){
//            i++;
//            Intent i = new Intent();
//            i.setType("image/*");
//            i.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(i, 1);
//
//        }
         stimg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent();
                 i.setType("image/*");
                 i.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(i, 1);
             }
         });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(story.this, "Story Uploaded", Toast.LENGTH_SHORT).show();

         StorageReference sref2 = sref.child(UUID.randomUUID().toString() + "_IMG");
         sref2.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              sref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                  @Override
                  public void onSuccess(Uri uri) {
                      url = uri.toString();
                      stories s = new stories(sttitle.getText().toString(), stdes.getText().toString(), auth.getUid(), url, 0);
                      ref.child(sttitle.getText().toString()).setValue(s);
                      ref2.child(auth.getUid()).child("story").child(auth.getUid()).child(sttitle.getText().toString()).setValue(s);
                      startActivity(new Intent(story.this, frag1.class));
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
        if(data != null && data.getData() != null && requestCode == 1){
            imguri = data.getData();
            stimg.setImageURI(imguri);
        }
    }
}