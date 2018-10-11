package com.konkuk.dna.httpjson;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonToObj {

    public void LoginJsonToObj(String jsonResult){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonResult);

        if(jsonObject.get("status")!=null){
            //리스폰스가 정상이라면
            Log.e("!!!=", jsonObject.get("status").toString());
        }else{
            //리스폰스에 하자가 있다면
            Log.e(jsonObject.get("code").toString(), jsonObject.get("message").toString());
        }


    }
}
