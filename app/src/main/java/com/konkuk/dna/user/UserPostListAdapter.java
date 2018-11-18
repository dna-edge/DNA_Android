package com.konkuk.dna.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konkuk.dna.R;
import com.konkuk.dna.post.Post;
import com.konkuk.dna.utils.HttpReqRes;
import com.konkuk.dna.utils.dbmanage.Dbhelper;

import java.util.ArrayList;

public class UserPostListAdapter extends ArrayAdapter<Post> {
    Context context;
    ArrayList<Post> posts;
    Boolean isBookMark;
    int idx;

    private static Typeface fontAwesomeR;
    private static Typeface fontAwesomeS;

    public UserPostListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects, boolean isBookMark) {
        super(context, resource, objects);

        this.context = context;
        this.posts = objects;
        this.isBookMark = isBookMark;

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
        idx = post.getPostingIdx();

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
        LinearLayout bookmarkDeleteBtn = v.findViewById(R.id.bookmarkDeleteBtn);

        if (isBookMark) {
            TextView bookmarkDeleteBtnText = v.findViewById(R.id.bookmarkDeleteBtnText);
            bookmarkDeleteBtnText.setTypeface(fontAwesomeS);
            bookmarkDeleteBtn.setVisibility(View.VISIBLE);
            bookmarkDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogSimple();
                    new deleteBookmarkAsync(context).execute(idx);
                }
            });
        }

        postTitle.setText(post.getTitle());
        postDate.setText(post.getDate());
        postLikeCntIcon.setTypeface(fontAwesomeS);
        postLikeCntText.setText(post.getLikeCount()+" ");
        postCommentCntIcon.setTypeface(fontAwesomeS);
        postCommentCntText.setText(post.getCommentCount()+" ");
        postScrapCntIcon.setTypeface(fontAwesomeS);
//        postScrapCntText.setText(post.getScrapCount()+" ");

        return v;
    }

    private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
        alt_bld.setMessage("북마크를 삭제하시겠습니까?").setCancelable(
                false).setPositiveButton("YES",
                // TODO 북마크 삭제 버튼 클릭 처리해야 합니다.
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("UserPostListA.._test", "북마크 삭제 버튼 클릭");

                        dialog.cancel();
                    }
                }).setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }
}

class deleteBookmarkAsync extends AsyncTask<Integer, Integer, Void> {

    private Context context;
    private Dbhelper dbhelper;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public deleteBookmarkAsync(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Integer... ints){
        ArrayList<Post> postings = new ArrayList<>();

        HttpReqRes httpReqRes = new HttpReqRes();
        dbhelper = new Dbhelper(context);

        httpReqRes.requestHttpPosting("https://dna.soyoungpark.me:9013/api/posting/bookmark/" + ints[0], dbhelper.getAccessToken(), 4);

        return null;
    }

//    @Override
//    protected void onPostExecute(ArrayList<Post> postings) {
//
//        super.onPostExecute(postings);
//    }
}
