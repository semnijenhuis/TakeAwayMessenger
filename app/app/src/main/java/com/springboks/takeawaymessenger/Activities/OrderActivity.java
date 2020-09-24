package com.springboks.takeawaymessenger.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.springboks.takeawaymessenger.Adapters.OrderDetailsListAdapter;
import com.springboks.takeawaymessenger.Model.Order;
import com.springboks.takeawaymessenger.Model.OrderAdmin;
import com.springboks.takeawaymessenger.Model.Product;
import com.springboks.takeawaymessenger.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private List<Product> products;
    private List<Order> orders;
    private OrderDetailsListAdapter adapter;
    private int position;
    private ImageView imageView;
    private Order order;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_order);

        ListView listView = findViewById(R.id.orderDetailsList);

        position = intent.getIntExtra("listItemPosition", 0);
        orders = OrderAdmin.orderList;
        products = orders.get(position).getProducts();
        order = orders.get(position);

        imageView = findViewById(R.id.orderpage_ImageID);
        InputStream inputStream = null;
        try {
            String imageFile = order.getImageFile();
            Log.i("Yoo", imageFile);

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
        address.setText(order.getCustomer().getAddress());

        adapter = new OrderDetailsListAdapter(this, products);
        listView.setAdapter(adapter);

    }
}