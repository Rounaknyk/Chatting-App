package com.example.connect;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

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

public class padapter extends RecyclerView.Adapter <padapter.viewholder>{
    Context context;
    int like = 0;
    int liked = 0;
    int l2 =0 ;
    DatabaseReference likes = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference likes2 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference like1 = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference like2 = FirebaseDatabase.getInstance().getReference().child("Users");
    List<Post> up;

    public padapter(Context context, List<Post> up){
        this.context = context;
        this.up = up;
    }
    public static class viewholder extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView name, des, tit;
        String gc;
        TextView lik;
        CardView cardview;
        ImageButton liked, unliked;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tit = itemView.findViewById(R.id.vtiti);
            name = itemView.findViewById(R.id.vname);
            des = itemView.findViewById(R.id.vde);
            liked = itemView.findViewById(R.id.liked);
            unliked = itemView.findViewById(R.id.unliked);
            lik = itemView.findViewById(R.id.likes);

        }
    }


    @NonNull
    @Override
    public padapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.playout, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull padapter.viewholder holder, int position) {
        holder.liked.setVisibility(View.GONE);
        holder.name.setText(up.get(position).getName());
        holder.tit.setText(up.get(position).getTitle());
        holder.des.setText(up.get(position).getDes());
        Picasso.get().load(up.get(position).getUrl()).resize(374, 339).into(holder.img);


        holder.unliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.unliked.setVisibility(View.GONE);
                holder.liked.setVisibility(View.VISIBLE);
                likes.child(up.get(position).getUid()).child("posts").child(up.get(position).getTitle()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String slike = snapshot.child("like").getValue().toString();
                        like = Integer.parseInt(slike);
                        l2 = Integer.parseInt(slike);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                like1.child(up.get(position).getUid()).child("posts").child(up.get(position).getTitle()).child("like").setValue(++l2);


            }
        });
        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.unliked.setVisibility(View.VISIBLE);
                holder.liked.setVisibility(View.GONE);
                likes.child(up.get(position).getUid()).child("posts").child(up.get(position).getTitle()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String slike = snapshot.child("like").getValue().toString();
                        like = Integer.parseInt(slike);
                        l2 = Integer.parseInt(slike);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                like2.child(up.get(position).getUid()).child("posts").child(up.get(position).getTitle()).child("like").setValue(--like);

            }
        });

        likes2.child(up.get(position).getUid()).child("posts").child(up.get(position).getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String slike = snapshot.child("like").getValue().toString();
                int l ;
                l = Integer.parseInt(slike);
                holder.lik.setText(l +" Likes ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

                   }


    @Override
    public int getItemCount() {
        return up.size();
    }
}
