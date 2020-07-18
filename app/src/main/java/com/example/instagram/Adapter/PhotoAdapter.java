package com.example.instagram.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.Fragments.PostDetailFragment;
import com.example.instagram.Model.Post;
import com.example.instagram.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    public PhotoAdapter(Context mCOntext, List<Post> mPosts) {
        this.mCOntext = mCOntext;
        this.mPosts = mPosts;
    }

    private Context mCOntext;
    private List<Post> mPosts;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCOntext).inflate(R.layout.photo_item, parent, false);


        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Post post = mPosts.get(position);
        Picasso.get().load(post.getImageurl()).placeholder(R.mipmap.ic_launcher).into(holder.postImage);

        //Placeholder = will load a default image until the main image is fetching from internet

        holder.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCOntext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid", post.getPostId()).apply();

                ((FragmentActivity)mCOntext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostDetailFragment()).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView postImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_image);
        }
    }
}
