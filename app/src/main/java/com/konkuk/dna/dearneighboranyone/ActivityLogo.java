package com.konkuk.dna.dearneighboranyone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.konkuk.dna.dearneighboranyone.ActivityLogo.prgDialog;
import static com.konkuk.dna.dearneighboranyone.ActivityLogo.showProgressDialog;

public class ActivityLogo extends AppCompatActivity {

    static ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        prgDialog = new ProgressDialog(this);
        getSupportActionBar().hide();

        AuthAsyncTask aat = new AuthAsyncTask(this);
        aat.execute();

    }

    public static void showProgressDialog(){
        prgDialog.setIcon(R.mipmap.dna_round);
        prgDialog.setTitle("DNA");
        prgDialog.setMessage("로그인 정보를 확인 중입니다.");
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
            Thread.sleep(3000);
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
        Intent intent = new Intent(context, ActivityLogin.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
