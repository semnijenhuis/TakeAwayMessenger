package com.springboks.takeawaymessenger.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import com.springboks.takeawaymessenger.Adapters.CustomListAdapter;
import com.springboks.takeawaymessenger.Model.Order;
import com.springboks.takeawaymessenger.Model.OrderAdmin;
import com.springboks.takeawaymessenger.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static boolean logedIn;

    private OrderAdmin orderAdmin;
    private List orders;
    private CustomListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!logedIn) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

        setContentView(R.layout.activity_main);




        ListView listView = findViewById(R.id.orderList);

        orderAdmin = new OrderAdmin();
        orders = OrderAdmin.orderList;

        if (orders.size() == 0) {
            orderAdmin.setOrderList();
            orders = orderAdmin.getOrderList();
        }

        adapter = new CustomListAdapter(this, orders);
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
}