package com.example.connect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag1 extends Fragment {
     RecyclerView rec;
     List<Post> pl ;
    String url;
    ImageView pro, search, chat;
    FloatingActionButton logout;
    ImageView pic;
    Uri imguri;
    StorageReference sref = FirebaseStorage.getInstance().getReference().child("Story");
     padapter pa;
     DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Post");
     DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users");
     FirebaseAuth auth = FirebaseAuth.getInstance();
     RecyclerView strec;
     List<stories> stlist;
     stadapter sta;
    DatabaseReference stref = FirebaseDatabase.getInstance().getReference().child("story");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag1.
     */
    // TODO: Rename and change types and number of parameters
    public static frag1 newInstance(String param1, String param2) {
        frag1 fragment = new frag1();
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
        View v =  inflater.inflate(R.layout.fragment_frag1, container, false);
        rec= v.findViewById(R.id.prec);
        pro = v.findViewById(R.id.pro);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity4.class));
            }
        });

//        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        rec.setLayoutManager(layout);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        pl = new ArrayList<>();
        logout = v.findViewById(R.id.logout2);
        pic = v.findViewById(R.id.pic);
        chat = v.findViewById(R.id.chat);
        search = v.findViewById(R.id.search);
        strec = v.findViewById(R.id.strec);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), com.example.connect.chat.class);
                startActivity(i);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity6.class));
            }
        });

        ref3.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                url= snapshot.child("url").getValue().toString();
                if(url.equals("No image")) {

                }
                else{
                    Picasso.get().load(url).into(pic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), story.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getContext(), MainActivity2.class));
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pl.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    Post p  = s.getValue(Post.class);
                    pl.add(p);
                }
                pa = new padapter(getContext(), pl);
                rec.setAdapter(pa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        strec.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
          strec.setLayoutManager(layout);
        stlist = new ArrayList<>();
        sta  = new  stadapter(getContext(), stlist);
        strec.setAdapter(sta);
        stref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stlist.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    stories st = s.getValue(stories.class);
                    stlist.add(st);
                }
              sta.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data!=null && data.getData() != null){
            imguri = data.getData();

        }

    }
}