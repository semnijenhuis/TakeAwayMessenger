package com.springboks.takeawaymessenger.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.springboks.takeawaymessenger.dbHandlers.OrderHandler;
import com.springboks.takeawaymessenger.adapters.OrderDetailsListAdapter;
import com.springboks.takeawaymessenger.dbHandlers.ProductHandler;
import com.springboks.takeawaymessenger.dbHandlers.SpecificOrderHandler;
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.model.Product;
import com.springboks.takeawaymessenger.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    private List<Product> products;
    private List<Order> orders;
    private OrderDetailsListAdapter adapter;
    private int position;
    private ImageView imageView;
    private Order currentOrder;
    private TextView address;
    private FloatingActionButton floatingActionButton;
    private OrderHandler oh;
    private ProductHandler ph;
    private int userId;
    private Intent intent;
    private int currentOrderId;
    private int orderId;
    private Button orderpage_OrderCompleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        floatingActionButton = findViewById(R.id.messageButton);

        final ListView listView = findViewById(R.id.orderDetailsList);
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        position = intent.getIntExtra("listItemPosition", -1);

        currentOrderId = intent.getIntExtra("orderId", -1);

        SpecificOrderHandler soh = new SpecificOrderHandler(currentOrderId);

        soh.setOnSpecificOrderReceivedListener(new SpecificOrderHandler.onSpecificOrderReceivedListener() {
            @Override
            public void displayOrder(Order order) {
                currentOrder = order;
                imageView = findViewById(R.id.orderpage_ImageID);
                InputStream inputStream = null;
                try {
                    String imageFile = order.getImageFile();

                    inputStream = getAssets().open(imageFile);
                    Drawable d = Drawable.createFromStream(inputStream, null);
                    imageView.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

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
        intent.putExtra("orderId", currentOrder.getOrderID());
        startActivity(intent);
    }

    public void completeOrder(View view) {
        Map<String, Object> update = new HashMap<>();
        update.put("open", false);
        //setting ad database
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("orders").document()
        CollectionReference orders = db.collection("orders");

        final Query currentOrder = orders.whereEqualTo("orderId", currentOrderId);

        CollectionReference docRef = db.collection("orders");

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot qs : task.getResult()) {
                        if (!qs.getData().isEmpty()) {

                            if (Integer.parseInt(qs.getData().get("orderId").toString()) == currentOrderId) {
                                Log.i("current", Integer.parseInt(qs.getData().get("orderId").toString()) + "");
//                                qs.getDocumentReference("open").update("open", false);

                            }
                        }
                    }

                }
            }
        });
        //display results and hide button
        Toast.makeText(view.getContext(), "Order was closed", Toast.LENGTH_SHORT).show();
    }
}
