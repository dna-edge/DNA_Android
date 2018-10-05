package com.konkuk.dna.friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.R;
import com.konkuk.dna.chat.ChatActivity;
import com.konkuk.dna.friend.fragments.FriendFragment;
import com.konkuk.dna.friend.fragments.NotifyFragment;
import com.konkuk.dna.friend.fragments.RoomFragment;
import com.konkuk.dna.friend.fragments.SettingFragment;

public class RoomActivity extends BaseActivity implements View.OnClickListener {
    final int ROOM_FRAGMENT = 1;
    final int FRIEND_FRAGMENT = 2;
    final int NOTIFY_FRAGMENT = 3;
    final int SETTING_FRAGMENT = 4;

    private Button roomBtn, friendBtn, notifyBtn, settingBtn;
    private FrameLayout roomFragContainer;

    int currentFragment; // 현재 프래그먼트 구별하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_room);

        init();
    }

    public void init() {
        roomFragContainer = (FrameLayout) findViewById(R.id.roomFragContainer);
        roomBtn = (Button) findViewById(R.id.roomBtn);
        friendBtn = (Button) findViewById(R.id.friendBtn);
        notifyBtn = (Button) findViewById(R.id.notifyBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);

        roomBtn.setOnClickListener(this);
        friendBtn.setOnClickListener(this);
        notifyBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);

        /* 초기 프래그먼트는 채팅방 리스트 프래그먼트라 여기서 먼저 초기화해줍니다! */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RoomFragment roomFragment = (RoomFragment) new RoomFragment();
        transaction.replace(R.id.roomFragContainer, roomFragment);
        transaction.commit();

        roomBtn.setSelected(true);
        currentFragment = ROOM_FRAGMENT;

        /* 좌측, 우측 밀기 이벤트 적용 */
        roomFragContainer.setOnTouchListener(new OnSwipeTouchListener(RoomActivity.this) {
            public void onSwipeRight() { // to left
                if (currentFragment > 1) {
                    callFragment(currentFragment - 1);
                    SlideAnimationUtil.slideInToRight(RoomActivity.this, roomFragContainer);
                }
            }

            public void onSwipeLeft() { // to right
                if (currentFragment < 4) {
                    callFragment(currentFragment + 1);
                    SlideAnimationUtil.slideInFromRight(RoomActivity.this, roomFragContainer);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgChatBtn:
                Intent chatIntent = new Intent(this, ChatActivity.class);
                startActivity(chatIntent);
                break;
            case R.id.roomBtn:
                callFragment(ROOM_FRAGMENT);
                break;
            case R.id.friendBtn:
                callFragment(FRIEND_FRAGMENT);
                break;
            case R.id.notifyBtn:
                callFragment(NOTIFY_FRAGMENT);
                break;
            case R.id.settingBtn:
                callFragment(SETTING_FRAGMENT);
                break;
        }
    }

    private void callFragment(int fragmentNumber) {
        if (fragmentNumber != currentFragment) {
            if (fragmentNumber > currentFragment) {
                SlideAnimationUtil.slideInFromRight(RoomActivity.this, roomFragContainer);
            } else {
                SlideAnimationUtil.slideInToRight(RoomActivity.this, roomFragContainer);
            }
            roomBtn.setSelected(false);
            friendBtn.setSelected(false);
            notifyBtn.setSelected(false);
            settingBtn.setSelected(false);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

            /* 현재 프래그먼트와 다른 프래그먼트를 호출할 경우 replace 해줍니다 */
            switch (fragmentNumber) {
                case ROOM_FRAGMENT:
                    roomBtn.setSelected(true);
                    RoomFragment roomFragment = (RoomFragment) new RoomFragment();
                    transaction.replace(R.id.roomFragContainer, roomFragment);
                    transaction.commit();
                    break;

                case FRIEND_FRAGMENT:
                    friendBtn.setSelected(true);
                    FriendFragment friendFragment = (FriendFragment) new FriendFragment();
                    transaction.replace(R.id.roomFragContainer, friendFragment);
                    transaction.commit();
                    break;

                case NOTIFY_FRAGMENT:
                    notifyBtn.setSelected(true);
                    NotifyFragment notifyFragment = (NotifyFragment) new NotifyFragment();
                    transaction.replace(R.id.roomFragContainer, notifyFragment);
                    transaction.commit();
                    break;

                case SETTING_FRAGMENT:
                    settingBtn.setSelected(true);
                    SettingFragment settingFragment = (SettingFragment) new SettingFragment();
                    transaction.replace(R.id.roomFragContainer, settingFragment);
                    transaction.commit();
                    break;
            }

            currentFragment = fragmentNumber;
        }
    }
}

class OnSwipeTouchListener implements View.OnTouchListener {
    final GestureDetector gestureDetector;
    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {}
    public void onSwipeLeft() {}
    public void onSwipeTop() {}
    public void onSwipeBottom() {}
}

class SlideAnimationUtil {
    public static void slideInToRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_right);
    }

    public static void slideInFromRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_right);
    }

    private static void runSimpleAnimation(Context context, View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(
                context, animationId
        ));
    }
}
