package com.project.daffaalmerf.uaspm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.daffaalmerf.uaspm.LoadingDialog;
import com.project.daffaalmerf.uaspm.R;
import com.project.daffaalmerf.uaspm.activity.EditSpacePostActivity;
import com.project.daffaalmerf.uaspm.activity.ViewSpacePostActivity;
import com.project.daffaalmerf.uaspm.model.ReplyModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReplyAdapter extends FirestoreRecyclerAdapter<ReplyModel, ReplyAdapter.ReplyHolder>{

    private Context context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private String current_uid = mAuth.getCurrentUser().getUid();
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private LoadingDialog loadingDialog;

    public ReplyAdapter(@NonNull FirestoreRecyclerOptions<ReplyModel> option, Context context){

        super(option);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ReplyAdapter.ReplyHolder holder, int position, @NonNull ReplyModel model) {

//        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
//        String replyCreationDate = dateFormat.format(model.getTimestamp());
        String replyCreationDate = String.valueOf(model.getTimestamp());
        String uid = model.getBy();
        String reply = model.getReply();
        String post_id = model.getPost_id();
        String reply_id = getSnapshots().getSnapshot(position).getId();

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
        holder.replyDate.setText(replyCreationDate);

        holder.replyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (current_uid.equals(uid)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Delete Reply");
                    builder.setMessage("Do you want to delete this reply?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    loadingDialog = new LoadingDialog((Activity) context);

                                    loadingDialog.startDialog();

                                    mFirestore.collection("Space").document(post_id).collection("Replies").document(reply_id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                loadingDialog.dismissDialog();

                                                Toast.makeText(context, "Reply Successfully Deleted", Toast.LENGTH_SHORT).show();

                                            } else {

                                                loadingDialog.dismissDialog();

                                                Toast.makeText(context, "Reply Failed to Delete", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });

                                }
                            });

                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

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