package com.konkuk.dna.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConvertType {
    /*
    * Date를 실제 어플리케이션에서 사용하는 양식으로 변경
    * */
    public static String DatetoStr(String datestr){
        // "2018-10-23T06:19:47.180Z"

        String result="";
        SimpleDateFormat formatter = new SimpleDateFormat ( "MM월dd일 HH시mm분", Locale.KOREA );

        try {
            Date dateorigin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(datestr);
            result = formatter.format(dateorigin);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return result;
    }

    /*
     * String에서 따옴표 제거하는 메소드
     * */
    public static String getStringNoQuote(String strQuote){
        String[] nonQuote = strQuote.split("\"");
        return nonQuote[1];
    }
}
