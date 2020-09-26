package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.mindrot.jbcrypt.*;

import com.springboks.takeawaymessenger.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button orderNumber = findViewById(R.id.loginOrderNumberButton);
        Button login = findViewById(R.id.loginLoginButton);
        Button createAccount = findViewById(R.id.loginCreateAccountButton);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void orderNumberStart(View view) {
        Intent intent = new Intent(this, LoginOrderActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
//        BCrypt.checkpw(password, this.password);
        MainActivity.loggedIn = true;


    }
}