package com.konkuk.dna.chat;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.Helpers;
import com.konkuk.dna.MainActivity;
import com.konkuk.dna.R;
import com.konkuk.dna.friend.DMActivity;
import com.konkuk.dna.friend.FriendActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private View mapFragment;

    private ListView messageListView;
    private EditText messageEditText;
    private Button speakerBtn, locationBtn, imageBtn;
    private ArrayList<ChatMessage> chatMessages;
    private ChatListAdapter chatListAdapter;

    private SimpleDateFormat timeFormat;

    private ValueAnimator slideAnimator;
    private AnimatorSet set;
    private int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
    }

    public void init() {
        menuDrawer = findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 0);

        mapFragment = (View) findViewById(R.id.chatMapFragment);
        messageListView = (ListView) findViewById(R.id.msgListView);
        messageEditText = (EditText) findViewById(R.id.msgEditText);
        speakerBtn = (Button) findViewById(R.id.msgSpeakerBtn);
        locationBtn = (Button) findViewById(R.id.msgLocationBtn);
        imageBtn = (Button) findViewById(R.id.msgImageBtn);

        // TODO chatMessages 배열에 실제 메시지 추가해야 합니다.
        chatMessages = new ArrayList<ChatMessage>();
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용", "오후 12:34", "0"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용내용내용", "오후 12:34", "1"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용", "오후 12:34", "2"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용", "오후 12:34", "0"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용내용내용", "오후 12:34", "1"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용", "오후 12:34", "2"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용", "오후 12:34", "0"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용내용내용", "오후 12:34", "1"));
        chatMessages.add(new ChatMessage("3457soso", null, "내용내용", "오후 12:34", "2"));

        chatListAdapter = new ChatListAdapter(this, R.layout.chat_item_left, chatMessages);
        messageListView.setAdapter(chatListAdapter);

        // 생성된 후 바닥으로 메시지 리스트를 내려줍니다.
        messageListView.post(new Runnable(){
            public void run() {
                messageListView.setSelection(messageListView.getCount() - 1);
        }});

        timeFormat = new SimpleDateFormat("a h:m", Locale.KOREA);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;

        slideAnimator = ValueAnimator
                .ofInt(Helpers.dpToPx(this, 150), height).setDuration(300);

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
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(0, R.anim.fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        set = new AnimatorSet();
    }

    @Override
    public void onBackPressed() {
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.msgFindBtn: // 검색 버튼 클릭
                break;

            case R.id.backBtn: // 뒤로가기 버튼 클릭
                this.onBackPressed();
                break;

            case R.id.menuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    menuDrawer.openDrawer(Gravity.RIGHT);
                }
                break;

            case R.id.msgSpeakerBtn: // 확성기 버튼 클릭
                // TODO 현재 유저의 포인트를 계산해서 사용 가능할 경우에만 활성화해야 합니다.
                speakerBtn.setTextColor(getResources().getColor(R.color.red));
                break;

            case R.id.msgLocationBtn: // 장소 전송 버튼 클릭
                // TODO 현재 주소 messageEditText에 채워줍니다.
                locationBtn.setTextColor(getResources().getColor(R.color.grayDarker));
                messageEditText.setText("서울시 광진구 화양동 1");
                break;

            case R.id.msgImageBtn: // 이미지 전송 버튼 클릭를
                // TODO 이미지를 선택하고, 해당 이미지의 이름을 messageEditText에 채워줍니다.
                imageBtn.setTextColor(getResources().getColor(R.color.grayDarker));
                break;

            case R.id.msgSendBtn: // 메시지 전송 버튼 클릭
                break;

            case R.id.msgSettingBtn: // 채팅 환경 설정 버튼 클릭
                break;

            case R.id.friendBtn: // 친구 관리 버튼 클릭
                Intent friendIntent = new Intent(this, FriendActivity.class);
                startActivity(friendIntent);
                break;
        }
    }
}
