package com.okey.konserrezervasyon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity {

    SQLHelper DB;
    String password,username,email;
    TextView txtUsername,txtPassword,txtEMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        txtUsername = findViewById(R.id.txtInfoPageUsername);
        txtPassword = findViewById(R.id.txtInfoPagePassword);
        txtEMail = findViewById(R.id.txtInfoPageEMail);

        Intent intent = getIntent();
        username =intent.getStringExtra("username");
        DB = new SQLHelper(getApplicationContext());

        password = DB.getPasswordByUsername(username);
        email = DB.getEMailByUsername(username);



        txtUsername.setText(" : " +username);
        txtPassword.setText(" : " + password);
        txtEMail.setText(" : " + email);


    }
}