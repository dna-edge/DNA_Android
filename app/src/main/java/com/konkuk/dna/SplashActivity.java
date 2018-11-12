package com.konkuk.dna;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.konkuk.dna.auth.LoginActivity;
import com.konkuk.dna.utils.dbmanage.Dbhelper;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.konkuk.dna.SplashActivity.prgDialog;
import static com.konkuk.dna.SplashActivity.showProgressDialog;

public class SplashActivity extends AppCompatActivity {
    static ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }
        }
    }

    public void openActivity(){
        prgDialog = new ProgressDialog(this);
        //getSupportActionBar().hide();

        AuthAsyncTask aat = new AuthAsyncTask(this);
        aat.execute();
    }

    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }
    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("권한설정 확인");
                alertBuilder.setMessage("파일 입출력 권한");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
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

class AuthAsyncTask extends AsyncTask<Integer, Boolean, Boolean> {
    private Context context;
    private Dbhelper dbhelper;

    public AuthAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        showProgressDialog();
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        //로그인 되어 있는지 확인
        //결과를 리턴

        boolean isSuccess;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        dbhelper = new Dbhelper(context);
//        /*
//        * DB에 남은 토큰을 검색해서 expired 확인, 유효하면 바로 chatactivity진입
//        * */
//
//        HttpReqRes httpreq = new HttpReqRes();
//        String responseResult = httpreq.requestHttpGETAuth("https://dna.soyoungpark.me:9011/api/auth/refresh", dbhelper.getRefreshToken());
//
//        JsonToObj jto = new JsonToObj();
//        HashMap<String, String> map = jto.TokenJsonToObj(responseResult);
//
//
//        if(map.get("issuccess").equals("true")){
//            /*
//             * 성공했으면 DB에 저장
//             * */
//            dbhelper = new Dbhelper(context);
//            dbhelper.refreshTokenDB(map);
//            isSuccess = true;
//        }else{
//            /*
//             * 실패했으면 값만 반환
//             * */
//            isSuccess = false;
//        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean isToken) {
        //되어있으면 ActivityChat
        //안 되어있으면 ActivityLogin

        prgDialog.dismiss();


        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();

//        if(isToken){
//            Intent intent = new Intent(context, MainActivity.class);
//            context.startActivity(intent);
//            ((Activity)context).finish();
//        }else{
//            Intent intent = new Intent(context, LoginActivity.class);
//            context.startActivity(intent);
//            ((Activity)context).finish();
//        }
    }
}
