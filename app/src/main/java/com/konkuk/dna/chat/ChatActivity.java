package com.konkuk.dna.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends BaseActivity {
//    private RecyclerView recyclerView;
//    protected RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;

    ListView messageListView;
    EditText messageEditText;
    ImageButton findBtn, menuBtn;
    Button speakerBtn, locationBtn, imageBtn, sendBtn;
    String userId;
    SimpleDateFormat timeFormat;
    ArrayList<ChatMessage> chatMessages;
    ChatListAdapter chatListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
    }

    public void init() {
        messageListView = (ListView) findViewById(R.id.msgListView);
        messageEditText = (EditText) findViewById(R.id.msgEditText);
        findBtn = (ImageButton) findViewById(R.id.chatFindBtn);
        menuBtn = (ImageButton) findViewById(R.id.chatMenuBtn);
        speakerBtn = (Button) findViewById(R.id.msgSpeakerBtn);
        locationBtn = (Button) findViewById(R.id.msgLocationBtn);
        imageBtn = (Button) findViewById(R.id.msgImageBtn);
        sendBtn = (Button) findViewById(R.id.msgSendBtn);

        userId = getIntent().getStringExtra("userId");

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

        chatListAdapter = new ChatListAdapter(this,
                R.layout.chat_item_left, chatMessages, userId);
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

            case R.id.chatFindBtn: // 검색 버튼 클릭
                break;

            case R.id.chatMenuBtn: // 메뉴 버튼 클릭
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
        }
    }
}
