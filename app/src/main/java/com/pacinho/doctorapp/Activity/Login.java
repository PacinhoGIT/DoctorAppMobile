package com.pacinho.doctorapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pacinho.doctorapp.CryptoHandler;
import com.pacinho.doctorapp.GlobalParameters;
import com.pacinho.doctorapp.R;
import com.pacinho.doctorapp.WS.WSConnector;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        //Login Test
        etUsername.setText("D-0012");
        etPassword.setText("1234567");
        //Login Test

        final Button bLogin = (Button) findViewById(R.id.bSignIn);


        bLogin.setOnClickListener
                (new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         String username = etUsername.getText().toString();
                         String password = etPassword.getText().toString();

                         try {
                             WSConnector.getDoctorByLogin(username);
                             if (GlobalParameters.doctor != null) {
                                 try {
                                     String hashPass = CryptoHandler.hashToSHA256(password);
                                     if (GlobalParameters.doctor.getPassword().equals(hashPass)) {
                                         Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
                                         Intent intent = new Intent(Login.this, Main.class);
                                         Login.this.startActivity(intent);
                                     }
                                 } catch (NoSuchAlgorithmException e) {
                                     e.printStackTrace();
                                 }
                             } else {
                                 Toast.makeText(getBaseContext(), "Login Failed !", Toast.LENGTH_LONG).show();
                             }
                         } catch (IOException e) {
                             System.out.println(e.getMessage());
                         }


                     }
                 }

                );


    }
}

