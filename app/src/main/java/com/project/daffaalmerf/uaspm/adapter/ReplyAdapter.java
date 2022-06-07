package com.project.daffaalmerf.uaspm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.daffaalmerf.uaspm.R;
import com.project.daffaalmerf.uaspm.model.ReplyModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReplyAdapter extends FirestoreRecyclerAdapter<ReplyModel, ReplyAdapter.ReplyHolder>{

    private Context context;

    public ReplyAdapter(@NonNull FirestoreRecyclerOptions<ReplyModel> option, Context context){

        super(option);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ReplyAdapter.ReplyHolder holder, int position, @NonNull ReplyModel model) {

        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        String creationDate = dateFormat.format(model.getTimestamp());
        String uid = model.getBy();
        String reply = model.getReply();

        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue().toString();

                holder.replyUsername.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.replyContent.setText(reply);
        holder.replyDate.setText(creationDate);

    }

    @NonNull
    @Override
    public ReplyAdapter.ReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_reply, parent, false);
        return new ReplyAdapter.ReplyHolder(view);

    }

    class ReplyHolder extends RecyclerView.ViewHolder {

        TextView replyUsername, replyContent, replyDate;

        public ReplyHolder(@NonNull View itemView) {

            super(itemView);

            replyUsername = itemView.findViewById(R.id.reply_item_profile_post_name);
            replyContent = itemView.findViewById(R.id.reply_item_content);
            replyDate = itemView.findViewById(R.id.reply_item_date);

        }

    }


}