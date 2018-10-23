package com.konkuk.dna.dbmanage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.HashMap;

public class Dbhelper extends SQLiteOpenHelper {

    /*
    * 업데이트를 하다가 디비구조가 변경되면 *반드시* 버전 숫자를 올려주어야 함
    * */
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "DNATokenDB.db";

    public static class DNAEntry implements BaseColumns{
        public static final String TABLE_NAME = "userinfo";
        public static final String COLUME_NAME_ACCESSTOKEN = "accessToken";
        public static final String COLUME_NAME_REFRESHTOKEN = "refreshToken";
        public static final String COLUME_NAME_IDX = "idx";
        public static final String COLUME_NAME_ID = "id";
        public static final String COLUME_NAME_NICKNAME = "nickname";
        public static final String COLUME_NAME_AVATAR = "avatar";
        public static final String COLUME_NAME_DESCRIPTION = "description";
        public static final String COLUME_NAME_RADIUS = "radius";
        public static final String COLUME_NAME_ANONIMITY = "is_anonimity";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DNAEntry.TABLE_NAME + " (" +
                    DNAEntry.COLUME_NAME_IDX +  " INTEGER," +
                    DNAEntry.COLUME_NAME_ID +  " TEXT PRIMARY KEY," +
                    DNAEntry.COLUME_NAME_NICKNAME +  " TEXT," +
                    DNAEntry.COLUME_NAME_AVATAR +  " TEXT," +
                    DNAEntry.COLUME_NAME_DESCRIPTION +  " TEXT," +
                    DNAEntry.COLUME_NAME_RADIUS +  " INTEGER," +
                    DNAEntry.COLUME_NAME_ANONIMITY +  " INTEGER," +
                    DNAEntry.COLUME_NAME_ACCESSTOKEN +  " TEXT," +
                    DNAEntry.COLUME_NAME_REFRESHTOKEN +  " TEXT )";

    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
    * DB가 존재하지 않을 경우 호출
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /*
    * DB버전이 바뀔 경우 호출
    * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + DNAEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    /*
    * 유저정보 저장 메소드
    * */
    public void saveUserInfo(HashMap<String, String> map){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DNAEntry.COLUME_NAME_IDX, Integer.parseInt(map.get("idx").toString()));
        values.put(DNAEntry.COLUME_NAME_ID, map.get("id").toString());
        values.put(DNAEntry.COLUME_NAME_NICKNAME, map.get("nickname").toString());
        values.put(DNAEntry.COLUME_NAME_AVATAR, map.get("avatar").toString());
        values.put(DNAEntry.COLUME_NAME_DESCRIPTION, map.get("description").toString());
        values.put(DNAEntry.COLUME_NAME_RADIUS, Integer.parseInt(map.get("radius").toString()));
        values.put(DNAEntry.COLUME_NAME_ANONIMITY, Integer.parseInt(map.get("is_anonymity").toString()));
        values.put(DNAEntry.COLUME_NAME_ACCESSTOKEN, map.get("accessToken").toString());
        values.put(DNAEntry.COLUME_NAME_REFRESHTOKEN, map.get("refreshToken").toString());

        /*
        * 기존에 있는 내용을 딜리트하고, 다시 유저의 정보를 디비에 저장.
        * */
        db.delete(DNAEntry.TABLE_NAME,null, null);
        db.insert(DNAEntry.TABLE_NAME, null, values);
    }

    /*
    * 토큰 가져오기
    * */
    public String getAccessToken(){
        String[] result;
        String str = null;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ DNAEntry.TABLE_NAME, null);
        while(cursor.moveToNext()){
            str = cursor.getString(7);
        }
        result = str.split("\"");
        return result[1];
    }

    /*
     * 내 idx 가져오기
     * */
    public int getMyIdx(){
        int myIdx = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ DNAEntry.TABLE_NAME, null);
        while(cursor.moveToNext()){
            myIdx = Integer.parseInt(cursor.getString(0));
        }
        return myIdx;
    }
}
