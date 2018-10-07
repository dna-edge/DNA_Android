package com.konkuk.dna.user;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konkuk.dna.R;
import com.konkuk.dna.post.Post;

import java.util.ArrayList;

public class UserPostListAdapter extends ArrayAdapter<Post> {
    Context context;
    ArrayList<Post> posts;

    private static Typeface fontAwesomeR;
    private static Typeface fontAwesomeS;

    public UserPostListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);

        this.context = context;
        this.posts = objects;

        init();
    }

    public void init() {
        if(fontAwesomeR == null) {
            fontAwesomeR = Typeface.createFromAsset(context.getAssets(), "fonts/fa-regular-400.ttf");
        }
        if(fontAwesomeS == null) {
            fontAwesomeS = Typeface.createFromAsset(context.getAssets(), "fonts/fa-solid-900.ttf");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        Post post = posts.get(position);

        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.post_list_item, null);
        }

        TextView postTitle = v.findViewById(R.id.postTitle);
        TextView postDate = v.findViewById(R.id.postDate);
        TextView postLikeCntIcon = v.findViewById(R.id.postLikeCntIcon);
        TextView postLikeCntText = v.findViewById(R.id.postLikeCntText);
        TextView postCommentCntText = v.findViewById(R.id.postCommentCntText);
        TextView postCommentCntIcon = v.findViewById(R.id.postCommentCntIcon);
        TextView postScrapCntText = v.findViewById(R.id.postScrapCntText);
        TextView postScrapCntIcon = v.findViewById(R.id.postScrapCntIcon);

        postTitle.setText(post.getTitle());
        postDate.setText(post.getDate());
        postLikeCntIcon.setTypeface(fontAwesomeS);
        postLikeCntText.setText(post.getLikeCount()+" ");
        postCommentCntIcon.setTypeface(fontAwesomeS);
        postCommentCntText.setText(post.getCommentCount()+" ");
        postScrapCntIcon.setTypeface(fontAwesomeS);
        postScrapCntText.setText(post.getScrapCount()+" ");

        return v;
    }
}

