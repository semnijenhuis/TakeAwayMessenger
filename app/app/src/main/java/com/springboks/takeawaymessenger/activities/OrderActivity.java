package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.springboks.takeawaymessenger.dbHandlers.OrderHandler;
import com.springboks.takeawaymessenger.adapters.OrderDetailsListAdapter;
import com.springboks.takeawaymessenger.dbHandlers.ProductHandler;
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.model.Product;
import com.springboks.takeawaymessenger.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private List<Product> products;
    private List<Order> orders;
    private OrderDetailsListAdapter adapter;
    private int position;
    private ImageView imageView;
    private Order order;
    private TextView address;
    private FloatingActionButton floatingActionButton;
    private OrderHandler oh;
    private ProductHandler ph;
    private int userId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        floatingActionButton = findViewById(R.id.messageButton);

        final ListView listView = findViewById(R.id.orderDetailsList);
        intent = getIntent();
        userId = intent.getIntExtra("userId",0);

        System.out.println("Global Position = " + position);
        oh = new OrderHandler(userId);
        oh.setOnOrdersReceivedListener(new OrderHandler.onOrdersReceivedListener() {
            @Override
            public void displayOrders(List<Order> ordersFromDatabase) {

                intent = getIntent();
                position =  intent.getIntExtra("listItemPosition", 0);
                System.out.println("UserId = " + userId);
                orders = ordersFromDatabase;

                System.out.println(orders.toString());

                for (Order order: orders
                     ) {
                    System.out.println("OrderId = " + order.getOrderID());
                }

                System.out.println(position + " = position ");
                order = orders.get(position);
                System.out.println("Current Order: "+ order.getOrderID() + ", at postion " + position );
                products = new ArrayList<>();

                imageView = findViewById(R.id.orderpage_ImageID);
                InputStream inputStream = null;
                try {
                    String imageFile = order.getImageFile();

                    inputStream = getAssets().open(imageFile);
                    Drawable d = Drawable.createFromStream(inputStream, null);
                    imageView.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                address = findViewById(R.id.orderpage_addressID);
                address.setText("placeholder address");

                ph = new ProductHandler(order);
                ph.setOnProductsReceivedListener(new ProductHandler.onProductsReceivedListener() {
                    @Override
                    public void displayProducts(List<Product> products) {
                        adapter = new OrderDetailsListAdapter(listView.getContext(), products);
                        listView.setAdapter(adapter);
                    }
                });




            }
        });
    }

    public void onChatButtonPressed(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("orderId", order.getOrderID());
        startActivity(intent);
    }
}