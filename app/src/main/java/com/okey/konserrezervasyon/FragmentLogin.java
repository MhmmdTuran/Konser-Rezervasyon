package com.okey.konserrezervasyon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentLogin extends Fragment {

    EditText username,password;

    Button btnLogin;

    SQLHelper DB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        DB = new SQLHelper(getContext());

        username = root.findViewById(R.id.txtLoginUsername);
        password = root.findViewById(R.id.txtLoginPassword);
        btnLogin = root.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals(""))
                    Toast.makeText(getContext(),"Lütfen Bir Kullanıcı Adı Ve Şifre Giriniz",Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpas = DB.authentication(user,pass);
                    if(checkuserpas){
                        Toast.makeText( getContext(),"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), Home.class);
                        intent.putExtra("username", user);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getContext(),"Kullanıcı Adı Veya Şifre Yanlış",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



        return root;
    }
}