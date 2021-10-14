package com.example.connect;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ca extends RecyclerView.Adapter<ca.sent>{
    Context context;
    List<chats> chat;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public   ca(Context context, List<chats> chat ){
        this.context = context;
        this.chat = chat;
    }

    @NonNull
    @Override
    public sent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chatlayout, parent, false);
        ca.sent viewholder = new ca.sent(view);

        return viewholder;
    }

    public class sent extends RecyclerView.ViewHolder {
        TextView name, time, txt;
        CardView cd;
        ConstraintLayout cl;
        public sent(@NonNull View itemView) {
            super(itemView);
            name  = itemView.findViewById(R.id.lname);
            time = itemView.findViewById(R.id.ltime);
            txt = itemView.findViewById(R.id.ltxt);
            cd = itemView.findViewById(R.id.cardView6);
            cl = itemView.findViewById(R.id.cl);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull sent holder, int position) {

        if(FirebaseAuth.getInstance().getUid().equals(chat.get(position).getUid())){
            holder.name.setText("You ");
            holder.time.setText(chat.get(position).getTime());
//            if(chat.get(position).getText().length() == 35) {
//                holder.cd.setMinimumHeight(50);
//                holder.txt.setHeight(50);
//                holder.cl.setMaxHeight(95);
//                holder.cl.setMinHeight(95);
                holder.txt.setText(chat.get(position).getText());
          //  }
//            else{
//                holder.txt.setText(chat.get(position).getText());
//            }
        }
        else{
//            MediaPlayer mc = MediaPlayer.create(context, R.raw.beep);
//            mc.start();
            holder.cd.setCardBackgroundColor(Color.WHITE);
            holder.txt.setTextColor(Color.BLACK);
            holder.name.setText(chat.get(position).getName());
            holder.time.setText(chat.get(position).getTime());
            holder.txt.setText(chat.get(position).getText());
        }
    }
    @Override
    public int getItemCount() {
        return chat.size();
    }


}
