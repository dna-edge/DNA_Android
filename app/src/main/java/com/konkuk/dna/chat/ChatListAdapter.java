package com.konkuk.dna.chat;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konkuk.dna.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatListAdapter extends ArrayAdapter<ChatMessage> {
    Context context;
    ArrayList<ChatMessage> messages;
    String currentUserId;
    Boolean isMyMessage = false;

    private static Typeface NSEB;
    private static Typeface NSB;
    private static Typeface NSR;

    private static Typeface NSREB;
    private static Typeface NSRB;
    private static Typeface NSRR;

    private static Typeface fontAwesomeR;
    private static Typeface fontAwesomeS;

    public ChatListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChatMessage> objects) {
        super(context, resource, objects);

        this.context = context;
        this.messages = objects;

        init();
    }

    public void init() {
        if(NSEB == null) {
            NSEB = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareEB.ttf");
        }
        if(NSB == null) {
            NSB = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareB.ttf");
        }
        if(NSR == null) {
            NSR = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareR.ttf");
        }
        if(NSREB == null) {
            NSREB = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundEB.ttf");
        }
        if(NSRB == null) {
            NSRB = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundB.ttf");
        }
        if(NSRR == null) {
            NSRR = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundR.ttf");
        }
        if(fontAwesomeR == null) {
            fontAwesomeR = Typeface.createFromAsset(context.getAssets(), "fonts/fa-regular-400.ttf");
        }
        if(fontAwesomeS == null) {
            fontAwesomeS = Typeface.createFromAsset(context.getAssets(), "fonts/fa-solid-900.ttf");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        ChatMessage message = messages.get(position);

        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // TODO 해당 메시지의 작성자가 현재 접속한 유저인지를 판별해 left, right를 정해줘야 합니다.
            // TODO 상대방이 작성했으면서 최초 메시지일 경우에는 프로필 이미지와 닉네임을 보여줘야 합니다.

            if (position == 0 || position == 6) {
                v = layoutInflater.inflate(R.layout.chat_item_with_avatar, null);

                TextView messageNickname = (TextView) v.findViewById(R.id.msgNickname);
                messageNickname.setText(message.getUserName());
                messageNickname.setTypeface(NSB);

                ImageView messageAvatar = (ImageView) v.findViewById(R.id.msgAvatar);

                if (message.getAvatar() != null) {
                    Picasso.get().load(message.getAvatar()).into(messageAvatar);
                }



        LinearLayout msgContainer = (LinearLayout) v.findViewById(R.id.msgContainer);
            } else if (position < 3 || position > 6) {
                v = layoutInflater.inflate(R.layout.chat_item_left, null);
            } else {
                v = layoutInflater.inflate(R.layout.chat_item_right, null);
            }
        }

        LinearLayout messageLikeWrapper = (LinearLayout) v.findViewById(R.id.likeWrapper);
        TextView messageText = (TextView) v.findViewById(R.id.msgText);
        TextView messageLike = (TextView) v.findViewById(R.id.likeCount);
        TextView messageTime = (TextView) v.findViewById(R.id.dateText);
        TextView likeStar = (TextView) v.findViewById(R.id.likeStar);

        messageText.setText(message.getMessage());
        messageLike.setText(message.getLike());
        messageTime.setText(message.getTime());

        messageTime.setTypeface(NSB);
        likeStar.setTypeface(fontAwesomeS);

        // TODO 내가 좋아요를 클릭했을 경우와 클릭하지 않았을 경우 다른 뷰를 보여줘야 합니다.
        if (position % 2 == 0) {
            messageLike.setTextColor(context.getResources().getColor(R.color.yellow));
            likeStar.setTextColor(context.getResources().getColor(R.color.yellow));
            messageLikeWrapper.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_like_clicked));

        } else {
            messageLike.setTextColor(context.getResources().getColor(R.color.grayDark));
            likeStar.setTextColor(context.getResources().getColor(R.color.grayLighter));
            messageLikeWrapper.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_like_default));
        }
        return v;
    }
}
