package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.springboks.takeawaymessenger.dbHandlers.OrderHandler;
import com.springboks.takeawaymessenger.adapters.CustomListAdapter;
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static boolean loggedIn;
    List<Order> orders;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!loggedIn) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.orderList);
        orders = new ArrayList<>();

        Intent intent= getIntent();
        userId = intent.getIntExtra("userId",0);
        OrderHandler db = new OrderHandler(userId);
        db.setOnOrdersReceivedListener(new OrderHandler.onOrdersReceivedListener() {
            @Override
            public void displayOrders(List<Order> ordersFromDatabase) {
                orders = ordersFromDatabase;
//                List<Order> usersOrders = new ArrayList<>();
//                for (Order order: orders
//                     ) {
//                    System.out.println("courierId = " + order.getCourierId() + "customerId = " + order.getCustomerId() + "intentId = " + userId );
//                    if (order.getCourierId() == userId || order.getCustomerId() == userId  ){
//                        usersOrders.add(order);
//                    }
//                }

                CustomListAdapter adapter = new CustomListAdapter(listView.getContext(), orders);
                listView.setAdapter(adapter);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                whenOrderClicked(position);
            }
        });

    }

    private void whenOrderClicked(int position) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("ListItemPosition", position);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void mainActivityFinish(View view) {
        finish();
    }
}