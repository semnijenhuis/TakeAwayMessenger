package com.springboks.takeawaymessenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.springboks.takeawaymessenger.adapters.CustomListAdapter;
import com.springboks.takeawaymessenger.model.OrderAdmin;
import com.springboks.takeawaymessenger.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static boolean loggedIn;

    private OrderAdmin orderAdmin;
    private List orders;
    private CustomListAdapter adapter;
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

        ListView listView = findViewById(R.id.orderList);

        OrderAdmin orderAdmin = new OrderAdmin();
        List orders = OrderAdmin.orderList;

        if (orders.size() == 0) {
            orderAdmin.setOrderList();
            orders = orderAdmin.getOrderList();
        }

        CustomListAdapter adapter = new CustomListAdapter(this, orders);
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