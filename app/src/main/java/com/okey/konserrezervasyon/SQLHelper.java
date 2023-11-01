package com.okey.konserrezervasyon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Konserium.db";


    public SQLHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table userLoginInfo(username TEXT primary key,password TEXT," +
                "email TEXT)");

        db.execSQL("create Table consertsInfo(id INTEGER primary key,username VARCHAR," +
                "artistName VARCHAR,consertDate VARCHAR,consertPlace VARCHAR,consertPicture BLOB)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists userLoginInfo");
        db.execSQL("drop Table if exists consertsInfo");
        onCreate(db);

    }

    public Boolean insertConsertData(String username,String artistName,String consertDate,String consertPlace,byte[] consertPicture){
        SQLiteDatabase MyDB = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("artistName",artistName);
        contentValues.put("consertDate",consertDate);
        contentValues.put("consertPlace",consertPlace);
        contentValues.put("consertPicture",consertPicture);
        long result = MyDB.insert("consertsInfo",null,contentValues);
        if(result==-1) return false;
        else
            return true;

    }

    public Boolean insertUserData(String username, String password, String email){
        SQLiteDatabase MyDB = getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("email",email);
        long result = MyDB.insert("userLoginInfo",null,contentValues);
        if (result==-1) return false;
        else
            return true;
    }




    public Boolean checkUsername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from userLoginInfo where username = ?",new String[]{username});

        if(cursor.getCount()>0){
            return true;
        }
        else
            return false;
    }

    public Boolean authentication(String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from userLoginInfo where username = ? and password = ?",new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor getConsertInfos(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from consertsInfo", null, null);
        return cursor;

    }

    public String getPasswordByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String password = null;

        String[] columns = {"password"}; // Şifre sütunu adı
        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("userLoginInfo", columns, selection, selectionArgs, null, null, null);
        int passwordColumnIndex = cursor.getColumnIndex("password");
        if(passwordColumnIndex != -1) {
            if (cursor.moveToFirst()) {
                password = cursor.getString(passwordColumnIndex);
            }
        }

        cursor.close();
        db.close();

        return password;
    }

    public String getEMailByUsername(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String mail = null;

        String[] columns = {"email"}; // Şifre sütunu adı
        String selection = "username = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("userLoginInfo", columns, selection, selectionArgs, null, null, null);
        int passwordColumnIndex = cursor.getColumnIndex("email");
        if(passwordColumnIndex != -1) {
            if (cursor.moveToFirst()) {
                mail = cursor.getString(passwordColumnIndex);
            }
        }

        cursor.close();
        db.close();

        return mail;
    }
}
