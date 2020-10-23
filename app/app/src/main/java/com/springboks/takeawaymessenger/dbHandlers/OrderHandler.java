package com.springboks.takeawaymessenger.dbHandlers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.model.Product;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    Map<String,Object> orderData = Collections.emptyMap();


    public interface onOrdersReceivedListener {
        public void displayOrders(List<Order> orders);
    }

    private onOrdersReceivedListener listener;

    public OrderHandler(int id) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener = null;
        getOrders(id);
    }

    public OrderHandler(String orderNumberString) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener = null;
        try {
            Integer orderNumber = Integer.parseInt(orderNumberString);
            getOrderByOrderNumber(orderNumber);
        } catch (Exception e) {

        }
    }

    public void setOnOrdersReceivedListener(onOrdersReceivedListener listener) {
        this.listener = listener;
    }

    public void getOrders(final int accountId) {
        CollectionReference ordersRef = db.collection("orders");
        ordersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Order> orders = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            int orderId = Integer.parseInt(document.getData().get("orderId").toString());
                            String restaurantName = document.getData().get("restaurantName").toString();
                            String date = document.getData().get("date").toString();
                            String selectedDeliveryTime = document.getData().get("selectedDeliveryTime").toString();
                            String actualDeliveryTime = document.getData().get("actualDeliveryTime").toString();
                            boolean open = Boolean.parseBoolean(document.getData().get("open").toString());
                            int customerId = Integer.parseInt(document.getData().get("customerId").toString());
                            int courierId = Integer.parseInt(document.getData().get("courierId").toString());

                            ArrayList<Integer> productIds = new ArrayList<>();
                            if (document.getData().get("productIds") != null) {
                                Object pIds = document.getData().get("productIds");
                                String[] values = String.valueOf(pIds).replace("[", "").replace("]", "").replace(" ", "").split(",");
                                for (int i = 0; i < values.length; i++) {
                                    productIds.add(Integer.parseInt(values[i]));

                                }
                            }

                            System.out.println("pids =" + productIds);

                            Order order = new Order(orderId, restaurantName, date, selectedDeliveryTime, actualDeliveryTime, open, customerId, courierId, productIds);
                            if (accountId == courierId || accountId == customerId) {
                                orders.add(order);
                            }
                        }

                        Log.d("DBHandlerG", document.getId() + " => " + document.getData());
                    }
                    listener.displayOrders(orders);
                } else {
                    Log.d("DBHandlerG", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public Map<String, Object> getOrderByOrderNumber(final int orderNumberLoggedIn) {
        Log.i("luc", "im at get order by order number");

        CollectionReference ordersRef = db.collection("orders");
        ordersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            int orderId = Integer.parseInt(document.getData().get("orderId").toString());
                            if (orderId == orderNumberLoggedIn){
                                orderData.putAll(document.getData());
                            }
                        }
                    }
                }
            }
        });
        return orderData;
    }
}

