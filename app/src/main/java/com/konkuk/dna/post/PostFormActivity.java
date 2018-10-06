package com.konkuk.dna.post;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.Helpers;
import com.konkuk.dna.MainActivity;
import com.konkuk.dna.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PostFormActivity extends BaseActivity {
    private DrawerLayout menuDrawer;
    private PostFormMapFragment postFormMapFragment;
    private EditText postTitleEdit, postContentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);

        init();
    }

    public void init() {
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Helpers.initDrawer(this, menuDrawer, 2);

        postTitleEdit = (EditText) findViewById(R.id.postTitleEdit);
        postContentEdit = (EditText) findViewById(R.id.postContentEdit);
        postFormMapFragment = (PostFormMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;

            case R.id.menuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    menuDrawer.openDrawer(Gravity.RIGHT);
                }
                break;

            case R.id.saveBtn: // 저장 버튼 클릭
                // 위치 정보는 아래 함수로 받아오면 됩니다.
                // JSONObject = { longitude, latitude }를 반환합니다.
                JSONObject position = postFormMapFragment.getCenterPosition();
                try {
                    Log.d("test", "lat :"  + position.get("latitude"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
