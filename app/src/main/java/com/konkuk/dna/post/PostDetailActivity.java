package com.konkuk.dna.post;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.Helpers;
import com.konkuk.dna.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class PostDetailActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private PostFormMapFragment postFormMapFragment;
    private ScrollView postScrollView;
    private ImageView postAvatar;
    private TextView postNickname, postDate, postTitle, postContent, postAddress,
        postLikeCnt, postCommentCnt, postScrapCnt;
    private ListView commentList;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
    }

    public void init() {
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 0);

        postScrollView = (ScrollView) findViewById(R.id.postScrollView);
        postAvatar = (ImageView) findViewById(R.id.postAvatar);
        postNickname = (TextView) findViewById(R.id.postNickname);
        postDate = (TextView) findViewById(R.id.postDate);
        postTitle = (TextView) findViewById(R.id.postTitle);
        postContent = (TextView) findViewById(R.id.postContent);
//        postAddress = (TextView) findViewById(R.id.postAddress);
        postLikeCnt = (TextView) findViewById(R.id.postLikeCnt);
        postCommentCnt = (TextView) findViewById(R.id.postCommentCnt);
        postScrapCnt = (TextView) findViewById(R.id.postScrapCnt);
        commentList = (ListView) findViewById(R.id.commentList);
        postFormMapFragment = (PostFormMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        Post post;
        Post extra = (Post) getIntent().getSerializableExtra("post");
        post = (extra == null) ? new Post() : extra;

        if (post.getAvatar() != null) {
            Picasso.get().load(post.getAvatar()).into(postAvatar);
        }
        postNickname.setText(post.getNickname());
        postDate.setText(post.getDate());
        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
//        postAddress.setText("서울시 광진구 화양동 1 건국대학교");
        postLikeCnt.setText(post.getLikeCount()+"개");
        postCommentCnt.setText(post.getCommentCount()+"개");
        postScrapCnt.setText(post.getScrapCount()+"개");

        commentAdapter = new CommentAdapter(this, R.layout.post_comment_item, post.getComments());
        commentList.setAdapter(commentAdapter);

        // 댓글 갯수에 맞춰서 height 설정하기
        final int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        for (int i = 0; i < commentAdapter.getCount(); i++) {
            View childView = commentAdapter.getView(i, null, commentList);
            childView.measure(UNBOUNDED, UNBOUNDED);
            totalHeight += childView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = commentList.getLayoutParams();
        params.height = totalHeight + (commentList.getDividerHeight() * (commentList.getCount() - 1));
        commentList.setLayoutParams(params);
        commentList.requestLayout();

        // 생성된 후 최상단으로 스크롤을 올려줍니다
//        postScrollView.fullScroll(ScrollView.FOCUS_UP);
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
        }
    }
}
