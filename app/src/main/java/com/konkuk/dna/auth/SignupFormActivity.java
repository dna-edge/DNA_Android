package com.konkuk.dna.auth;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.Helpers;
import com.konkuk.dna.R;

public class SignupFormActivity extends BaseActivity {
    private EditText IDEdit, emailEdit, PWEdit, PWcheckEdit, nicknameEdit, infoEdit;
    private ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        init();
    }

    public void init() {
        IDEdit = (EditText) findViewById(R.id.IDEdit);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        PWEdit = (EditText) findViewById(R.id.PWEdit);
        PWcheckEdit = (EditText) findViewById(R.id.PWcheckEdit);
        nicknameEdit = (EditText) findViewById(R.id.nicknameEdit);
        infoEdit = (EditText) findViewById(R.id.infoEdit);
        avatarImage = (ImageView) findViewById(R.id.avatarImage);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IDcheckBtn: // 아이디 중복 확인 버튼
                break;

            case R.id.emailcheckBtn: // 이메일 중복 확인 버튼
                break;

            case R.id.selectImgBtn: // 이미지 선택 버튼
                break;

            case R.id.userSaveBtn: // 저장하기 버튼
                break;

        }
    }
}
