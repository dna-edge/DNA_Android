package com.konkuk.dna;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.konkuk.dna.R;

public class LoginActivity extends BaseActivity {

    private Button button;
    private EditText UserMail;
    private EditText UserPW;

    private TextView MissPW;
    private TextView SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().hide();
        button = (Button) findViewById(R.id.button_login);
        UserMail = (EditText) findViewById(R.id.editText_mail);
        UserPW = (EditText) findViewById(R.id.editText_pw);
//        MissPW = (TextView)findViewById(R.id.miss_pw);
        SignUp = (TextView)findViewById(R.id.sign_up);

        //링크에 밑줄처리하기
//        String udata="비밀번호가 기억나지 않는다면?";
//        SpannableString content = new SpannableString(udata);
//        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
//        MissPW.setText(content);

        String udata="회원가입하기";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        SignUp.setText(content);

        //로그인 부분
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 받아온 메일과 비밀번호로 Auth받아오기
                if(UserMail.getText().toString().equals("aa")){
                    // 받아온다면?
                    startActivity(new Intent(LoginActivity.this, ChatActivity.class));
                    LoginActivity.this.finish();
                }else{
                    //못받아오면?
                    showDenyDialog();
                }
            }
        });

        //비밀번호가 기억나지 않는다면?
//        MissPW.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, ActivityMissPW.class));
//            }
//        });

        //회원가입하기
        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               startActivity(new Intent(LoginActivity.this, SignupAgreeActivity.class));
            }
        });
    }

    //로그인 실패 대화상자 출력
    public void showDenyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(R.mipmap.dna_round);
        builder.setTitle("DNA");
        builder.setMessage("없는 e-mail입니다.");
        builder.setPositiveButton("확인",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

