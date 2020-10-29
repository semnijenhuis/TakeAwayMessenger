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
import com.springboks.takeawaymessenger.dbHandlers.SpecificOrderHandler;
import com.springboks.takeawaymessenger.model.Order;

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

    public void orderNumberLogin(final View view) {
        MainActivity.loggedIn = true;
        final Intent intent = new Intent(this, OrderActivity.class);
        EditText orderIdInput = findViewById(R.id.orderNumberLogin);
        String orderNumber = orderIdInput.getText().toString();
        SpecificOrderHandler soh = new SpecificOrderHandler(Integer.parseInt(orderNumber));

        soh.setOnSpecificOrderReceivedListener(new SpecificOrderHandler.onSpecificOrderReceivedListener() {
            @Override
            public void displayOrder(Order order) {
                boolean orderMatch = false;
               if(order != null){
                   intent.putExtra("orderId",order.getOrderID());
                   startActivity(intent);
                   orderMatch = true;
               }
               if(!orderMatch){
                   Toast.makeText(view.getContext(), "Entered order number incorrect", Toast.LENGTH_SHORT).show();
               }
            }

        });
    }
}