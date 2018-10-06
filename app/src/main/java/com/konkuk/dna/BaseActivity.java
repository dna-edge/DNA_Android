package com.konkuk.dna;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {
    private static Typeface NSEB;
    private static Typeface NSB;
    private static Typeface NSR;

    private static Typeface NSREB;
    private static Typeface NSRB;
    private static Typeface NSRR;

    private static Typeface fontAwesomeR;
    private static Typeface fontAwesomeS;

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(NSEB == null) {
            NSEB = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareEB.ttf");
        }
        if(NSB == null) {
            NSB = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareB.ttf");
        }
        if(NSR == null) {
            NSR = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareR.ttf");
        }
        if(NSREB == null) {
            NSREB = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareRoundEB.ttf");
        }
        if(NSRB == null) {
            NSRB = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareRoundB.ttf");
        }
        if(NSRR == null) {
            NSRR = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareRoundR.ttf");
        }
        if(fontAwesomeR == null) {
            fontAwesomeR = Typeface.createFromAsset(this.getAssets(), "fonts/fa-regular-400.ttf");
        }
        if(fontAwesomeS == null) {
            fontAwesomeS = Typeface.createFromAsset(this.getAssets(), "fonts/fa-solid-900.ttf");
        }
        setGlobalFont(getWindow().getDecorView());
    }

    private void setGlobalFont(View view) {
        if(view != null) {
            if(view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view;
                int vgCnt = viewGroup.getChildCount();
                for(int i = 0; i<vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if (v instanceof Button) {
                        switch(String.valueOf(v.getTag())) {
                            case "far"  : ((Button) v).setTypeface(fontAwesomeR); break;
                            case "fas"  : ((Button) v).setTypeface(fontAwesomeS); break;
                            case "nseb" : ((Button) v).setTypeface(NSEB); break;
                            case "nsb"  : ((Button) v).setTypeface(NSB); break;
                            case "nsr"  : ((Button) v).setTypeface(NSR); break;
                            case "nsreb": ((Button) v).setTypeface(NSREB); break;
                            case "nsrb" : ((Button) v).setTypeface(NSRB); break;
                            case "nsrr" : ((Button) v).setTypeface(NSRR); break;
                        }
                    }
                    else if(v instanceof TextView || v instanceof EditText) {
                        switch(String.valueOf(v.getTag())) {
                            case "far"  : ((TextView) v).setTypeface(fontAwesomeR); break;
                            case "fas"  : ((TextView) v).setTypeface(fontAwesomeS); break;
                            case "nseb" : ((TextView) v).setTypeface(NSEB); break;
                            case "nsb"  : ((TextView) v).setTypeface(NSB); break;
                            case "nsr"  : ((TextView) v).setTypeface(NSR); break;
                            case "nsreb": ((TextView) v).setTypeface(NSREB); break;
                            case "nsrb" : ((TextView) v).setTypeface(NSRB); break;
                            case "nsrr" : ((TextView) v).setTypeface(NSRR); break;
                        }
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}