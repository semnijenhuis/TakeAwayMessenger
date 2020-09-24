package com.springboks.takeawaymessenger.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.springboks.takeawaymessenger.Adapters.CustomListAdapter;
import com.springboks.takeawaymessenger.Model.Order;
import com.springboks.takeawaymessenger.Model.OrderAdmin;
import com.springboks.takeawaymessenger.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OrderAdmin orderAdmin;
    private List orders;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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