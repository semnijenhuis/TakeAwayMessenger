package com.springboks.takeawaymessenger.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.springboks.takeawaymessenger.R;

public class LoginActivity extends AppCompatActivity {

    Button orderbNumber;
    Button createAccount;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        orderbNumber = findViewById(R.id.loginOrderNumberButton);
        createAccount = findViewById(R.id.loginCreateAccountButton);
        login = findViewById(R.id.loginLoginButton);


    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);

    }

    public void orderNumberstart(View view) {
        Intent intent = new Intent(this, LoginOrderActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        MainActivity.logedIn =true;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

}