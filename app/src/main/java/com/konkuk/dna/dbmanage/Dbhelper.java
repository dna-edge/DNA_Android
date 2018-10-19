package com.konkuk.dna.dbmanage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Dbhelper extends SQLiteOpenHelper {

    /*
    * 업데이트를 하다가 혹시 디비구조가 변경되면 반드시 버전 숫자를 올려주어야 함
    * */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DNATokenDB.db";

    public static class DNAEntry implements BaseColumns{
        public static final String TABLE_NAME = "userinfo";
        public static final String COLUME_NAME_AUTHTOKEN = "authToken";
        public static final String COLUME_NAME_REFRESHTOKEN = "refreshToken";
        public static final String COLUME_NAME_IDX = "idx";
        public static final String COLUME_NAME_ID = "id";
        public static final String COLUME_NAME_EMAIL = "email";
        public static final String COLUME_NAME_NICKNAME = "nickname";
        public static final String COLUME_NAME_AVATAR = "avatar";
        public static final String COLUME_NAME_DESCRIPTION = "description";
        public static final String COLUME_NAME_RADIUS = "radius";
        public static final String COLUME_NAME_POINTS = "points";
        public static final String COLUME_NAME_ANONIMITY = "is_anonimity";
        public static final String COLUME_NAME_FAULTY = "is_faulty";
        public static final String COLUME_NAME_CREATEDAT = "created_at";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DNAEntry.TABLE_NAME + " (" +
                    DNAEntry._ID + " INTEGER PRIMARY KEY," +
                    DNAEntry.COLUME_NAME_AUTHTOKEN +  " TEXT," +
                    DNAEntry.COLUME_NAME_REFRESHTOKEN +  " TEXT," +
                    DNAEntry.COLUME_NAME_IDX +  " TEXT," +
                    DNAEntry.COLUME_NAME_ID +  " TEXT," +
                    DNAEntry.COLUME_NAME_EMAIL +  " TEXT," +
                    DNAEntry.COLUME_NAME_NICKNAME +  " TEXT," +
                    DNAEntry.COLUME_NAME_AVATAR +  " TEXT," +
                    DNAEntry.COLUME_NAME_DESCRIPTION +  " TEXT," +
                    DNAEntry.COLUME_NAME_RADIUS +  " TEXT," +
                    DNAEntry.COLUME_NAME_POINTS +  " TEXT," +
                    DNAEntry.COLUME_NAME_ANONIMITY +  " TEXT," +
                    DNAEntry.COLUME_NAME_FAULTY +  " TEXT," +
                    DNAEntry.COLUME_NAME_CREATEDAT +  " TEXT )";

    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
