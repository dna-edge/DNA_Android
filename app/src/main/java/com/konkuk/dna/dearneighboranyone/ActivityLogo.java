package com.konkuk.dna.dearneighboranyone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.konkuk.dna.dearneighboranyone.ActivityLogo.showProgressDialog;

public class ActivityLogo extends AppCompatActivity {

    static ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        prgDialog = new ProgressDialog(this);
        getSupportActionBar().hide();

        AuthAsyncTask aat = new AuthAsyncTask();
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
    @Override
    protected void onPreExecute() {
        showProgressDialog();
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
