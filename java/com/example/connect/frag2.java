package com.example.connect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag2 extends Fragment {
    ImageButton btn;
    Uri viduri;
    ProgressBar pg;
    String url , name, des, tit;
    EditText vdt,vdd;
    View view;
    FloatingActionButton upload;
    int like;
    RecyclerView rec;
    List<vidlist> vl;
    va va;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    MediaController media;
    VideoView vv;
    StorageReference storage = FirebaseStorage.getInstance().getReference().child("video");
    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("video");
    DatabaseReference data2 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference main = FirebaseDatabase.getInstance().getReference().child("video");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag2.
     */
    // TODO: Rename and change types and number of parameters
    public static frag2 newInstance(String param1, String param2) {
        frag2 fragment = new frag2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_frag2, container, false);
        btn = v.findViewById(R.id.vidadd);
        vv = v.findViewById(R.id.videoView2);
        vdt = v.findViewById(R.id.vdt);
        vdd = v.findViewById(R.id.vdd);
        view = v.findViewById(R.id.view5);
        upload = v.findViewById(R.id.upload);
        vdt.setVisibility(View.GONE);
        vdd.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        upload.setVisibility(View.GONE);
        vv.setVisibility(View.GONE);
        ProgressBar pg  = v.findViewById(R.id.progressBar);
        media = new MediaController(getContext());
        vv.setMediaController(media);
        pg.setVisibility(View.GONE);
        rec = v.findViewById(R.id.vrec);
        rec.setHasFixedSize(true);
//        rec.setVisibility(View.GONE);
        vl = new ArrayList<>();
        rec.setLayoutManager(new LinearLayoutManager(getContext()));

//        vv.setVisibility(View.GONE);
        main.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vl.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    vidlist vdlist = s.getValue(vidlist.class);
                    vl.add(vdlist);
                }
                va = new va(getContext(), vl);
                rec.setAdapter(va);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent  i = new Intent();
                        i.setType("video/*");
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(i, 1);
                        vdt.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                        vv.setVisibility(View.VISIBLE);
                        vdd.setVisibility(View.VISIBLE);
                        upload.setVisibility(View.VISIBLE);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vdt.setVisibility(View.GONE);
                vdd.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                upload.setVisibility(View.GONE);
                vv.setVisibility(View.GONE);
                des = vdd.getText().toString();
                tit = vdt.getText().toString();
                vdd.setText("");
                vdt.setText("");
                StorageReference sref = storage.child(UUID.randomUUID().toString() + "_VID");
                 sref.putFile(viduri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                         Uri uri = Uri.parse(url);
//                         vv.setVideoURI(Uri.parse(url));
                        sref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                                pg.setVisibility(View.GONE);
                                vidlist list = new vidlist(tit,  des, auth.getUid(), url, 18);
                                data.child(UUID.randomUUID().toString()).setValue(list);
                            }
                        });
                  //       Toast.makeText(getContext(), "Uploaded !", Toast.LENGTH_SHORT).show();

//                         vv.setVideoURI(viduri);
//                         vv.start();
                     }
                 }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                         pg.setVisibility(View.VISIBLE);
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         pg.setVisibility(View.GONE);
                         Toast.makeText(getContext(), "Error! ", Toast.LENGTH_SHORT).show();
                     }
                 });

            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            viduri = data.getData();
            vv.setVisibility(View.VISIBLE);
            vv.setVideoURI(viduri);
            vv.start();

        }

    }
}