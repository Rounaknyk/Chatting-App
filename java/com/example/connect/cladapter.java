package com.example.connect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class cladapter extends RecyclerView.Adapter <cladapter.viewholder>{
    Context context;
    List<chatlist> up;

    public cladapter(Context context, List<chatlist> up){
        this.context = context;
        this.up = up;
    }
    public static class viewholder extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        CardView clcd;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.climg);
            name = itemView.findViewById(R.id.clname);
            clcd = itemView.findViewById(R.id.clcd);
        }
    }


    @NonNull
    @Override
    public cladapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.clayout, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull cladapter.viewholder holder, int position) {
        holder.name.setText(up.get(position).getName());
        Picasso.get().load(up.get(position).getUrl()).into(holder.img);
        holder.clcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, mainchat.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("chatn", up.get(position).getName());
                i.putExtra("chatd", up.get(position).getDes());
                i.putExtra("chatuid", up.get(position).getUid());
                i.putExtra("chaturl", up.get(position).getUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return up.size();
    }
}
