package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import org.mindrot.jbcrypt.*;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.springboks.takeawaymessenger.R;
import com.springboks.takeawaymessenger.model.Customer;
import com.springboks.takeawaymessenger.model.User;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void createCreateAccountButton(View view) {
        EditText firstNameInput = findViewById(R.id.createAccountName);
        EditText lastNameInput = findViewById(R.id.createAccountLastName);
        EditText userNameInput = findViewById(R.id.createAccountUserName);
        EditText passwordInput = findViewById(R.id.createAccountPassword);

        //TODO: check if they already exist
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        //TODO: desing courier create account
//        Customer newUser = new User(firstName,lastName,userName,password);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}