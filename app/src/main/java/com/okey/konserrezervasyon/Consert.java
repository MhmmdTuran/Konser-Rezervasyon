package com.okey.konserrezervasyon;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Consert {
    private String artistName,consertDate,consertPlace;
    private Bitmap consertPicture;

    static SQLHelper DB;

    public Consert(){

    }

    public Consert(String artistName, String consertDate, String consertPlace, Bitmap consertPicture) {
        this.artistName = artistName;
        this.consertDate = consertDate;
        this.consertPlace = consertPlace;
        this.consertPicture = consertPicture;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getConsertDate() {
        return consertDate;
    }

    public void setConsertDate(String consertDate) {
        this.consertDate = consertDate;
    }

    public String getConsertPlace() {
        return consertPlace;
    }

    public void setConsertPlace(String consertPlace) {
        this.consertPlace = consertPlace;
    }

    public Bitmap getConsertPicture() {
        return consertPicture;
    }

    public void setConsertPicture(Bitmap consertPicture) {
        this.consertPicture = consertPicture;
    }

    static public ArrayList<Consert> getData(Context context){
        ArrayList<Consert> consertList = new ArrayList<>();

        ArrayList<String> artistNameList = new ArrayList<>();
        ArrayList<String> consertDateList = new ArrayList<>();
        ArrayList<String> consertPlaceList = new ArrayList<>();
        ArrayList<Bitmap> consertPictureList = new ArrayList<>();


        try {
            SQLiteDatabase database = context.openOrCreateDatabase("Konserium",Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,null);
            DB = new SQLHelper(context.getApplicationContext());
            //Cursor cursor = database.rawQuery("SELECT * FROM consertsInfo",null);
            Cursor cursor = DB.getConsertInfos();
            int artistNameIndex = cursor.getColumnIndex("artistName");
            int consertDateIndex = cursor.getColumnIndex("consertDate");
            int consertPlaceIndex = cursor.getColumnIndex("consertPlace");
            int consertPictureIndex = cursor.getColumnIndex("consertPicture");

            while (cursor.moveToNext()){
                artistNameList.add(cursor.getString(artistNameIndex));
                consertDateList.add(cursor.getString(consertDateIndex));
                consertPlaceList.add(cursor.getString(consertPlaceIndex));

                byte[] incomedPictureByte = cursor.getBlob(consertPictureIndex);
                Bitmap incomedPicture = BitmapFactory.decodeByteArray(incomedPictureByte,0,incomedPictureByte.length);
                consertPictureList.add(incomedPicture);
            }

            cursor.close();

            for (int i = 0; i < artistNameList.size(); i++) {
                Consert consert = new Consert();
                consert.setArtistName(artistNameList.get(i));
                consert.setConsertDate(consertDateList.get(i));
                consert.setConsertPlace(consertPlaceList.get(i));
                consert.setConsertPicture(consertPictureList.get(i));

                consertList.add(consert);

                
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return consertList;
    }
}
