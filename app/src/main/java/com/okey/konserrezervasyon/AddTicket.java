package com.okey.konserrezervasyon;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

public class AddTicket extends AppCompatActivity {

    ImageView imgArtist;

    EditText txtArtistName, txtConsertDate;
    AutoCompleteTextView txtConsertPlace;

    Button btnAddConsert;

    String username,artistName, consertDate, consertPlace;
    String[] Cities = new String[81];

    int unreq_code = 0, req_code = 1;

    Bitmap choosenPicture, minimizedPicture,defaultPicture;

    SQLHelper DB;


    /*ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == req_code && result != null){
                Intent data = result.getData();

                Uri pictureUri = data.getData();

                try {
                    if(Build.VERSION.SDK_INT>=28) {
                        ImageDecoder.Source pictureSource = ImageDecoder.createSource(getContentResolver(),pictureUri);
                        choosenPicture = ImageDecoder.decodeBitmap(pictureSource);
                        imgArtist.setImageBitmap(choosenPicture);
                    }else{
                        choosenPicture = MediaStore.Images.Media.getBitmap(getContentResolver(), pictureUri);
                        imgArtist.setImageBitmap(choosenPicture);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    });*/

    private void init() {
        imgArtist = (ImageView) findViewById(R.id.imgAddPicture);
        txtArtistName = findViewById(R.id.txtArtistName);
        txtConsertDate = findViewById(R.id.txtConsertDate);
        txtConsertPlace = findViewById(R.id.txtConsertPlace);

        DB = new SQLHelper(getApplicationContext());

        Intent intent = getIntent();
        username =intent.getStringExtra("username");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        init();

        try {
            addItemsFromJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, Cities);
        txtConsertPlace.setAdapter(adapter);


    }

    public void saveConsert(View V) {

        artistName = txtArtistName.getText().toString();
        consertDate = txtConsertDate.getText().toString();
        consertPlace = txtConsertPlace.getText().toString();

        if (!TextUtils.isEmpty(artistName)) {
            if (!TextUtils.isEmpty(consertDate)) {
                if (!TextUtils.isEmpty(consertPlace)) {
                    //SAVE
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    minimizedPicture = minimizePicture(choosenPicture);
                    minimizedPicture.compress(Bitmap.CompressFormat.PNG, 75, outputStream);
                    byte[] savedPicture = outputStream.toByteArray();



                        Boolean insert = DB.insertConsertData(username,artistName,consertDate,consertPlace,savedPicture);
                        if(insert){
                            Toast.makeText(getApplicationContext(), "KAYIT BAŞARILI", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getApplicationContext(), "KAYIT BAŞARISIZ", Toast.LENGTH_SHORT).show();

                        clearPage();


                } else
                    Toast.makeText(getApplicationContext(), "Konser Yeri Boş !!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Konser Tarihi Boş !!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Sanatçı Adı Boş !!", Toast.LENGTH_SHORT).show();

    }

    private Bitmap minimizePicture(Bitmap picture) {
        return Bitmap.createScaledBitmap(picture, 120, 150, true);
    }

    private void clearPage() {
        txtArtistName.setText("");
        txtConsertPlace.setText("");
        txtConsertDate.setText("");
        defaultPicture = BitmapFactory.decodeResource(this.getResources(),R.drawable.pick_icon);
        imgArtist.setImageBitmap(defaultPicture);
    }

    public void choosePicture(View V) {


        ImagePicker.with(AddTicket.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        if (Build.VERSION.SDK_INT >= 28) {
            ImageDecoder.Source pictureSource = ImageDecoder.createSource(this.getContentResolver(), uri);
            try {
                choosenPicture = ImageDecoder.decodeBitmap(pictureSource);
                imgArtist.setImageBitmap(choosenPicture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void addItemsFromJSON() throws IOException, JSONException {
        String jsonDataString = readJSONDataFromFile();

        Gson gson = new Gson();

        Type type = new TypeToken<List<Country>>(){}.getType();
        List<Country> list = gson.fromJson(jsonDataString, type);

        for (int i = 0; i < list.size() ; i++) {
            Cities[i] = String.valueOf(list.get(i).il_adi);
        }

    }

    private String readJSONDataFromFile() {
        InputStream inputStream = null;

        StringBuilder builder = new StringBuilder();
        try {
            String jsonString = null;
            inputStream = getResources().openRawResource(R.raw.cities);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream,"UTF-8"));

            while ((jsonString=bufferedReader.readLine()) != null){
                builder.append(jsonString);
            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return new String(builder);
    }
}