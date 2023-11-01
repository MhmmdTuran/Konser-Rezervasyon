package com.okey.konserrezervasyon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {

    Button btnExit,btnAddConsert,btnBuyConsert,btnUserInfo;


    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        username =intent.getStringExtra("username");

        btnExit = findViewById(R.id.btnExitPage);
        btnAddConsert = findViewById(R.id.btnAddConsertPage);
        btnBuyConsert = findViewById(R.id.btnBuyTicketPage);
        btnUserInfo = findViewById(R.id.btnUserInfoPage);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnAddConsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddTicket.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnBuyConsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BuyTicket.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

         btnUserInfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(),UserInfo.class);
                 intent.putExtra("username",username);
                 startActivity(intent);
             }
         });

    }
}