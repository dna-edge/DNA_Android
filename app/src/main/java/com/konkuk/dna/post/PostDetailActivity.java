package com.konkuk.dna.post;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.konkuk.dna.utils.HttpReqRes;
import com.konkuk.dna.utils.dbmanage.Dbhelper;
import com.konkuk.dna.utils.helpers.BaseActivity;
import com.konkuk.dna.utils.helpers.InitHelpers;
import com.konkuk.dna.R;
import com.konkuk.dna.map.MapFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.konkuk.dna.utils.JsonToObj.PostingJsonToObj;

public class PostDetailActivity extends BaseActivity {
    protected DrawerLayout menuDrawer;
    private MapFragment mapFragment;
    private ScrollView postScrollView;
    private ImageView postAvatar;
    private ImageButton addFriendBtn;
    private TextView postNickname, postDate, postTitle, postContent,
    postLikeBtnIcon, postLikeBtnText, postScrapBtnIcon, postScrapBtnText,
    postLikeCnt, postCommentCnt, postScrapCnt;
    private EditText commentEdit;
    private ListView commentList;
    private CommentAdapter commentAdapter;
    private int idx;

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        idx = intent.getIntExtra("pidx", 0);
        new showPostingAsyncTask().execute(idx);

        init();
    }

    public void init() {
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        InitHelpers.initDrawer(this, menuDrawer, 2);

        postScrollView = (ScrollView) findViewById(R.id.postScrollView);
        postAvatar = (ImageView) findViewById(R.id.postAvatar);
        postNickname = (TextView) findViewById(R.id.postNickname);
        addFriendBtn = (ImageButton) findViewById(R.id.addFriendBtn);

        postDate = (TextView) findViewById(R.id.postDate);
        postTitle = (TextView) findViewById(R.id.postTitle);
        postContent = (TextView) findViewById(R.id.postContent);
        postLikeBtnIcon = (TextView) findViewById(R.id.postLikeBtnIcon);
        postLikeBtnText = (TextView) findViewById(R.id.postLikeBtnText);
        postScrapBtnIcon = (TextView) findViewById(R.id.postScrapBtnIcon);
        postScrapBtnText = (TextView) findViewById(R.id.postScrapBtnText);

        postLikeCnt = (TextView) findViewById(R.id.postLikeCnt);
        postCommentCnt = (TextView) findViewById(R.id.postCommentCnt);
        postScrapCnt = (TextView) findViewById(R.id.postScrapCnt);

//        commentEdit = (EditText) findViewById(R.id.commentEdit);
//        commentList = (ListView) findViewById(R.id.commentList);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);

        Post extra = (Post) getIntent().getSerializableExtra("post");
        post = (extra == null) ? new Post() : extra;

//        if (post.getAvatar() != null) {
//            Picasso.get().load(post.getAvatar()).into(postAvatar);
//        }
//        postNickname.setText(post.getNickname());

        // TODO 해당 유저가 나와 친구 관계인지 아닌지 확인하고, 친구 추가 버튼을 보여줍니다.
        if (false) { // TODO 여기에 [친구 관계가 아닌] 조건을 추가해주면 됩니다.
            addFriendBtn.setVisibility(View.GONE);
        }

        postDate.setText(post.getDate());
        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());

        // TODO 내가 좋아요를 누른 글인지, 스크랩 한 글인지에 따라 버튼 색깔이 달라져야 합니다.
        // TODO 지금은 좋아요와 스크랩 둘 다 해당하도록 설정되어 있는데,
        // TODO 해당하지 않을 경우 R.color.concrete를 적용해주면 됩니다!
        postLikeBtnIcon.setTextColor(getResources().getColor(R.color.alizarin));
        postLikeBtnText.setTextColor(getResources().getColor(R.color.alizarin));
        postScrapBtnIcon.setTextColor(getResources().getColor(R.color.sunflower));
        postScrapBtnText.setTextColor(getResources().getColor(R.color.sunflower));

        postLikeCnt.setText(post.getLikeCount()+"개");
//        postCommentCnt.setText(post.getCommentCount()+"개");
        postScrapCnt.setText(post.getScrapCount()+"개");
//        commentAdapter = new CommentAdapter(this, R.layout.post_comment_item, post.getComments());
//        commentList.setAdapter(commentAdapter);

        // 댓글 갯수에 맞춰서 height 설정하기
        final int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
//        for (int i = 0; i < commentAdapter.getCount(); i++) {
//            View childView = commentAdapter.getView(i, null, commentList);
//            childView.measure(UNBOUNDED, UNBOUNDED);
//            totalHeight += childView.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = commentList.getLayoutParams();
//        params.height = totalHeight + (commentList.getDividerHeight() * (commentList.getCount() - 1));
//        commentList.setLayoutParams(params);
//        commentList.requestLayout();

        // 생성된 후 최상단으로 스크롤을 올려줍니다
        postScrollView.smoothScrollTo(0, 0);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;

            case R.id.menuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    menuDrawer.openDrawer(Gravity.RIGHT);
                }
                break;

            case R.id.addFriendBtn: // 친구 추가 버튼 클릭 : 4
                Log.d("PostDetail", "add friend");
                break;

            case R.id.postLikeBtn: // 좋아요 버튼 클릭 : 1
                Log.d("PostDetail", "like");
                new PostingAsyncTask(this).execute(1, idx);
                break;

            case R.id.postShareBtn:
                FragmentManager fragmentManager = getFragmentManager();
                PostShareFragment postShareFragment = new PostShareFragment();

                postShareFragment.show(fragmentManager, "postShareFragment");
                break;

            case R.id.postScrapBtn: // 스크랩 버튼 클릭 : 2
                Log.d("PostDetail", "scrap");
                new PostingAsyncTask(this).execute(2, idx);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.initMapCenter(post.getLongitude(), post.getLatitude(), 0);
    }
}

class showPostingAsyncTask extends AsyncTask<Integer, Integer, Post> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Post doInBackground(Integer... ints) {
        HttpReqRes httpReqRes = new HttpReqRes();
        Post post = new Post();
        int idx = ints[0];

        String res = httpReqRes.requestHttpGetPosting("https://dna.soyoungpark.me:9013/api/posting/show/" + idx);
        post = PostingJsonToObj(res).get(0);

        return post;
    }

    @Override
    protected void onPostExecute(Post posting) {
        super.onPostExecute(posting);

    }
}

class PostingAsyncTask extends AsyncTask<Integer, Integer, Post> {
    Context context;
    Dbhelper dbhelper;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public PostingAsyncTask(Context context){ this.context = context; }

    @Override
    protected Post doInBackground(Integer... ints) {
        dbhelper = new Dbhelper(context);
        HttpReqRes httpReqRes = new HttpReqRes();
        Post post = new Post();
        int num = ints[0];
        String res = null;

        switch(num){
            case 1:     // 포스팅 북마크
                res = httpReqRes.requestHttpPosting("https://dna.soyoungpark.me:9013/api/posting/like/" + ints[1], dbhelper.getAccessToken(), 1);

                if(res.matches("(.*)201(.*)")){
                    Log.v("postdetail", "status : 201");
                    break;
                }
                else if(res.matches("(.*)400(.*)")){
                    Log.v("postdetail", "status : 400");
                    res = httpReqRes.requestHttpPosting("https://dna.soyoungpark.me:9013/api/posting/like/" + ints[1], dbhelper.getAccessToken(), 3);
                }
                break;

            case 2:     // 포스팅 북마크
                res = httpReqRes.requestHttpPosting("https://dna.soyoungpark.me:9013/api/posting/bookmark/" + ints[1], dbhelper.getAccessToken(), 2);

                if(res.matches("(.*)201(.*)")){
                    Log.v("postdetail", "status : 201");
                    break;
                }
                else if(res.matches("(.*)400(.*)")){
                    Log.v("postdetail", "status : 400");
                    res = httpReqRes.requestHttpPosting("https://dna.soyoungpark.me:9013/api/posting/bookmark/" + ints[1], dbhelper.getAccessToken(), 4);
                }
                break;

        }

        return post;
    }

    @Override
    protected void onPostExecute(Post posting) {
        super.onPostExecute(posting);

    }
}