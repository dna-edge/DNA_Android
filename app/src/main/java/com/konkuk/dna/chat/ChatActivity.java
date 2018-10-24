package com.konkuk.dna.chat;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonObject;
import com.konkuk.dna.Utils.SocketConnection;
import com.konkuk.dna.dbmanage.Dbhelper;
import com.konkuk.dna.helpers.AnimHelpers;
import com.konkuk.dna.helpers.BaseActivity;
import com.konkuk.dna.helpers.InitHelpers;
import com.konkuk.dna.MainActivity;
import com.konkuk.dna.R;
import com.konkuk.dna.Utils.HttpReqRes;
import com.konkuk.dna.map.MapFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.konkuk.dna.Utils.JsonToObj.ChatAllJsonToObj;
import static com.konkuk.dna.Utils.ObjToJson.SendMsgObjToJson;
import static com.konkuk.dna.Utils.ObjToJson.StoreObjToJson;

public class ChatActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private MapFragment mapFragment;
    private View mapFragmentView;
    private RelativeLayout barLayout;

    private ListView msgListView;
    private EditText msgEditText;
    private Button msgSpeakerBtn, msgLocationBtn, msgImageBtn;
    private LinearLayout mapSizeBtn, bestChatBtn, bestChatMargin;
    private RelativeLayout bestChatWrapper;
    private TextView mapSizeAngle, bestChatAngle, bestChatContent, bestChatNickname, bestChatDate;
    private ImageView bestChatAvatar;

    private ChatListAdapter chatListAdapter;
    private ArrayList<ChatMessage> chatMessages;

    private SimpleDateFormat timeFormat;

    private ValueAnimator slideAnimator;
    private AnimatorSet set;
    private int height, radius;
    private double longitude, latitude;
    private boolean mapIsOpen = true, bestChatIsOpen = true;

    /* 메시지의 타입을 구분하기 위한 변수들입니다 */
    private final String TYPE_MESSAGE = "Message";     // 일반 메시지 전송
    private final String TYPE_LOUDSPEAKER = "LoudSpeaker"; // 확성기 전송
    private final String TYPE_LOCATION = "Location";    // 현재 위치 전송
    private final String TYPE_IMAGE = "Image";       // 이미지 전송
    private String messageType = TYPE_MESSAGE;

    private Dbhelper dbhelper;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
        socketInit();

        //Log.e("Socket", "Connected!!!!");

        JsonObject info = StoreObjToJson(dbhelper, gpsTracker.getLongitude(), gpsTracker.getLatitude());
        Log.e("!!!store=", info.toString());
        mSocket.emit("store", info);

    }

    public void init() {

        menuDrawer = findViewById(R.id.drawer_layout);
        InitHelpers.initDrawer(this, menuDrawer, 0);

        mapFragmentView = (View) findViewById(R.id.mapFragment);
        barLayout = (RelativeLayout) findViewById(R.id.barLayout);
        msgListView = (ListView) findViewById(R.id.msgListView);
        msgEditText = (EditText) findViewById(R.id.msgEditText);
        msgSpeakerBtn = (Button) findViewById(R.id.msgSpeakerBtn);
        msgLocationBtn = (Button) findViewById(R.id.msgLocationBtn);
        msgImageBtn = (Button) findViewById(R.id.msgImageBtn);
        mapSizeBtn = (LinearLayout) findViewById(R.id.mapSizeBtn);
        mapSizeAngle = (TextView) findViewById(R.id.mapSizeAngle);
        bestChatBtn = (LinearLayout) findViewById(R.id.bestChatBtn);
        bestChatAngle = (TextView) findViewById(R.id.bestChatAngle);
        bestChatWrapper = (RelativeLayout) findViewById(R.id.bestChatWrapper);
        bestChatMargin = (LinearLayout) findViewById(R.id.bestChatMargin);

        /*
        * GPS 받아오기, 반경 설정하기
        * */
        longitude = gpsTracker.getLongitude();
        latitude = gpsTracker.getLatitude();

        dbhelper = new Dbhelper(this);
        radius = dbhelper.getMyRadius();

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);

        bestChatContent = (TextView) findViewById(R.id.bestChatContent);
        bestChatNickname = (TextView) findViewById(R.id.bestChatNickname);
        bestChatDate = (TextView) findViewById(R.id.bestChatDate);
        bestChatAvatar = (ImageView) findViewById(R.id.bestChatAvatar);

        //채팅 불러오기
        ChatSetAsyncTask csat = new ChatSetAsyncTask(this, radius, msgListView, bestChatAvatar, bestChatContent, bestChatNickname, bestChatDate);
        csat.execute(longitude, latitude);

        // TODO 베스트챗 내용 세팅해줘야 합니다!
//        Picasso.get()
//                .load("http://slingshotesports.com/wp-content/uploads/2017/07/34620595595_b4c90a2e22_b.jpg")
//                .into(bestChatAvatar);
//        bestChatContent.setText("좋아요를 많이 받은 베스트챗의 내용이로다...");
//        bestChatNickname.setText("3457soso");
//        bestChatDate.setText("오후 01:30");

        // TODO chatMessages 배열에 실제 메시지 추가해야 합니다.
//        chatMessages = new ArrayList<ChatMessage>();
//        chatMessages.add(new ChatMessage(0, "3457soso", null, "http://file3.instiz.net/data/cached_img/upload/2018/06/22/14/2439cadf98e7bebdabd174ed41ca0849.jpg", "오후 12:34", "0", TYPE_IMAGE, 127.07934279999995, 37.5407625));
//        chatMessages.add(new ChatMessage(1, "3457soso", null, "내용내용", "오후 12:34", "2", TYPE_LOUDSPEAKER, 127.0793427999999, 37.540762));
//        chatMessages.add(new ChatMessage(2, "3457soso", null, "내용내용내용내용내용", "오후 12:34", "1", TYPE_MESSAGE, 127.079342799995, 37.540625));
//        chatMessages.add(new ChatMessage(3, "3457soso", null, "내용내용내용내용내용", "오후 12:34", "1", TYPE_LOUDSPEAKER, 127.0734279999995, 37.5407625));
//        chatMessages.add(new ChatMessage(4, "3457soso", null, "내용내용내용", "오후 12:34", "0", TYPE_MESSAGE, 127.0794279999995, 37.507625));
//        chatMessages.add(new ChatMessage(5, "3457soso", null, "내용내용내용", "오후 12:34", "0", TYPE_MESSAGE, 127.0793427999995, 37.540625));
//        chatMessages.add(new ChatMessage(6, "3457soso", null, "{\"lat\":37.550544099999996,\"lng\":127.07221989999998}", "오후 12:34", "1", TYPE_LOCATION, 127.07934279999995, 37.540762));
//        chatMessages.add(new ChatMessage(7, "3457soso", null, "http://www.ohfun.net/contents/article/images/2016/0830/1472551795750578.jpeg", "오후 12:34", "2", TYPE_IMAGE, 127.079342799995, 37.5407625));
//        chatMessages.add(new ChatMessage(8, "3457soso", null, "내용내용", "오후 12:34", "2", TYPE_MESSAGE, 127.0793427999995, 37.540625));
//        chatMessages.add(new ChatMessage(10, "3457soso", null, "{\"lat\":37.550544099999,\"lng\":127.07221989999}", "오후 12:34", "1", TYPE_LOCATION, 127.07934279999995, 37.540762));
//
//        chatListAdapter = new ChatListAdapter(this, R.layout.chat_item_left, chatMessages);
//        msgListView.setAdapter(chatListAdapter);

        // 생성된 후 바닥으로 메시지 리스트를 내려줍니다.
        //scrollMyListViewToBottom();

        timeFormat = new SimpleDateFormat("a h:m", Locale.KOREA);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;

        slideAnimator = ValueAnimator
                .ofInt(AnimHelpers.dpToPx(this, 150), height).setDuration(400);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mapFragmentView.getLayoutParams();
                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) barLayout.getLayoutParams();
                params.height = value.intValue();
                int divider = (height - AnimHelpers.dpToPx(getApplicationContext(), 150)) / 50;
                params2.setMargins(0, -1 * AnimHelpers.dpToPx(getApplicationContext(), value - AnimHelpers.dpToPx(getApplicationContext(), 150))/divider, 0, 0);
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

    public void socketInit(){
        SocketConnection socketCon = new SocketConnection();
        mSocket = socketCon.getSocket();

        mSocket.on("ping", onPingReceived);
        mSocket.on("new_msg", onMessageReceived);

        Log.e("Start", "Connect");
        mSocket.connect();
        Log.e("After", "Connect");
    }

    private Emitter.Listener onMessageReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 전달받은 데이터는 아래와 같이 추출할 수 있습니다.
            JSONObject receivedData = (JSONObject) args[0];
            // your code...
        }
    };

    private Emitter.Listener onPingReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e("SOcket Ping", "COME!!!");
            JsonObject updateJson = StoreObjToJson(dbhelper, gpsTracker.getLongitude(), gpsTracker.getLatitude());
            mSocket.emit("update", "geo", updateJson);
        }
    };



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

            case R.id.mapSizeBtn: // 지도 크기 조정 버튼 클릭
                if (mapIsOpen) {
                    mapSizeAngle.animate().rotation(180).setDuration(400L).start();
                    AnimHelpers.animateViewHeight(this, mapFragmentView, AnimHelpers.dpToPx(this, 150), 0);
                    AnimHelpers.animateMargin(this, mapSizeBtn, "top", 200L,
                            AnimHelpers.dpToPx(this, 100), 0);
                    AnimHelpers.animateMargin(this, bestChatMargin, "right", 200L,
                            AnimHelpers.dpToPx(this,35), AnimHelpers.dpToPx(this,80));
                    AnimHelpers.animateMargin(this, bestChatBtn, "chat", 200L, 50, 0);
                } else {
                    mapSizeAngle.animate().rotation(360).setDuration(400L).start();
                    AnimHelpers.animateViewHeight(this, mapFragmentView, 0, AnimHelpers.dpToPx(this, 150));
                    AnimHelpers.animateMargin(this, mapSizeBtn, "top", 200L,
                            0, AnimHelpers.dpToPx(this, 100));
                    AnimHelpers.animateMargin(this, bestChatMargin, "right", 200L,
                            AnimHelpers.dpToPx(this,80), AnimHelpers.dpToPx(this,35));
                    AnimHelpers.animateMargin(this, bestChatBtn, "chat", 200L, 0, 50);
                }
                mapIsOpen = !mapIsOpen;
                break;
            case R.id.bestChatBtn: // 베스트챗 버튼 클릭
                if (bestChatIsOpen) {
                    bestChatAngle.setText(getResources().getString(R.string.fa_crown));
                    AnimHelpers.animateViewHeight(this, bestChatWrapper, AnimHelpers.dpToPx(this, 50), 0);
                } else {
                    bestChatAngle.setText(getResources().getString(R.string.fa_x));
                    AnimHelpers.animateViewHeight(this, bestChatWrapper, 0, AnimHelpers.dpToPx(this, 50));
                }
                bestChatIsOpen = !bestChatIsOpen;
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
                JsonObject sendMsgJson = SendMsgObjToJson(dbhelper, gpsTracker.getLongitude(), gpsTracker.getLatitude(), messageType, msgEditText.getText().toString());
                Log.e("!!!=sendMsg", sendMsgJson.toString());
                mSocket.emit("save_msg", sendMsgJson);

                msgEditText.setText("");
                msgEditText.setEnabled(true);
                messageType = TYPE_MESSAGE;
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

        int top = AnimHelpers.dpToPx(this, 5);
        if (mapIsOpen) {
            top = AnimHelpers.dpToPx(this, 105);
        }

        AnimHelpers.animateMargin(this, mapSizeBtn, "top", 200L,
                top, AnimHelpers.dpToPx(this, -40));

        // TODO 1번 누르면 ChatActivity가 destroy되지 않고 그냥 mainActivity가 보임
        // TODO 2번쨰, 3번째 눌렀을때 앱이 종료 되도록 만들기
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.initMapCenter(longitude, latitude, radius);
    }
}




/*
* 비동기 Http 연결 작업 클래스
* */
class ChatSetAsyncTask extends AsyncTask <Double, Integer, ArrayList<String>>  {
    private Context context;
    private String m_token;
    private Integer radius;
    private Dbhelper dbhelper;

    private ChatListAdapter chatListAdapter;
    private ListView msgListView;
    private TextView bestChatContent, bestChatNickname, bestChatDate;
    private ImageView bestChatAvatar;

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

    public ChatSetAsyncTask(Context context, Integer radius, ListView msgListView, ImageView bcAvatar, TextView bcContent, TextView bcNickname, TextView bcDate){
        this.context=context;
        this.radius=radius;
        this.msgListView = msgListView;
        this.bestChatAvatar = bcAvatar;
        this.bestChatContent = bcContent;
        this.bestChatDate = bcDate;
        this.bestChatNickname = bcNickname;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(Double... doubles) {

        //ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

        ArrayList<String> resultArray = new ArrayList<>();

        HttpReqRes httpreq = new HttpReqRes();
        dbhelper = new Dbhelper(context);
        m_token = dbhelper.getAccessToken();

        String repBestChat = httpreq.requestHttpPostMsgAll("https://dna.soyoungpark.me:9014/api/best", m_token, doubles[0], doubles[1], radius);
        String repMsgAll = httpreq.requestHttpPostMsgAll("https://dna.soyoungpark.me:9014/api/messages/:page", m_token, doubles[0], doubles[1], radius);

        resultArray.add(0, repBestChat);
        resultArray.add(1, repMsgAll);

        return resultArray;
    }

    @Override
    protected void onPostExecute(ArrayList<String> resultArray) {
        super.onPostExecute(resultArray);

        /*
        * 베스트챗 내용 세팅
        * */
        ArrayList<ChatMessage> bestMessages = new ArrayList<ChatMessage>();
        bestMessages = ChatAllJsonToObj(resultArray.get(0));

        if(bestMessages.size()>0) {
            Picasso.get()
                    .load(bestMessages.get(0).getAvatar())
                    .into(bestChatAvatar);
            bestChatContent.setText(bestMessages.get(0).getContents());
            bestChatNickname.setText(bestMessages.get(0).getUserName());
            bestChatDate.setText(bestMessages.get(0).getDate());
        }else{
            bestChatContent.setText("이 지역의 베스트챗이 존재하지 않아요ㅠ");
            bestChatNickname.setText("리보솜");
        }
        /*
        * 전체 채팅 내용 세팅
        * */
        ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
        chatMessages = ChatAllJsonToObj(resultArray.get(1));

        //거꾸로 받아온 리스트를 역순으로 바꿈
        Collections.reverse(chatMessages);
        chatListAdapter = new ChatListAdapter(context, R.layout.chat_item_left, chatMessages);
        msgListView.setAdapter(chatListAdapter);

        // 생성된 후 바닥으로 메시지 리스트를 내려줍니다.
        scrollMyListViewToBottom();
    }
}