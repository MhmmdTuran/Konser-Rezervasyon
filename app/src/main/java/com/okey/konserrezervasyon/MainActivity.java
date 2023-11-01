package com.okey.konserrezervasyon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnChsLogin,btnChsReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChsLogin = findViewById(R.id.btnChooseLogin);
        btnChsReg = findViewById(R.id.btnChooseRegister);

        FragmentManager fragmentManager = getSupportFragmentManager();


        btnChsLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerLoginForm,FragmentLogin.class,null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        btnChsReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerLoginForm,FragmentRegister.class,null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

    }
}