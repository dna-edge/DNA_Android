package com.konkuk.dna;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.konkuk.dna.auth.LoginActivity;

import static com.konkuk.dna.SplashActivity.prgDialog;
import static com.konkuk.dna.SplashActivity.showProgressDialog;

public class SplashActivity extends AppCompatActivity {
    static ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        prgDialog = new ProgressDialog(this);
        //getSupportActionBar().hide();

        AuthAsyncTask aat = new AuthAsyncTask(this);
        aat.execute();

    }

    public static void showProgressDialog(){
        prgDialog.setIcon(R.mipmap.dna_round);
        prgDialog.setTitle("DNA");
        prgDialog.setProgressStyle(0);
        prgDialog.setMessage("로그인 정보를 확인 중입니다.");
        prgDialog.setCanceledOnTouchOutside(false);
        prgDialog.setCancelable(false);
        prgDialog.show();
    }
}

class AuthAsyncTask extends AsyncTask<Integer, Integer, Integer> {
    private Context context;

    public AuthAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        showProgressDialog();
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        //로그인 되어 있는지 확인
        //결과를 리턴

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        //되어있으면 ActivityChat
        //안 되어있으면 ActivityLogin

        prgDialog.dismiss();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
