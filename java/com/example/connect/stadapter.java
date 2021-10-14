package com.example.connect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class stadapter extends RecyclerView.Adapter <stadapter.viewholder>{
    Context context;
    String name;
    List<stories> up;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public stadapter(Context context, List<stories> up){
        this.context = context;
        this.up = up;
    }
    public static class viewholder extends  RecyclerView.ViewHolder{
        ImageView img;
        CardView c;
        TextView stname;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.stimg2);
            c=  itemView.findViewById(R.id.cv);
            stname = itemView.findViewById(R.id.stname);
            
        }
    }
    @NonNull
    @Override
    public stadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stlayout, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull stadapter.viewholder holder, int position) {
        Picasso.get().load(up.get(position).getUrl()).resize(100, 100).into(holder.img);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(up.get(position).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("name").getValue().toString();
                if(auth.getUid().equals(up.get(position).getUid())){
                    holder.stname.setTextColor(Color.GREEN);
                    holder.stname.setText("You");
                }
                else {
                    holder.stname.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, stview.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("vimg", up.get(position).getUrl());
                i.putExtra("vtit", up.get(position).getTitle());
                i.putExtra("vdes", up.get(position).getDes());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return up.size();
    }
}
