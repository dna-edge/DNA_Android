package com.konkuk.dna.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.konkuk.dna.utils.helpers.BaseActivity;
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
            case R.id.backBtn: // 뒤로가기 버튼
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("되돌아 가시겠습니까? 입력하시던 내용은 저장되지 않습니다.").setCancelable(
                        false).setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SignupFormActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
                break;

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
