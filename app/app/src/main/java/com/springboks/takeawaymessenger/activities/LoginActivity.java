package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.springboks.takeawaymessenger.R;
import com.springboks.takeawaymessenger.dbHandlers.AccountHandler;
import com.springboks.takeawaymessenger.model.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    AccountHandler ah;
    TextView userNameField;
    TextView passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button orderNumber = findViewById(R.id.loginOrderNumberButton);
        Button login = findViewById(R.id.loginLoginButton);
        userNameField = findViewById(R.id.userName);
        passwordField = findViewById(R.id.loginPassword);
    }

    public void orderNumberStart(View view) {
        Intent intent = new Intent(this, LoginOrderActivity.class);
        startActivity(intent);
    }

    public void login(final View view) {
        ah = new AccountHandler();

        ah.setOnAccountsReceivedListener(new AccountHandler.onAccountsReceivedListener() {
            @Override
            public void displayAccounts(List<User> accounts) {
                String enteredUserName = userNameField.getText().toString();
                String enteredPassword = passwordField.getText().toString();
                boolean accountMatch = false;

                for (User user: accounts
                     ) {
                    if (enteredUserName.equals(user.getUserName()) && enteredPassword.equals(user.getPassWord())){
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        intent.putExtra("userId", user.getUserID());
                        startActivity(intent);
                        MainActivity.loggedIn = true;
                        accountMatch= true;

                    }
                }
                if(!accountMatch){
                    Toast.makeText(view.getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}