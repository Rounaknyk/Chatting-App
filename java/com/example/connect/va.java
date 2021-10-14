package com.example.connect;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class va extends RecyclerView.Adapter <va.viewholder>{
    Context context;
    MediaController m ;
    List<vidlist> up;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    friend f;
    public va(Context context, List<vidlist> up){
        this.context = context;
        this.up = up;
    }
    public static class viewholder extends  RecyclerView.ViewHolder{
        VideoView v;
        TextView name, tit,des;
        ImageView img;
        ImageButton liked, unliked;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.vname);
            v = itemView.findViewById(R.id.vid2);
            des = itemView.findViewById(R.id.vde);
            tit = itemView.findViewById(R.id.vtiti);
             img = itemView.findViewById(R.id.imageView4);
             liked = v.findViewById(R.id.liked2);
             unliked = v.findViewById(R.id.unliked2);
        }
    }

    @NonNull
    @Override
    public va.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vlayout, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull va.viewholder holder, int position) {
        vidlist vd = up.get(position);
        holder.des.setText(up.get(position).getDes());
        holder.tit.setText(up.get(position).getTit());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(vd.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name  = snapshot.child("name").getValue().toString();
                holder.name.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try {

            m = new MediaController(context);
            Uri uri = Uri.parse(vd.getUrl());
            m.setAnchorView(holder.v);
            holder.v.setMediaController(m);
            holder.v.setVideoURI(uri);


        }catch (Exception e){
            Toast.makeText(context, "Error !", Toast.LENGTH_SHORT).show();
        }
//        holder.v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.v.start();
//            }
//        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.img.setVisibility(View.GONE);
                holder.v.start();
                holder.v.setMinimumHeight(302);
                holder.v.setMinimumWidth(356);
            }
        });
        holder.v.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                holder.img.setVisibility(View.VISIBLE);
            }
        });
//        holder.liked.setVisibility(View.GONE);
//        holder.unliked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.unliked.setVisibility(View.GONE);
//                holder.liked.setVisibility(View.VISIBLE);
//            }
//        });
//        holder.liked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.unliked.setVisibility(View.VISIBLE);
//                holder.liked.setVisibility(View.GONE);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return up.size();
    }
}
