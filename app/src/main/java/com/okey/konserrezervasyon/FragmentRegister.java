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


public class FragmentRegister extends Fragment {

    EditText username,password,email;

    Button btnRegister;

    SQLHelper DB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        username = root.findViewById(R.id.txtRegisterUsername);
        password = root.findViewById(R.id.txtRegisterPassword);
        email = root.findViewById(R.id.txtRegisterEmail);
        btnRegister = root.findViewById(R.id.btnRegister);

        DB = new SQLHelper(getContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String mail = email.getText().toString();

                if(user.equals("") || pass.equals("") || mail.equals(""))
                    Toast.makeText(getContext(),"Lütfen boş yerleri doldurunuz",Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUser = DB.checkUsername(user);
                    if(checkUser==false) {
                        Boolean insert = DB.insertUserData(user,pass,mail);
                        if(insert){
                            Toast.makeText(getContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), Home.class);
                            intent.putExtra("username", user);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getContext(),"Kayıt Başarısız",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getContext(),"Bu Kullanıcı Adı Zaten Mevcut",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });





        return root;
    }
}