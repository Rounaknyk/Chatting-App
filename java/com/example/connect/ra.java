package com.example.connect;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class ra extends RecyclerView.Adapter <ra.viewholder>{
    Context context;
    int i=0;
    List<pending> up;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    friend f;
    public ra(Context context, List<pending> up){
        this.context = context;
        this.up = up;
    }
    public static class viewholder extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView  txt;
        CardView c;
        Button a, r, con;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.rimg);
            txt = itemView.findViewById(R.id.rtxt);
            a = itemView.findViewById(R.id.accept);
            r = itemView.findViewById(R.id.reject);

//            con = itemView.findViewById(R.id.button3);
        }
    }


    @NonNull
    @Override
    public ra.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.request, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ra.viewholder holder, int position) {
        holder.txt.setText(up.get(position).getName() + " wants to Connect with you !");
        Picasso.get().load(up.get(position).getUrl()).into(holder.img);

      holder.r.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
//              holder.txt.setTextColor(Color.RED);
//              Toast.makeText(context,               "Your have rejected "+up.get(position).getName()+"'s connection request !"
//                      , Toast.LENGTH_SHORT).show();
              DatabaseReference reference2 =  FirebaseDatabase.getInstance().getReference().child("Users");
              reference2.child(up.get(position).getUid()).child("pending").child(up.get(position).getName()).removeValue();

          }
      });

      holder.a.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

//              holder.con.setText("CONNECTED");
                DatabaseReference fdetails =FirebaseDatabase.getInstance().getReference().child("Users");
                DatabaseReference connecters =FirebaseDatabase.getInstance().getReference().child("Users");
                DatabaseReference ac =FirebaseDatabase.getInstance().getReference().child("Users");
                DatabaseReference u =FirebaseDatabase.getInstance().getReference().child("Users");
                u.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String d = snapshot.child(auth.getUid()).child("des").getValue().toString();
                        String n = snapshot.child(auth.getUid()).child("name").getValue().toString();
                        String url = snapshot.child(auth.getUid()).child("url").getValue().toString();

                        connectors c = new connectors(n, d, auth.getUid(), url);
                        connecters.child(up.get(position).getUid()).child("connectors").child(auth.getUid()).setValue(c);
                        f = new friend(up.get(position).getName() ,up.get(position).getDes(), up.get(position).getUid(), up.get(position).getUrl());
                        fdetails.child(auth.getUid()).child("connected").child("details").child(up.get(position).getUid()).setValue(f);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
              DatabaseReference reference =  FirebaseDatabase.getInstance().getReference().child("Users");
              reference.child(up.get(position).getUid()).child("pending").child(up.get(position).getName()).removeValue();
//
//              holder.c.setVisibility(View.GONE);
//              holder.img.setVisibility(View.GONE);
//              holder.a.setVisibility(View.GONE);
//              holder.r.setVisibility(View.GONE);
//              holder.txt.setTextColor(Color.GREEN);
//              holder.txt.setText("You are connected to "+ up.get(position).getName() + " !");
//            //  ac.child(auth.getUid()).child("pending").child(up.get(position).getName()).removeValue();


          }
      });


    }

    @Override
    public int getItemCount() {
        return up.size();
    }
}
