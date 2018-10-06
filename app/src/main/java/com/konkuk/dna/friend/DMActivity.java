package com.konkuk.dna.friend;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.Helpers;
import com.konkuk.dna.R;
import com.konkuk.dna.chat.ChatActivity;
import com.konkuk.dna.chat.ChatListAdapter;
import com.konkuk.dna.chat.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DMActivity extends BaseActivity {
    private DrawerLayout menuDrawer;

    private ListView messageListView;
    private EditText messageEditText;
    private Button speakerBtn, locationBtn, imageBtn;
    private ArrayList<ChatMessage> chatMessages;
    private ChatListAdapter chatListAdapter;

    private SimpleDateFormat timeFormat;

    /* 메시지의 타입을 구분하기 위한 변수들입니다 */
    private final String TYPE_MESSAGE = "Message";     // 일반 메시지 전송
    private final String TYPE_LOUDSPEAKER = "LoudSpeaker"; // 확성기 전송
    private final String TYPE_LOCATION = "Location";    // 현재 위치 전송
    private final String TYPE_IMAGE = "Image";       // 이미지 전송
    private String messageType = TYPE_MESSAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_dm);

        init();
    }

    public void init() {
        menuDrawer = findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 1);

        messageListView = (ListView) findViewById(R.id.msgListView);
        messageEditText = (EditText) findViewById(R.id.msgEditText);
        speakerBtn = (Button) findViewById(R.id.msgSpeakerBtn);
        locationBtn = (Button) findViewById(R.id.msgLocationBtn);
        imageBtn = (Button) findViewById(R.id.msgImageBtn);

        // TODO chatMessages 배열에 실제 메시지 추가해야 합니다.
        chatMessages = new ArrayList<ChatMessage>();
//        chatMessages.add(new ChatMessage("3457soso", null, "http://file3.instiz.net/data/cached_img/upload/2018/06/22/14/2439cadf98e7bebdabd174ed41ca0849.jpg", "오후 12:34", "0", TYPE_IMAGE));
//        chatMessages.add(new ChatMessage("3457soso", null, "내용내용", "오후 12:34", "2", TYPE_LOUDSPEAKER));
//        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용내용내용", "오후 12:34", "1", TYPE_MESSAGE));
//        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용내용내용", "오후 12:34", "1", TYPE_LOUDSPEAKER));
//        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용", "오후 12:34", "0", TYPE_MESSAGE));
//        chatMessages.add(new ChatMessage("3457soso", null, "내용내용내용", "오후 12:34", "0", TYPE_MESSAGE));
//        chatMessages.add(new ChatMessage("3457soso", null, "{\"lat\":37.550544099999996,\"lng\":127.07221989999998}", "오후 12:34", "1", TYPE_LOCATION));
//        chatMessages.add(new ChatMessage("3457soso", null, "http://www.ohfun.net/contents/article/images/2016/0830/1472551795750578.jpeg", "오후 12:34", "2", TYPE_IMAGE));
//        chatMessages.add(new ChatMessage("3457soso", null, "내용내용", "오후 12:34", "2", TYPE_MESSAGE));

        chatListAdapter = new ChatListAdapter(this, R.layout.chat_item_left, chatMessages);
        messageListView.setAdapter(chatListAdapter);

        // 생성된 후 바닥으로 메시지 리스트를 내려줍니다.
        messageListView.post(new Runnable(){
            public void run() {
                messageListView.setSelection(messageListView.getCount() - 1);
            }});

        timeFormat = new SimpleDateFormat("a h:m", Locale.KOREA);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backBtn:
                Intent friendIntent = new Intent(this, FriendActivity.class);
                startActivity(friendIntent);

            case R.id.msgSearchBtn: // 검색 버튼 클릭
                break;

            case R.id.msgMenuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) { menuDrawer.openDrawer(Gravity.RIGHT); }

                break;

            case R.id.msgChatBtn: // 채팅 버튼 클릭
                Intent chatIntent = new Intent(this, ChatActivity.class);
                startActivity(chatIntent);
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
        }
    }
}
