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
    private static Typeface typefaceMenu;
    private static Typeface typefaceBold;
    private static Typeface typefaceRegular;
    private static Typeface fontAwesomeR;
    private static Typeface fontAwesomeS;

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(typefaceMenu == null) {
            typefaceMenu = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareB.ttf");
        }
        if(typefaceBold == null) {
            typefaceBold = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareRoundEB.ttf");
        }
        if(typefaceRegular == null) {
            typefaceRegular = Typeface.createFromAsset(this.getAssets(), "fonts/NanumSquareRoundR.ttf");
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
                        if (String.valueOf(v.getTag()).equals("far")) {
                            ((Button) v).setTypeface(fontAwesomeR);
                        } else if (String.valueOf(v.getTag()).equals("fas")) {
                            ((Button) v).setTypeface(fontAwesomeS);
                        } else if (String.valueOf(v.getTag()).equals("menu")) {
                            ((Button) v).setTypeface(typefaceMenu);
                        } else {
                            ((Button) v).setTypeface(typefaceBold);
                        }
                    }
                    else if(v instanceof TextView || v instanceof EditText) {
                        if (String.valueOf(v.getTag()).equals("far")) {
                            ((TextView) v).setTypeface(fontAwesomeR);
                        } else if (String.valueOf(v.getTag()).equals("fas")) {
                            ((TextView) v).setTypeface(fontAwesomeS);
                        } else if (String.valueOf(v.getTag()).equals("menu")) {
                            ((TextView) v).setTypeface(typefaceMenu);
                        } else if (String.valueOf(v.getTag()).equals("bold")) {
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