package com.konkuk.dna.Utils;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.konkuk.dna.chat.ChatMessage;

import java.util.ArrayList;
import java.util.HashMap;

import static com.konkuk.dna.Utils.ConvertType.DatetoStr;
import static com.konkuk.dna.Utils.ConvertType.getStringNoQuote;

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

    /*
     * 전체 메세지 조회해서 날아온 Json변환 메소드
     * */
    public static ArrayList<ChatMessage> ChatAllJsonToObj(String jsonResult){
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonResult);

        ArrayList<Integer> whoLikes = new ArrayList<>();
        int user_idx;
        String nickname, avatar, position,like_count;
        double lng, lat;
        String msg_type, _id, contents, created_at;
        int idx, __v;


        if(jsonObject.get("status")!=null && jsonObject.get("status").toString().equals("200")) {
            JsonArray resultArray = (JsonArray) jsonObject.get("result");

            for(int i=0; i<resultArray.size(); i++){
                JsonObject oneObject = (JsonObject) resultArray.get(i);

                JsonObject userObject = (JsonObject) oneObject.get("user");
                user_idx = Integer.parseInt(userObject.get("idx").toString());
                nickname = getStringNoQuote(userObject.get("nickname").toString());
                avatar = getStringNoQuote(userObject.get("avatar").toString());

                JsonObject posObject = (JsonObject) oneObject.get("position");
                JsonArray coorArray = (JsonArray) posObject.get("coordinates");
                lng = Double.parseDouble(coorArray.get(0).toString());
                lat = Double.parseDouble(coorArray.get(1).toString());

                msg_type = getStringNoQuote(oneObject.get("type").toString());
                like_count = oneObject.get("like_count").toString();
                JsonArray wholikesArray = (JsonArray) oneObject.get("likes");
                for(int j=0; j<wholikesArray.size(); j++){
                    whoLikes.add(Integer.parseInt(wholikesArray.get(j).toString()));
                }

                _id = getStringNoQuote(oneObject.get("_id").toString());
                contents = getStringNoQuote(oneObject.get("contents").toString());
                created_at = getStringNoQuote(oneObject.get("created_at").toString());
                idx = Integer.parseInt(oneObject.get("idx").toString());
                __v = Integer.parseInt(oneObject.get("__v").toString());

                chatMessages.add(new ChatMessage(user_idx, nickname, avatar, contents, DatetoStr(created_at), like_count, msg_type, lng, lat));
            }
        }else{
            chatMessages = null;
        }

        return chatMessages;
    }
}
