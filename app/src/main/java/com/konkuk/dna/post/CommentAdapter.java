package com.konkuk.dna.post;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konkuk.dna.R;
import com.konkuk.dna.post.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {
    Context context;
    ArrayList<Comment> comments;

    private static Typeface boldTypeface;

    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);

        this.context = context;
        this.comments = objects;

        init();
    }

    public void init() {
        if(boldTypeface == null) {
            boldTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareEB.ttf");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        Comment comment = comments.get(position);

        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.post_comment_item, null);
        }

        ImageView avatar = v.findViewById(R.id.commentAvatar);
        TextView nickname = v.findViewById(R.id.commentNickname);
        TextView content = v.findViewById(R.id.commentContent);
        TextView date = v.findViewById(R.id.commentDate);

        if (comment.getAvatar() != null) {
            Picasso.get().load(comment.getAvatar()).into(avatar);
        }

        nickname.setTypeface(boldTypeface);
        nickname.setText(comment.getNickname());
        content.setText(comment.getContent());
        date.setText(comment.getDate());

        return v;
    }
}

