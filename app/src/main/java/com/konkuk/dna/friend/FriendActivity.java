package com.konkuk.dna.friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.Helpers;
import com.konkuk.dna.R;
import com.konkuk.dna.chat.ChatActivity;
import com.konkuk.dna.friend.fragments.FriendFragment;
import com.konkuk.dna.friend.fragments.NotifyFragment;
import com.konkuk.dna.friend.fragments.RoomFragment;
import com.konkuk.dna.post.PostFormMapFragment;

public class FriendActivity extends BaseActivity implements View.OnClickListener {
    final int ROOM_FRAGMENT = 1;
    final int FRIEND_FRAGMENT = 2;
    final int NOTIFY_FRAGMENT = 3;

    private DrawerLayout menuDrawer;
    private Button roomBtn, friendBtn, notifyBtn;
    private ViewPager roomFragContainer;

//    private FrameLayout roomFragContainer;

    int currentFragment; // 현재 프래그먼트 구별하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        init();
    }

    public void init() {
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 2);
        roomFragContainer = (ViewPager) findViewById(R.id.roomFragContainer);
        roomBtn = (Button) findViewById(R.id.roomBtn);
        friendBtn = (Button) findViewById(R.id.friendBtn);
        notifyBtn = (Button) findViewById(R.id.notifyBtn);

        final Button buttons[] = new Button[]{ roomBtn, friendBtn, notifyBtn };

        roomFragContainer.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        roomFragContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                for(Button button: buttons) {
                    button.setSelected(false);
                }
                buttons[position].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        roomFragContainer.setCurrentItem(0);
        buttons[0].setSelected(true);

        roomBtn.setOnClickListener(movePageListener);
        friendBtn.setOnClickListener(movePageListener);
        notifyBtn.setOnClickListener(movePageListener);

//        /* 초기 프래그먼트는 채팅방 리스트 프래그먼트라 여기서 먼저 초기화해줍니다! */
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        RoomFragment roomFragment = (RoomFragment) new RoomFragment();
//        transaction.replace(R.id.roomFragContainer, roomFragment);
//        transaction.commit();
//
//        roomBtn.setSelected(true);
//        currentFragment = ROOM_FRAGMENT;

        /* 좌측, 우측 밀기 이벤트 적용 */
//        roomFragContainer.setOnTouchListener(new OnSwipeTouchListener(FriendActivity.this) {
//            public void onSwipeRight() { // to left
//                Log.d("test", "right");
//                if (currentFragment > 1) {
//                    callFragment(currentFragment - 1);
//                    SlideAnimationUtil.slideInToRight(FriendActivity.this, roomFragContainer);
//                }
//            }
//
//            public void onSwipeLeft() { // to right
//                Log.d("test", "left");
//                if (currentFragment < 3) {
//                    callFragment(currentFragment + 1);
//                    SlideAnimationUtil.slideInFromRight(FriendActivity.this, roomFragContainer);
//                }
//            }
//        });
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int)((String)v.getTag()).charAt(0) - 48;
            roomFragContainer.setCurrentItem(tag);
        }
    };

    @Override
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
//
//            case R.id.roomBtn:
//                callFragment(ROOM_FRAGMENT);
//                break;
//
//            case R.id.friendBtn:
//                callFragment(FRIEND_FRAGMENT);
//                break;
//
//            case R.id.notifyBtn:
//                callFragment(NOTIFY_FRAGMENT);
//                break;
        }
    }

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new RoomFragment();
                case 1:
                    return new FriendFragment();
                case 2:
                    return new NotifyFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }
}

//    private void callFragment(int fragmentNumber) {
//        if (fragmentNumber != currentFragment) {
//            if (fragmentNumber > currentFragment) {
//                SlideAnimationUtil.slideInFromRight(FriendActivity.this, roomFragContainer);
//            } else {
//                SlideAnimationUtil.slideInToRight(FriendActivity.this, roomFragContainer);
//            }
//            roomBtn.setSelected(false);
//            friendBtn.setSelected(false);
//            notifyBtn.setSelected(false);
//
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
//
//            /* 현재 프래그먼트와 다른 프래그먼트를 호출할 경우 replace 해줍니다 */
//            switch (fragmentNumber) {
//                case ROOM_FRAGMENT:
//                    roomBtn.setSelected(true);
//                    RoomFragment roomFragment = (RoomFragment) new RoomFragment();
//                    transaction.replace(R.id.roomFragContainer, roomFragment);
//                    transaction.commit();
//                    break;
//
//                case FRIEND_FRAGMENT:
//                    friendBtn.setSelected(true);
//                    FriendFragment friendFragment = (FriendFragment) new FriendFragment();
//                    transaction.replace(R.id.roomFragContainer, friendFragment);
//                    transaction.commit();
//                    break;
//
//                case NOTIFY_FRAGMENT:
//                    notifyBtn.setSelected(true);
//                    NotifyFragment notifyFragment = (NotifyFragment) new NotifyFragment();
//                    transaction.replace(R.id.roomFragContainer, notifyFragment);
//                    transaction.commit();
//                    break;
//            }
//
//            currentFragment = fragmentNumber;
//        }
//    }

//class OnSwipeTouchListener implements View.OnTouchListener {
//    final GestureDetector gestureDetector;
//    public OnSwipeTouchListener (Context ctx){
//        gestureDetector = new GestureDetector(ctx, new GestureListener());
//    }
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }
//
//    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//        private static final int SWIPE_THRESHOLD = 100;
//        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            boolean result = false;
//            try {
//                float diffY = e2.getY() - e1.getY();
//                float diffX = e2.getX() - e1.getX();
//                if (Math.abs(diffX) > Math.abs(diffY)) {
//                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffX > 0) {
//                            onSwipeRight();
//                        } else {
//                            onSwipeLeft();
//                        }
//                        result = true;
//                    }
//                }
//                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                    if (diffY > 0) {
//                        onSwipeBottom();
//                    } else {
//                        onSwipeTop();
//                    }
//                    result = true;
//                }
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            return result;
//        }
//    }
//
//    public void onSwipeRight() {}
//    public void onSwipeLeft() {}
//    public void onSwipeTop() {}
//    public void onSwipeBottom() {}
//}
//
//class SlideAnimationUtil {
//    public static void slideInToRight(Context context, View view) {
//        runSimpleAnimation(context, view, R.anim.slide_to_right);
//    }
//
//    public static void slideInFromRight(Context context, View view) {
//        runSimpleAnimation(context, view, R.anim.slide_from_right);
//    }
//
//    private static void runSimpleAnimation(Context context, View view, int animationId) {
//        view.startAnimation(AnimationUtils.loadAnimation(
//                context, animationId
//        ));
//    }
//}
