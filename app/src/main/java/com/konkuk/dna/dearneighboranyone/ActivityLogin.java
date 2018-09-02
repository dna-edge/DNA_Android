package com.konkuk.dna.dearneighboranyone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {

    private Button button;
    private EditText UserMail;
    private EditText UserPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        button = (Button) findViewById(R.id.button_login);
        UserMail = (EditText) findViewById(R.id.editText_mail);
        UserPW = (EditText) findViewById(R.id.editText_pw);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UserMail.getText();
                //UserPW.getText();

                // 받아온 메일과 비밀번호로 Auth받아오기
                if(UserMail.getText().equals("aa")){
                    // 받아온다면?
                    startActivity(new Intent(ActivityLogin.this, ActivityChat.class));
                }else{
                    //못받아오면?
                    showDenyDialog();
                }

            }
        });
    }
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

