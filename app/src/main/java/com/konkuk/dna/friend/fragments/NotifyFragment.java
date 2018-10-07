package com.konkuk.dna.friend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.konkuk.dna.Helpers;
import com.konkuk.dna.R;
import com.konkuk.dna.friend.manage.Request;
import com.konkuk.dna.friend.manage.RequestListAdapter;
import com.konkuk.dna.user.PostListAdapter;
import com.konkuk.dna.user.UserFormActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragment extends Fragment {
    private TextView receivedAngle, acceptedAngle, sendedAngle;
    private ListView receivedList, acceptedList, sendedList;
    private RequestListAdapter receivedListAdapter, acceptedListAdapter, sendedListAdapter;
    private ArrayList<Request> received;
    private ArrayList<Request> accepted;
    private ArrayList<Request> sended;

    private int type;              // 보여줄 요청의 타입을 지정해 타입에 해당하는 뷰를 보여줍니다.
    private int TYPE_RECEIVED = 1; // 받은 요청
    private int TYPE_ACCEPTED = 2; // 상대방이 수락해 친구 관계가 성립된 경우
    private int TYPE_SENEDED = 3;  // 보냈는데 아직 상대방이 수락/거절 하지 않은 요청

    private boolean receivedListIsOpen = true, acceptedListIsOpen = false, sendedListIsOpen = false;

    public NotifyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        receivedAngle = (TextView) getView().findViewById(R.id.receivedAngle);
        acceptedAngle = (TextView) getView().findViewById(R.id.acceptedAngle);
        sendedAngle = (TextView) getView().findViewById(R.id.sendedAngle);
        receivedList = (ListView) getView().findViewById(R.id.receivedList);
        acceptedList = (ListView) getView().findViewById(R.id.acceptedList);
        sendedList = (ListView) getView().findViewById(R.id.sendedList);

        received = new ArrayList<Request>();
        accepted = new ArrayList<Request>();
        sended = new ArrayList<Request>();

        // TODO 승인된 친구 요청과 받은 친구 요청 리스트를 채워줘야 합니다.
        // TODO 어댑터 마지막 인자에 해당 요청의 타입을 명시해줘야 합니다. 타입은 위에 멤버 변수에 있습니다.
        received.add(new Request(0, "test", null, "2011-12-12"));
        received.add(new Request(0, "test", null, "2011-12-12"));
        received.add(new Request(0, "test", null, "2011-12-12"));


        /* 받은 친구 요청 */
        receivedListAdapter = new RequestListAdapter(getActivity(), R.layout.post_list_item, received, TYPE_RECEIVED);
        receivedList.setAdapter(receivedListAdapter);

        /* 수락된 친구 요청 */
        receivedListAdapter = new RequestListAdapter(getActivity(), R.layout.post_list_item, received, TYPE_RECEIVED);
        acceptedList.setAdapter(receivedListAdapter);

        /* 보낸 친구 요청 */
        receivedListAdapter = new RequestListAdapter(getActivity(), R.layout.post_list_item, received, TYPE_RECEIVED);
        sendedList.setAdapter(receivedListAdapter);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.receivedBtn: // 받은 친구 요청 버튼 클릭
                if (receivedListIsOpen) {
                    receivedAngle.animate().rotation(180).start();
                    Helpers.animateListHeight(getActivity(), receivedList, received.size(), 0);
                } else {
                    receivedAngle.animate().rotation(0).start();
                    Helpers.animateListHeight(getActivity(), receivedList, 0, received.size());
                }
                receivedListIsOpen = !receivedListIsOpen;
                break;

            case R.id.acceptBtn: // 수락된 친구 요청 버튼 클릭
                if (acceptedListIsOpen) {
                    acceptedAngle.animate().rotation(180).start();
                    Helpers.animateListHeight(getActivity(), acceptedList, accepted.size(), 0);
                } else {
                    acceptedAngle.animate().rotation(0).start();
                    Helpers.animateListHeight(getActivity(), acceptedList, 0, accepted.size());
                }
                acceptedListIsOpen = !acceptedListIsOpen;
                break;

            case R.id.sendedBtn: // 보낸 친구 요청 버튼 클릭
                if (sendedListIsOpen) {
                    sendedAngle.animate().rotation(180).start();
                    Helpers.animateListHeight(getActivity(), sendedList, sended.size(), 0);
                } else {
                    sendedAngle.animate().rotation(0).start();
                    Helpers.animateListHeight(getActivity(), sendedList, 0, sended.size());
                }
            sendedListIsOpen = !sendedListIsOpen;
            break;
        }
    }

}
