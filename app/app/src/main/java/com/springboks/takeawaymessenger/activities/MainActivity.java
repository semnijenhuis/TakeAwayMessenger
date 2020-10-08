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

        OrderHandler db = new OrderHandler(1);
        db.setOnOrdersReceivedListener(new OrderHandler.onOrdersReceivedListener() {
            @Override
            public void displayOrders(List<Order> ordersFromDatabase) {
                orders = ordersFromDatabase;
                CustomListAdapter adapter = new CustomListAdapter(listView.getContext(), orders);
                listView.setAdapter(adapter);


            }
        });

//        if (orders.size() == 0) {
//            orderAdmin.setOrderList();
//            orders = orderAdmin.getOrderList();
//        }
        CustomListAdapter adapter = new CustomListAdapter(listView.getContext(), orders);
        listView.setAdapter(adapter);
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