package com.konkuk.dna.friend.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.konkuk.dna.utils.EventListener;
import com.konkuk.dna.utils.ServerURL;
import com.konkuk.dna.utils.dbmanage.Dbhelper;
import com.konkuk.dna.utils.helpers.AnimHelpers;
import com.konkuk.dna.utils.helpers.BaseFragment;
import com.konkuk.dna.friend.manage.FriendDetailFragment;
import com.konkuk.dna.friend.manage.OnFriendListAdapter;
import com.konkuk.dna.R;
import com.konkuk.dna.friend.manage.Friend;
import com.konkuk.dna.friend.manage.FriendListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.konkuk.dna.utils.HttpReqRes.requestHttpGETUserInfo;
import static com.konkuk.dna.utils.JsonToObj.SearchUserJsonToObj;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment implements View.OnClickListener {
    private EditText friendSearchEdit;
    private ImageButton friendSearchBtn;
    private OnFriendListAdapter onFriendListAdapter;
    private FriendListAdapter allFriendListAdapter;

    private RecyclerView onFriendList;
    private ListView allFriendList;
    private ArrayList<Friend> onFriends, allFriends;

    private Context context = getContext();
    private Dbhelper dbhelper;

    private static final int SOCKET_DIRECT = 7;

    public FriendFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        init();
    }

    public void init() {
        dbhelper = new Dbhelper(getContext());
        friendSearchEdit = (EditText) getView().findViewById(R.id.friendSearchEdit);
        friendSearchBtn = (ImageButton) getView().findViewById(R.id.friendSearchBtn);
        onFriendList = (RecyclerView) getView().findViewById(R.id.onFriendList);
        allFriendList = (ListView) getView().findViewById(R.id.allFriendList);
        onFriends = new ArrayList<Friend>();
        allFriends = new ArrayList<Friend>();

        if (friendSearchBtn != null) friendSearchBtn.setOnClickListener(this);

//        onFriends.add(new Friend("3457soso", "socoing", "http://slingshotesports.com/wp-content/uploads/2017/07/34620595595_b4c90a2e22_b.jpg", "자기소개", true));
//        onFriends.add(new Friend("3457soso", "socoing", null, "ㅋㅋ", true));
//        onFriends.add(new Friend("3457soso", "socoing", null, "", true));
            onFriends.add(new Friend("", "", null, "", false));

//        onFriends.add(new Friend("3457soso", "socoing", null, "", true));
//        onFriends.add(new Friend("3457soso", "socoing", null, "", true));
//        onFriends.add(new Friend("3457soso", "socoing", null, "", true));
//        onFriends.add(new Friend("3457soso", "socoing", null, "", true));

        allFriends.add(new Friend("3457soso", "socoing", "http://slingshotesports.com/wp-content/uploads/2017/07/34620595595_b4c90a2e22_b.jpg", "상태 메시지", true));
        allFriends.add(new Friend("3457soso", "socoing", null, "", false));
        allFriends.add(new Friend("3457soso", "socoing", null, "", false));
        allFriends.add(new Friend("3457soso", "socoing", null, "ㅋㅋㅎㅁ", true));
        allFriends.add(new Friend("3457soso", "socoing", null, "", false));
        allFriends.add(new Friend("3457soso", "socoing", null, "", false));

        /* 접속 중인 친구 */
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        onFriendList.setLayoutManager(layoutManager);
        onFriendListAdapter = new OnFriendListAdapter(getContext(), onFriends);
        onFriendList.setAdapter(onFriendListAdapter);

        /* 모든 친구 */
        allFriendListAdapter = new FriendListAdapter(getContext(), 0, allFriends);
        allFriendList.setAdapter(allFriendListAdapter);
        AnimHelpers.setListViewHeight(allFriendList);
        allFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FriendDetailFragment friendDetailFragment = new FriendDetailFragment();

                friendDetailFragment.setData(allFriends.get(i));
                friendDetailFragment.show(fragmentManager, "friendDetailFragment");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.friendSearchBtn: // 검색 버튼 클릭
                Log.d("test", "검색 버튼 클릭됨");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventListener(EventListener event) {

        switch (event.message){
            case SOCKET_DIRECT:
                Log.e("Socket ON", "direct(friends list)");
                FriendListAsyncTask flas = new FriendListAsyncTask(getContext(), onFriendListAdapter, onFriendList);
                flas.execute(event.args, dbhelper.getAccessToken());

                break;
            default:
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}

class FriendListAsyncTask extends AsyncTask<String, Integer, ArrayList<Friend>> {
    private Context context;
    private OnFriendListAdapter onFriendListAdapter;
    private RecyclerView onFriendList;

    public FriendListAsyncTask(Context context, OnFriendListAdapter onFriendListAdapter, RecyclerView onFriendList) {
        this.context = context;
        this.onFriendListAdapter = onFriendListAdapter;
        this.onFriendList = onFriendList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Friend> doInBackground(String... strings) {
        //Dbhelper dbhelper = new Dbhelper(context);
        ArrayList<Friend> friends = new ArrayList<>();
        int idx=-1;
        JsonParser jp = new JsonParser();
        JsonArray ja = (JsonArray) jp.parse(strings[0]);

        for(int i=0; i<ja.size(); i++){
            JsonObject jo = (JsonObject) ja.get(i);
            idx = jo.get("idx").getAsInt();
            String res = requestHttpGETUserInfo(ServerURL.DNA_SERVER+ServerURL.PORT_USER_API+"/user/"+idx, strings[1]);
            Log.e("after http", res);
            friends.add(SearchUserJsonToObj(res));
        }
        return friends;
    }

    @Override
    protected void onPostExecute(ArrayList<Friend> fs) {
        super.onPostExecute(fs);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        onFriendList.setLayoutManager(layoutManager);
        onFriendListAdapter = new OnFriendListAdapter(context, fs);
        onFriendList.setAdapter(onFriendListAdapter);



//        onFriends.add(new Friend("3457soso", "socoing", null, "", true));
//        allFriends.add(new Friend("3457soso", "socoing",
//              "http://slingshotesports.com/wp-content/uploads/2017/07/34620595595_b4c90a2e22_b.jpg", "상태 메시지", true));

    }
}