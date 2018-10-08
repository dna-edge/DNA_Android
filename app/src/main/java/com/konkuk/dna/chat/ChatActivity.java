package com.konkuk.dna.chat;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.Helpers;
import com.konkuk.dna.MainActivity;
import com.konkuk.dna.R;
import com.konkuk.dna.map.MapFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private MapFragment mapFragment;
    private View mapFragmentView;
    private RelativeLayout barLayout;

    private ListView msgListView;
    private EditText msgEditText;
    private Button msgSpeakerBtn, msgLocationBtn, msgImageBtn;
    private ChatListAdapter chatListAdapter;
    private ArrayList<ChatMessage> chatMessages;

    private SimpleDateFormat timeFormat;

    private ValueAnimator slideAnimator;
    private AnimatorSet set;
    private int height, radius;
    private double longitude, latitude;

    /* 메시지의 타입을 구분하기 위한 변수들입니다 */
    private final String TYPE_MESSAGE = "Message";     // 일반 메시지 전송
    private final String TYPE_LOUDSPEAKER = "LoudSpeaker"; // 확성기 전송
    private final String TYPE_LOCATION = "Location";    // 현재 위치 전송
    private final String TYPE_IMAGE = "Image";       // 이미지 전송
    private String messageType = TYPE_MESSAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
    }

    public void init() {
        menuDrawer = findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 0);

        mapFragmentView = (View) findViewById(R.id.mapFragment);
        barLayout = (RelativeLayout) findViewById(R.id.barLayout);
        msgListView = (ListView) findViewById(R.id.msgListView);
        msgEditText = (EditText) findViewById(R.id.msgEditText);
        msgSpeakerBtn = (Button) findViewById(R.id.msgSpeakerBtn);
        msgLocationBtn = (Button) findViewById(R.id.msgLocationBtn);
        msgImageBtn = (Button) findViewById(R.id.msgImageBtn);

        radius = 500; // TODO 반경, 위치 초기값 설정해줘야 합니다!
        longitude = 127.07934279999995;
        latitude = 37.5407625;

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);


        // TODO chatMessages 배열에 실제 메시지 추가해야 합니다.
        chatMessages = new ArrayList<ChatMessage>();
        chatMessages.add(new ChatMessage(0, "3457soso", null, "http://file3.instiz.net/data/cached_img/upload/2018/06/22/14/2439cadf98e7bebdabd174ed41ca0849.jpg", "오후 12:34", "0", TYPE_IMAGE, 127.07934279999995, 37.5407625));
        chatMessages.add(new ChatMessage(1, "3457soso", null, "내용내용", "오후 12:34", "2", TYPE_LOUDSPEAKER, 127.0793427999999, 37.540762));
        chatMessages.add(new ChatMessage(2, "3457soso", null, "내용내용내용내용내용", "오후 12:34", "1", TYPE_MESSAGE, 127.079342799995, 37.540625));
        chatMessages.add(new ChatMessage(3, "3457soso", null, "내용내용내용내용내용", "오후 12:34", "1", TYPE_LOUDSPEAKER, 127.0734279999995, 37.5407625));
        chatMessages.add(new ChatMessage(4, "3457soso", null, "내용내용내용", "오후 12:34", "0", TYPE_MESSAGE, 127.0794279999995, 37.507625));
        chatMessages.add(new ChatMessage(5, "3457soso", null, "내용내용내용", "오후 12:34", "0", TYPE_MESSAGE, 127.0793427999995, 37.540625));
        chatMessages.add(new ChatMessage(6, "3457soso", null, "{\"lat\":37.550544099999996,\"lng\":127.07221989999998}", "오후 12:34", "1", TYPE_LOCATION, 127.07934279999995, 37.540762));
        chatMessages.add(new ChatMessage(7, "3457soso", null, "http://www.ohfun.net/contents/article/images/2016/0830/1472551795750578.jpeg", "오후 12:34", "2", TYPE_IMAGE, 127.079342799995, 37.5407625));
        chatMessages.add(new ChatMessage(8, "3457soso", null, "내용내용", "오후 12:34", "2", TYPE_MESSAGE, 127.0793427999995, 37.540625));
        chatMessages.add(new ChatMessage(10, "3457soso", null, "{\"lat\":37.550544099999,\"lng\":127.07221989999}", "오후 12:34", "1", TYPE_LOCATION, 127.07934279999995, 37.540762));

        chatListAdapter = new ChatListAdapter(this, R.layout.chat_item_left, chatMessages);
        msgListView.setAdapter(chatListAdapter);

        // 생성된 후 바닥으로 메시지 리스트를 내려줍니다.
        scrollMyListViewToBottom();

        timeFormat = new SimpleDateFormat("a h:m", Locale.KOREA);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;

        slideAnimator = ValueAnimator
                .ofInt(Helpers.dpToPx(this, 150), height).setDuration(400);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mapFragmentView.getLayoutParams();
                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) barLayout.getLayoutParams();
                params.height = value.intValue();
                int divider = (height - Helpers.dpToPx(getApplicationContext(), 150)) / 50;
                params2.setMargins(0, -1 * Helpers.dpToPx(getApplicationContext(), value - Helpers.dpToPx(getApplicationContext(), 150))/divider, 0, 0);
                mapFragmentView.requestLayout();
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

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.msgSearchBtn: // 검색 버튼 클릭
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
                if (messageType.equals(TYPE_LOUDSPEAKER)) { // 확성기 모드일 경우 다시 누르면 취소됩니다.
                    msgSpeakerBtn.setTextColor(getResources().getColor(R.color.concrete));
                    messageType = TYPE_MESSAGE;
                } else {
                    msgSpeakerBtn.setTextColor(getResources().getColor(R.color.red));
                    messageType = TYPE_LOUDSPEAKER;
                }
                break;

            case R.id.msgLocationBtn: // 장소 전송 버튼 클릭
                // TODO 현재 주소를 messageEditText에 채워줍니다.
                if (messageType.equals(TYPE_MESSAGE) || messageType.equals(TYPE_LOUDSPEAKER)) {
                    msgLocationBtn.setTextColor(getResources().getColor(R.color.colorRipple));
                    msgEditText.setText("서울시 광진구 화양동 1 건국대학교");
                    msgEditText.setEnabled(false);
                    messageType = TYPE_LOCATION;
                } else {
                    DialogSimple();
                    messageType = TYPE_MESSAGE;
                }
                break;

            case R.id.msgImageBtn: // 이미지 전송 버튼 클릭
                // TODO 현재 주소를 messageEditText에 채워줍니다.
                if (messageType.equals(TYPE_MESSAGE) || messageType.equals(TYPE_LOUDSPEAKER)) {
                    msgImageBtn.setTextColor(getResources().getColor(R.color.colorRipple));
                    msgEditText.setText("Doraemon.png");
                    msgEditText.setEnabled(false);
                    messageType = TYPE_IMAGE;
                } else {
                    DialogSimple();
                    messageType = TYPE_MESSAGE;
                }
                break;

            case R.id.msgSendBtn: // 메시지 전송 버튼 클릭
                break;
        }
    }

    private void scrollMyListViewToBottom() {
        msgListView.post(new Runnable() {
            @Override
            public void run() {
                msgListView.clearFocus();
                chatListAdapter.notifyDataSetChanged();
                msgListView.requestFocusFromTouch();
                msgListView.setSelection(msgListView.getCount() - 1);
            }
        });
    }

    private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("메시지 타입을 초기화 하시겠습니까?").setCancelable(
                false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        msgLocationBtn.setTextColor(getResources().getColor(R.color.concrete));
                        msgImageBtn.setTextColor(getResources().getColor(R.color.concrete));
                        msgEditText.setEnabled(true);
                        msgEditText.setText(null);
                        messageType = (messageType == TYPE_LOUDSPEAKER) ? TYPE_LOUDSPEAKER : TYPE_MESSAGE;

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

    @Override
    public void onBackPressed() {
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.initMapCenter(longitude, latitude, radius);
    }
}
