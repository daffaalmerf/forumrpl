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
import com.project.daffaalmerf.uaspm.activity.ViewSpacePostActivity;
import com.project.daffaalmerf.uaspm.model.SpacePostModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SpacePostAdapter extends FirestoreRecyclerAdapter<SpacePostModel, SpacePostAdapter.spacePostHolder>{

    private Context context;

    public SpacePostAdapter(@NonNull FirestoreRecyclerOptions<SpacePostModel> option, Context context){

        super(option);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull SpacePostAdapter.spacePostHolder holder, int position, @NonNull SpacePostModel model) {

        String postId = getSnapshots().getSnapshot(position).getId();

        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        String creationDate = dateFormat.format(model.getTimestamp());
        String uid = model.getBy();
        String category = model.getCategory();
        String content = model.getContent();

        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue().toString();

                holder.postUsername.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.postCategory.setText(category);
        holder.postContent.setText(content);
        holder.postDate.setText(creationDate);

        holder.postUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent viewPostIntent = new Intent(context, ViewSpacePostActivity.class);
                viewPostIntent.putExtra("uid", uid);
                viewPostIntent.putExtra("post_id", postId);
                viewPostIntent.putExtra("category", category);
                viewPostIntent.putExtra("content", content);
                viewPostIntent.putExtra("date", creationDate);
                context.startActivity(viewPostIntent);

            }
        });

        holder.postCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent viewPostIntent = new Intent(context, ViewSpacePostActivity.class);
                viewPostIntent.putExtra("uid", uid);
                viewPostIntent.putExtra("post_id", postId);
                viewPostIntent.putExtra("category", category);
                viewPostIntent.putExtra("content", content);
                viewPostIntent.putExtra("date", creationDate);
                context.startActivity(viewPostIntent);

            }
        });

        holder.postContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent viewPostIntent = new Intent(context, ViewSpacePostActivity.class);
                viewPostIntent.putExtra("uid", uid);
                viewPostIntent.putExtra("post_id", postId);
                viewPostIntent.putExtra("category", category);
                viewPostIntent.putExtra("content", content);
                viewPostIntent.putExtra("date", creationDate);
                context.startActivity(viewPostIntent);

            }
        });

        holder.postDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent viewPostIntent = new Intent(context, ViewSpacePostActivity.class);
                viewPostIntent.putExtra("uid", uid);
                viewPostIntent.putExtra("post_id", postId);
                viewPostIntent.putExtra("category", category);
                viewPostIntent.putExtra("content", content);
                viewPostIntent.putExtra("date", creationDate);
                context.startActivity(viewPostIntent);

            }
        });

    }

    @NonNull
    @Override
    public SpacePostAdapter.spacePostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_post, parent, false);
        return new SpacePostAdapter.spacePostHolder(view);

    }

    class spacePostHolder extends RecyclerView.ViewHolder {

        TextView postUsername, postCategory, postContent, postDate;

        public spacePostHolder(@NonNull View itemView) {

            super(itemView);

            postUsername = itemView.findViewById(R.id.space_item_profile_post_name);
            postCategory = itemView.findViewById(R.id.space_item_category);
            postContent = itemView.findViewById(R.id.space_item_content);
            postDate = itemView.findViewById(R.id.space_item_date);

        }

    }


}
