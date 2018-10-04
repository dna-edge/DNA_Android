package com.konkuk.dna;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.konkuk.dna.chat.ChatUser;
import com.konkuk.dna.chat.ChatUserAdapter;

import java.util.ArrayList;

import static android.view.View.GONE;

public class Helpers {
    public static View initDrawer(Context context, View v, int type) {
        ImageView pfAvatar = (ImageView) v.findViewById(R.id.msgPfAvatar);
        TextView pfNickname = (TextView) v.findViewById(R.id.msgPfNickname);
        TextView pfID = (TextView) v.findViewById(R.id.msgPfID);
        LinearLayout drawerForUserList = (LinearLayout) v.findViewById(R.id.drawerForUserList);
        LinearLayout drawerForFriend = (LinearLayout) v.findViewById(R.id.drawerForFriend);

        //        pfAvatar =
        pfNickname.setText("soyoungpark");
        pfID.setText("3457soso");

        if (type == 0) {
            drawerForFriend.setVisibility(GONE);

            // TODO chatUsers 배열에 실제 접속중인 유저 리스트 추가해야 합니다.
            ListView ccuListView = (ListView) v.findViewById(R.id.ccuList);
            ArrayList<ChatUser> chatUsers = new ArrayList<ChatUser>();
            chatUsers.add(new ChatUser("3457soso", null, true));
            chatUsers.add(new ChatUser("test", null, true));
            chatUsers.add(new ChatUser("test2", null, false));
            ChatUserAdapter chatUserAdapter = new ChatUserAdapter(context, R.layout.chat_item_ccu, chatUsers);
            ccuListView.setAdapter(chatUserAdapter);
        } else {
            drawerForUserList.setVisibility(GONE);

            // TODO 해당 친구의 프로필을 입력해줘야 합니다.
            ImageView friendAvatar = (ImageView) v.findViewById(R.id.friendAvatar);
            TextView friendNickname = (TextView) v.findViewById(R.id.friendNickname);
            TextView friendNicknameText = (TextView) v.findViewById(R.id.friendNicknameText);
            TextView friendInfo = (TextView) v.findViewById(R.id.friendInfo);

            friendNickname.setText("fakerzzang");
            friendNicknameText.setText("fakerzzang"); // 바로 위에꺼랑 같은 값으로 세팅해주세요!
            friendInfo.setText("이번 롤드컵에 페이커가 출전하지 못해서 굉장히 유감입니다. 사실 롤을 본 지는 오래 돼서 지금 봐도 뭐가 뭔지는 모릅니다.");
        }

        return v;
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
