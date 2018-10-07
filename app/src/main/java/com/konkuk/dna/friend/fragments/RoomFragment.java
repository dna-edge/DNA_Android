package com.konkuk.dna.friend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.konkuk.dna.R;
import com.konkuk.dna.friend.message.DMActivity;
import com.konkuk.dna.friend.message.DMMessage;
import com.konkuk.dna.friend.message.DMRoom;
import com.konkuk.dna.friend.message.DMRoomListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {
    private ListView roomList;
    private ArrayList<DMRoom> rooms;
    private DMRoomListAdapter dmRoomListAdapter;

    /* 메시지의 타입을 구분하기 위한 변수들입니다 */
    private final String TYPE_MESSAGE = "Message";     // 일반 메시지 전송
    private final String TYPE_LOCATION = "Location";    // 현재 위치 전송
    private final String TYPE_IMAGE = "Image";       // 이미지 전송

    public RoomFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_room, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        roomList = (ListView) getView().findViewById(R.id.roomList);
        rooms = new ArrayList<DMRoom>();

        // TODO 서버에서 room 리스트를 받아와서 초기화시켜줘야 합니다.
        rooms.add(new DMRoom(0, 1, "3457soso", "https://pbs.twimg.com/media/DbYfg2IWkAENdiS.jpg", "마지막 메시지", TYPE_MESSAGE, "2018-01-24"));
        rooms.add(new DMRoom(1, 2, "test", null, "마지막 메시지2", TYPE_MESSAGE, "2018-01-23"));
        rooms.add(new DMRoom(2, 3, "avatar", null, "마지막 메시지2", TYPE_MESSAGE, "2018-01-22"));

        dmRoomListAdapter = new DMRoomListAdapter(getActivity(), R.layout.chat_item_room, rooms);
        roomList.setAdapter(dmRoomListAdapter);

        roomList.setClickable(true);
        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                DMRoom room = (DMRoom) roomList.getItemAtPosition(position);
                Intent intent = new Intent (getActivity(), DMActivity.class);
                intent.putExtra("roomIdx", room.getIdx());
                intent.putExtra("roomUpdated", room.getUpdateDate());
                startActivity(intent);
            }
        });
    }

}
