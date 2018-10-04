package com.konkuk.dna.chat;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.R;
import com.konkuk.dna.dm.RoomActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private LinearLayout drawerList;
    private ImageView pfAvatar;
    private TextView pfNickname, pfID, pfInfo, addressText;
    private ListView ccuListView;
    private ArrayList<ChatUser> chatUsers;
    private ChatUserAdapter chatUserAdapter;

    private ListView messageListView;
    private EditText messageEditText;
    private ImageButton menuBtn, findBtn, friendBtn;
    private Button speakerBtn, locationBtn, imageBtn, sendBtn;
    private ArrayList<ChatMessage> chatMessages;
    private ChatListAdapter chatListAdapter;

    private SimpleDateFormat timeFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
    }

    public void init() {
        pfAvatar = (ImageView) findViewById(R.id.msgPfAvatar);
        pfNickname = (TextView) findViewById(R.id.msgPfNickname);
        pfID = (TextView) findViewById(R.id.msgPfID);
        pfInfo = (TextView) findViewById(R.id.msgPfInfo);
        ccuListView = (ListView) findViewById(R.id.ccuList);

        addressText = (TextView) findViewById(R.id.addressText);

        messageListView = (ListView) findViewById(R.id.msgListView);
        messageEditText = (EditText) findViewById(R.id.msgEditText);
        menuBtn = (ImageButton) findViewById(R.id.msgMenuBtn);
        findBtn = (ImageButton) findViewById(R.id.msgFindBtn);
        friendBtn = (ImageButton) findViewById(R.id.msgFriendBtn);
        speakerBtn = (Button) findViewById(R.id.msgSpeakerBtn);
        locationBtn = (Button) findViewById(R.id.msgLocationBtn);
        imageBtn = (Button) findViewById(R.id.msgImageBtn);
        sendBtn = (Button) findViewById(R.id.msgSendBtn);

        // 우측 메뉴 생성
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (LinearLayout) findViewById(R.id.right_drawer);


        // TODO 프로필 및 현재 주소 값 채우기
        addressText.setText("서울시 광진구");
//        pfAvatar =
        pfNickname.setText("soyoungpark");
        pfID.setText("3457soso");
        pfInfo.setText("이것은 자기소개인 것입니다. 길게 써야 그럴 듯 하니까 길게 쓰도록 하겠습니다. 할 말이 떨어져 가니까 그만 써야지!");


        // TODO chatUsers 배열에 실제 접속중인 유저 리스트 추가해야 합니다.
        chatUsers = new ArrayList<ChatUser>();
        chatUsers.add(new ChatUser("3457soso", null, true));
        chatUsers.add(new ChatUser("test", null, true));
        chatUsers.add(new ChatUser("test2", null, false));
        chatUserAdapter = new ChatUserAdapter(this, R.layout.chat_item_ccu, chatUsers);
        ccuListView.setAdapter(chatUserAdapter);


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
    }

    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.msgFindBtn: // 검색 버튼 클릭
                break;

            case R.id.msgMenuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) { menuDrawer.openDrawer(Gravity.RIGHT); }

                break;

            case R.id.msgChatBtn: // 친구 리스트 (DM 채팅 리스트) 버튼 클릭
                Intent intent = new Intent(this, RoomActivity.class);
                startActivity(intent);
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

            case R.id.pfModifyBtn: // 프로필 설정 버튼 클릭
                break;
        }
    }
}
