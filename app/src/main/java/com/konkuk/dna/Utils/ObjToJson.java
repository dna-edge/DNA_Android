package com.konkuk.dna.Utils;

import android.util.Log;

import com.google.gson.JsonObject;
import com.konkuk.dna.dbmanage.Dbhelper;

import static com.konkuk.dna.Utils.ConvertType.getStringAddQuote;

public class ObjToJson {

     /*
     * 정보저장(store)용 Json 생성
     * */
    public static JsonObject StoreObjToJson(Dbhelper dbhelp, Double lng, Double lat){
        Dbhelper dbhelper = dbhelp;

        JsonObject jObj = new JsonObject();

        jObj.addProperty("idx", dbhelper.getMyIdx());
        jObj.addProperty("nickname", dbhelper.getMyNickname());
        jObj.addProperty("avatar", dbhelper.getMyAvatar());

        JsonObject posObj = new JsonObject();
        posObj.addProperty("type", "Point");
        posObj.addProperty("coordinates", "["+lng+","+lat+"]");

        jObj.add("position", posObj);
        jObj.addProperty("radius", dbhelper.getMyRadius());

        return jObj;
    }

    /*
    * 메세지 전송 용 Json 생성
    * */
    public static JsonObject SendMsgObjToJson(Dbhelper dbhelp, Double lng, Double lat, String msgType, String contents) {

        Dbhelper dbhelper = dbhelp;

        JsonObject jObj = new JsonObject();

        jObj.addProperty("token", dbhelper.getAccessToken());

        JsonObject mdataObj = new JsonObject();
        mdataObj.addProperty("lng", lng);
        mdataObj.addProperty("lat", lat);
        mdataObj.addProperty("type", msgType);
        mdataObj.addProperty("contents", contents);

        jObj.add("messageData", mdataObj);
        jObj.addProperty("radius", dbhelper.getMyRadius());

//        data = {
//                token,
//                messageData : {
//                    lng,
//                    lat,
//                    type,
//                    contents
//                }
//            radius,
//        };

        Log.e("!!!!=", jObj.toString());
        return jObj;
    }

}
