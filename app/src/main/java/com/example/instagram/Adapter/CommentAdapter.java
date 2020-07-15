package com.example.instagram.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.Model.Comment;
import com.example.instagram.Model.User;
import com.example.instagram.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> mComments;

    private FirebaseUser fUser;

    public CommentAdapter(Context mContext, List<Comment> mComments) {
        this.mContext = mContext;
        this.mComments = mComments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        return  new CommentAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        Comment comment = mComments.get(position);

        holder.comment.setText(comment.getComment());

        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(comment.getPublisher())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        holder.userName.setText(user.getUsername());

                        if(user.getImageurl().equals("default")) {
                            holder.imageProfile.setImageResource(R.mipmap.ic_launcher);
                        } else {
                            Picasso.get().load(user.getImageurl()).into(holder.imageProfile);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imageProfile;
        public TextView userName;
        public TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.image_profile);
            userName = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);

        }
    }
}
