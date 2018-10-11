package com.konkuk.dna.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.konkuk.dna.BaseActivity;
import com.konkuk.dna.R;
import com.konkuk.dna.httpjson.HttpReqRes;
import com.konkuk.dna.httpjson.JsonToObj;

import static com.konkuk.dna.auth.LoginActivity.loginDialog;
import static com.konkuk.dna.auth.LoginActivity.showLoginDialog;

public class LoginActivity extends BaseActivity {

    private Context context;

    static ProgressDialog loginDialog;

    private Button button;
    private EditText UserID;
    private EditText UserPW;

    private TextView MissPW;
    private TextView SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().hide();
        button = (Button) findViewById(R.id.button_login);
        UserID = (EditText) findViewById(R.id.editText_id);
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
//                if(UserID.getText().toString().equals("")){
//                    // 빈칸이라면?
//                    Toast.makeText(view.getContext(), "정보를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
//                }else{
                    // 로그인 시도하기
                    loginDialog = new ProgressDialog(view.getContext());

                    LoginAsyncTask lat = new LoginAsyncTask(view.getContext());
                    lat.execute(UserID.getText().toString(), UserPW.getText().toString());

                    //showDenyDialog();
//                }
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

    public static void showLoginDialog(){
        loginDialog.setIcon(R.mipmap.dna_round);
        loginDialog.setTitle("DNA");
        loginDialog.setProgressStyle(0);
        loginDialog.setMessage("로그인 중입니다.");
        loginDialog.show();
    }
}


class LoginAsyncTask extends AsyncTask<String, Integer, String> {
    private Context context;

    public LoginAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        showLoginDialog();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        //로그인 되어 있는지 확인
        //결과를 리턴

        String responseResult = "no";

        HttpReqRes httpreq = new HttpReqRes();
        responseResult = httpreq.requestHttpPostLogin("https://dna.soyoungpark.me:9011/api/users/login", strings[0], strings[1]);

        JsonToObj jto = new JsonToObj();
        jto.LoginJsonToObj(responseResult);

        return responseResult;
    }

    @Override
    protected void onPostExecute(String result) {
        //되어있으면 ActivityChat
        //안 되어있으면 ActivityLogin

        loginDialog.dismiss();

        Toast.makeText(context, "result = "+result, Toast.LENGTH_SHORT);

//        Intent intent = new Intent(context, MainActivity.class);
//        context.startActivity(intent);
//        ((Activity)context).finish();
    }
}

