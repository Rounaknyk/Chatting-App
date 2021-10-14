package com.example.connect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag4 extends Fragment {
    EditText ptitle, pdes;
    ImageView pimg;
    FloatingActionButton post;
    TextView ptxt;
    Uri imguri;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Post");
    StorageReference sref = FirebaseStorage.getInstance().getReference().child("Post");
    String url, t, name,d, rname;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag4.
     */
    // TODO: Rename and change types and number of parameters
    public static frag4 newInstance(String param1, String param2) {
        frag4 fragment = new frag4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_frag4, container, false);
        ptitle = v.findViewById(R.id.ptitle);
        pdes = v.findViewById(R.id.pdes);
        pimg = v.findViewById(R.id.pimg);
        ptxt = v.findViewById(R.id.ptxt);
        post = v.findViewById(R.id.post);
        ref3.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rname = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = ptitle.getText().toString();
                d = pdes.getText().toString();
                Toast.makeText(getContext(), "Post Uploaded !", Toast.LENGTH_SHORT).show();
                ptitle.setText("");
                pdes.setText("");
                pimg.setImageResource(R.drawable.ic_baseline_person_24);
                StorageReference sref2 = sref.child(UUID.randomUUID().toString() +"_IMG");
                sref2.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        sref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                                ref2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        name = snapshot.child(auth.getUid()).child("name").getValue().toString();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                Post p = new Post(rname, t, d, auth.getUid(), url, 0 );

                                ref.child(UUID.randomUUID().toString()).setValue(p);
                                DatabaseReference change = FirebaseDatabase.getInstance().getReference().child("Post2");
                                change.child(t).setValue(0);
//                                DatabaseReference s =FirebaseDatabase.getInstance().getReference().child("Users");
//                                s.child(auth.getUid()).child("connectors")
                                DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Users");
                                r.child(auth.getUid()).child("posts").child(t).setValue(p);

                            }
                        });

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getData() != null){
            imguri = data.getData();
            pimg.setImageURI(imguri);
            ptxt.setVisibility(View.GONE);

        }

    }
}