package com.konkuk.dna;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.konkuk.dna.chat.ChatUser;
import com.konkuk.dna.chat.ChatUserAdapter;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private DrawerLayout menuDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        menuDrawer = findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 0);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgMenuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    menuDrawer.openDrawer(Gravity.RIGHT);
                }

                break;

        }
    }
}