package com.konkuk.dna.httpjson;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class JsonToObj {
    /*
    * 클래스 설명
    * : Json Java Object로 변환하는 클래스
    * */

    /*
    * 로그인 할때 날아온 Json변환 메소드
    * */
    public HashMap LoginJsonToObj(String jsonResult){

        HashMap<String, String> hm = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonResult);

        if(jsonObject.get("status")!=null && jsonObject.get("status").toString().equals("200")){
            // 리스폰스가 정상이고 서버 응답이 200이라면?
            hm.put("issuccess", "true");
            JsonObject resultObject = (JsonObject) jsonObject.get("result");
            JsonObject profileObject = (JsonObject) resultObject.get("profile");

            // 유저정보를 해시맵에 저장
            hm.put("idx", profileObject.get("idx").toString());
            hm.put("id", profileObject.get("id").toString());
            hm.put("nickname", profileObject.get("nickname").toString());
            hm.put("avatar", profileObject.get("avatar").toString());
            hm.put("description", profileObject.get("description").toString());
            hm.put("radius", profileObject.get("radius").toString());
            hm.put("is_anonymity", profileObject.get("is_anonymity").toString());

            //토큰을 해시맵에 저장
            JsonObject tokenObject = (JsonObject) resultObject.get("token");
            hm.put("accessToken", tokenObject.get("accessToken").toString());
            hm.put("refreshToken", tokenObject.get("refreshToken").toString());

            Log.e("!!!=accessToken", tokenObject.get("accessToken").toString());

        }else{
            //리스폰스에 하자가 있다면
            Log.e(jsonObject.get("code").toString(), jsonObject.get("message").toString());
            hm.put("issuccess", "false");
            hm.put("code", jsonObject.get("code").toString());
            hm.put("message", jsonObject.get("message").toString());
        }

        return hm;
    }

    /*
    * auth토큰 획득 Json변환 메소드
    * */
    public HashMap TokenJsonToObj(String jsonResult){

        HashMap<String, String> hm = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonResult);

        if(jsonObject.get("status")!=null && jsonObject.get("status").toString().equals("200")){
            // 리스폰스가 정상이고 서버 응답이 200이라면?
            hm.put("issuccess", "true");
            JsonObject resultObject = (JsonObject) jsonObject.get("result");

            // 해시맵에 저장
            hm.put("accessToken", resultObject.get("accessToken").toString());
            Log.e("!!!=accessToken", resultObject.get("accessToken").toString());

        }else{
            //리스폰스에 하자가 있다면
            Log.e(jsonObject.get("code").toString(), jsonObject.get("message").toString());
            hm.put("issuccess", "false");
            hm.put("code", jsonObject.get("code").toString());
            hm.put("message", jsonObject.get("message").toString());
        }

        return hm;
    }

    /*
     * 회원가입 Json변환 메소드
     * */
    public HashMap RegisterJsonToObj(String jsonResult){

        HashMap<String, String> hm = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonResult);

        if(jsonObject.get("status")!=null && jsonObject.get("status").toString().equals("201")){
            // 리스폰스가 정상이고 서버 응답이 200이라면?
            hm.put("issuccess", "true");
            JsonObject resultObject = (JsonObject) jsonObject.get("result");

            // 해시맵에 저장
            hm.put("idx", resultObject.get("idx").toString());
            hm.put("id", resultObject.get("id").toString());
            Log.e("!!!=id", resultObject.get("id").toString());

        }else{
            //리스폰스에 하자가 있다면
            if(jsonObject.get("code")!=null) {
                //ID 중복확인 메세지라면
                Log.e(jsonObject.get("code").toString(), jsonObject.get("message").toString());
                hm.put("issuccess", "false");
                hm.put("code", jsonObject.get("code").toString());
                hm.put("message", jsonObject.get("message").toString());
            }else{
                //ID를 쓰지 않았다면
                JsonObject uidObject = (JsonObject) jsonObject.get("errors");
                JsonObject msgObject = (JsonObject) uidObject.get("id");
                hm.put("name", jsonObject.get("name").toString());
                hm.put("message", msgObject.get("message").toString());

                Log.e(jsonObject.get("name").toString(), msgObject.get("message").toString());
            }
        }

        return hm;
    }

}
