package com.konkuk.dna;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {
    private static Typeface typefaceBold;
    private static Typeface typefaceRegular;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(typefaceBold == null) {
            typefaceBold = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareRoundEB.ttf");
        }
        if(typefaceRegular == null) {
            typefaceRegular = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareRoundR.ttf");
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
                        ((Button) v).setTypeface(typefaceBold);
                    }
                    else if(v instanceof TextView || v instanceof EditText) {
                        if (String.valueOf(v.getTag()).equals("bold")) {
                            ((TextView) v).setTypeface(typefaceBold);
                        } else {
                            ((TextView) v).setTypeface(typefaceRegular);
                        }
                    }
                    setGlobalFont(v);
                }
            }
        }
    }

}