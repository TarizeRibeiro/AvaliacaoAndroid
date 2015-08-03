package com.example.fabricio.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fabricio.myapplication.R;
import com.example.fabricio.myapplication.model.entities.User;

/**
 * Created by Fabricio on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity {


    Button buttonLogin;
    EditText editTextUserName;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        bindLoginElements();
        bindLoginButton();
    }

    private void bindLoginElements(){
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
    }

    private void bindLoginButton(){
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindLoginElements();
                User user = new User();
                user.setUserName(editTextUserName.getText().toString());
                user.setPassword(editTextPassword.getText().toString());


                if(user.authentication()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();

                    Intent goToMainAcitivity = new Intent(LoginActivity.this, ClientListAcitvity.class);
                    startActivity(goToMainAcitivity);
                }else{
                    dlgInvalidUser();
                }

            }
        });
    }

    private void dlgInvalidUser(){
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(getString(R.string.user_password_invalid))
                .setTitle(getString(R.string.try_again))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();


    }
}
