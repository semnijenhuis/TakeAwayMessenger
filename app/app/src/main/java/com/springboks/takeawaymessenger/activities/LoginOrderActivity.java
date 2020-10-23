package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.springboks.takeawaymessenger.R;
import com.springboks.takeawaymessenger.dbHandlers.OrderHandler;

public class LoginOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button login = findViewById(R.id.loginLoginButton);


    }

    public void cancelOrderNumber(View view) {
        finish();
    }

    public void orderNumberLogin(View view) {
        MainActivity.loggedIn = true;
        Intent intent = new Intent(this, MainActivity.class);
        EditText orderIdInput = findViewById(R.id.orderNumberLogin);

        String orderNumber = orderIdInput.getText().toString();
        OrderHandler oh = new OrderHandler(orderNumber);


        startActivity(intent);

    }
}