package com.konkuk.dna;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.konkuk.dna.chat.ChatActivity;
import com.konkuk.dna.chat.ChatUser;
import com.konkuk.dna.chat.ChatUserAdapter;
import com.konkuk.dna.post.PostFormActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private View mapFragment;

    private ValueAnimator slideAnimator;
    private AnimatorSet set;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        menuDrawer = findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y - Helpers.dpToPx(this, 50);

        mapFragment = (View) findViewById(R.id.mapFragment);

        slideAnimator = ValueAnimator
            .ofInt(height, Helpers.dpToPx(this, 150)).setDuration(300);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mapFragment.getLayoutParams().height = value.intValue();
                mapFragment.requestLayout();
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

        mapFragment.getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
        mapFragment.requestLayout();
    }
}