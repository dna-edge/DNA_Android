package com.konkuk.dna.Utils;

import android.util.Log;

import com.google.gson.JsonArray;
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

        //jObj.addProperty("token", dbhelper.getAccessToken());
        jObj.addProperty("idx", dbhelper.getMyIdx());
        jObj.addProperty("nickname", dbhelper.getMyNickname());
        jObj.addProperty("avatar", dbhelper.getMyAvatar());

//        JsonObject posObj = new JsonObject();
//        posObj.addProperty("type", "Point");
//
//        JsonArray coordarr = new JsonArray();
//        coordarr.add(lng);
//        coordarr.add(lat);
//        posObj.add("coordinates", coordarr);
//
//        jObj.add("position", posObj);

        JsonArray posarr = new JsonArray();
        posarr.add(lng);
        posarr.add(lat);
        jObj.add("position", posarr);

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

        return jObj;
    }

}
