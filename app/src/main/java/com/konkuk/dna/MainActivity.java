package com.konkuk.dna;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.konkuk.dna.chat.ChatActivity;
import com.konkuk.dna.helpers.AnimHelpers;
import com.konkuk.dna.helpers.BaseActivity;
import com.konkuk.dna.helpers.InitHelpers;
import com.konkuk.dna.map.MapFragment;
import com.konkuk.dna.post.Comment;
import com.konkuk.dna.post.Post;
import com.konkuk.dna.post.PostFormActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private MapFragment mapFragment;
    private View mapFragmentView;
    private FloatingActionButton gotoChatBtn, postWriteBtn;

    private ArrayList<Post> posts;

    private ValueAnimator slideAnimator;
    private AnimatorSet set;
    private int height, radius;
    private double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitHelpers.getPermission(this);

        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        menuDrawer = findViewById(R.id.drawer_layout);
        InitHelpers.initDrawer(this, menuDrawer, 0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;

        mapFragmentView = (View) findViewById(R.id.mapFragment);
        gotoChatBtn = (FloatingActionButton) findViewById(R.id.gotoChatBtn);
        postWriteBtn = (FloatingActionButton) findViewById(R.id.postWriteBtn);

        AnimHelpers.animateMargin(this, gotoChatBtn, "main", 400L,
                AnimHelpers.dpToPx(this, -80), AnimHelpers.dpToPx(this, 95));
        AnimHelpers.animateMargin(this, postWriteBtn, "main", 400L,
                AnimHelpers.dpToPx(this, -80), AnimHelpers.dpToPx(this, 25));

        radius = 500; // TODO 반경, 위치 초기값 설정해줘야 합니다!
        longitude = gpsTracker.getLongitude();
        latitude = gpsTracker.getLatitude();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);

        posts = new ArrayList<Post>();

        // TODO 포스트의 리스트를 서버에서 불러와서 넣어줘야 합니다.
        posts.add(new Post(0, "http://slingshotesports.com/wp-content/uploads/2017/07/34620595595_b4c90a2e22_b.jpg",
                "3457soso", "2018.10.05", "제목입니다",
                "이건 내용인데 사실 많이 쓸 필요는 없긴 한데... \n그래도 왠지 많이 써야할 것 같아서 쓰긴 씁니다.\n메롱메롱\n페이커가 최고임",
                127.081958, 37.537484, 1, 2, 3,
                new ArrayList<Comment>(
                        Arrays.asList(new Comment(null,"test","2018.10.05","이건 댓글입니다."),
                                new Comment(null,"test","2018.10.05","이건 댓글입니다."))
                )
        ));
        posts.add(new Post(1, "http://slingshotesports.com/wp-content/uploads/2017/07/34620595595_b4c90a2e22_b.jpg",
                "3457soso", "2018.10.05", "제목입니다22",
                "이건 내용인데 사실 많이 쓸 필요는 없긴 한데... \n그래도 왠지 많이 써야할 것 같아서 쓰긴 씁니다.\n메롱메롱\n페이커가 최고임",
                127.083559, 37.536543, 1, 2, 3,
                new ArrayList<Comment>(
                        Arrays.asList(new Comment(null,"test","2018.10.05","이건 댓글입니다."),
                                new Comment(null,"test","2018.10.05","이건 댓글입니다."))
                )
        ));

        slideAnimator = ValueAnimator
            .ofInt(height, AnimHelpers.dpToPx(this, 150)).setDuration(500);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapFragmentView.getLayoutParams();
                params.height = value.intValue();
                int divider = height / 50;
                params.setMargins(0, AnimHelpers.dpToPx(getApplicationContext(), (AnimHelpers.dpToPx(getApplicationContext(), 150) + height-value)/divider), 0, 0);
                mapFragmentView.requestLayout();
            }
        });

        slideAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(chatIntent);
                overridePendingTransition(0, R.anim.fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        set = new AnimatorSet();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgMenuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    menuDrawer.openDrawer(Gravity.RIGHT);
                }

                break;

            case R.id.gotoChatBtn:
                set.play(slideAnimator);
                set.setInterpolator(new AccelerateDecelerateInterpolator());
                set.start();
                AnimHelpers.animateMargin(this, gotoChatBtn, "main", 400L,
                        AnimHelpers.dpToPx(this, 95), AnimHelpers.dpToPx(this, -80));
                AnimHelpers.animateMargin(this, postWriteBtn, "main", 400L,
                        AnimHelpers.dpToPx(this, 25), AnimHelpers.dpToPx(this, -80));
                break;

            case R.id.postWriteBtn:
                Intent formIntent = new Intent(this, PostFormActivity.class);
                startActivity(formIntent);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mapFragmentView.getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
        mapFragmentView.requestLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mapFragment.initMapCenter(longitude, latitude, radius);
        mapFragment.drawPostLocations(posts);
    }
}