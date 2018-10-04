package com.konkuk.dna.dm;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
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
import com.konkuk.dna.chat.ChatActivity;
import com.konkuk.dna.chat.ChatListAdapter;
import com.konkuk.dna.chat.ChatMessage;
import com.konkuk.dna.chat.ChatUser;
import com.konkuk.dna.chat.ChatUserAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DMActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private ImageView pfAvatar, friendAvatar;
    private TextView pfNickname, pfID, pfInfo, friendNickname, friendInfo, friendNicknameText;

    private ListView messageListView;
    private EditText messageEditText;
    private ImageButton menuBtn, findBtn, chatBtn;
    private Button speakerBtn, locationBtn, imageBtn, sendBtn;
    private ArrayList<ChatMessage> chatMessages;
    private ChatListAdapter chatListAdapter;

    private SimpleDateFormat timeFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm);

        init();
    }

    public void init() {
        pfAvatar = (ImageView) findViewById(R.id.msgPfAvatar);
        pfNickname = (TextView) findViewById(R.id.msgPfNickname);
        pfID = (TextView) findViewById(R.id.msgPfID);
        pfInfo = (TextView) findViewById(R.id.msgPfInfo);

        friendAvatar = (ImageView) findViewById(R.id.friendAvatar);
        friendNickname = (TextView) findViewById(R.id.friendNickname);
        friendInfo = (TextView) findViewById(R.id.friendInfo);

        friendNicknameText = (TextView) findViewById(R.id.friendNicknameText);

        messageListView = (ListView) findViewById(R.id.msgListView);
        messageEditText = (EditText) findViewById(R.id.msgEditText);
        menuBtn = (ImageButton) findViewById(R.id.msgMenuBtn);
        findBtn = (ImageButton) findViewById(R.id.msgFindBtn);
        chatBtn = (ImageButton) findViewById(R.id.msgChatBtn);
        speakerBtn = (Button) findViewById(R.id.msgSpeakerBtn);
        locationBtn = (Button) findViewById(R.id.msgLocationBtn);
        imageBtn = (Button) findViewById(R.id.msgImageBtn);
        sendBtn = (Button) findViewById(R.id.msgSendBtn);

        // 우측 메뉴 생성
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // TODO 프로필 값 채우기
//        pfAvatar =
        pfNickname.setText("soyoungpark");
        pfID.setText("3457soso");
        pfInfo.setText("이것은 자기소개인 것입니다. 길게 써야 그럴 듯 하니까 길게 쓰도록 하겠습니다. 할 말이 떨어져 가니까 그만 써야지!");

        // TODO 현재 DM 중인 친구의 프로필 값 채우기
//        friendAvatar =
        friendNickname.setText("fakerzzang");
        friendNicknameText.setText("fakerzzang"); // 바로 위에꺼랑 같은 값으로 세팅해주세요!
        friendInfo.setText("이번 롤드컵에 페이커가 출전하지 못해서 굉장히 유감입니다. 사실 롤을 본 지는 오래 돼서 지금 봐도 뭐가 뭔지는 모릅니다.");


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

            case R.id.msgChatBtn: // 채팅 버튼 클릭
                Intent intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
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
