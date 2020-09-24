package com.springboks.takeawaymessenger.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.springboks.takeawaymessenger.R;

public class LoginOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void cancelOrderNumber(View view) {
        finish();
    }

    public void orderNumberLogin(View view) {

        MainActivity.logedIn =true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}