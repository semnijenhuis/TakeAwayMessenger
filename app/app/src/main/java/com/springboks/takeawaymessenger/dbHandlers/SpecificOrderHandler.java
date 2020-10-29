package com.springboks.takeawaymessenger.dbHandlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.springboks.takeawaymessenger.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificOrderHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    private Order order;

    public interface onSpecificOrderReceivedListener{
        public void displayOrder(Order order);
    }
    private SpecificOrderHandler.onSpecificOrderReceivedListener listener;

    public SpecificOrderHandler(int OrderId) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener= null;
        getOrderByOrderNumber(OrderId);
    }

    public void setOnSpecificOrderReceivedListener(SpecificOrderHandler.onSpecificOrderReceivedListener listener){
        this.listener = listener;
    }

    public void getOrderByOrderNumber(final int orderNumberLoggedIn) {

        CollectionReference ordersRef = db.collection("orders");
        ordersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            int orderId = Integer.parseInt(document.getData().get("orderId").toString());
                            if (orderId == orderNumberLoggedIn) {

                                String restaurantName = document.getData().get("restaurantName").toString();
                                String date = document.getData().get("date").toString();
                                String selectedDeliveryTime = document.getData().get("selectedDeliveryTime").toString();
                                String actualDeliveryTime = document.getData().get("actualDeliveryTime").toString();
                                boolean open = Boolean.parseBoolean(document.getData().get("open").toString());
                                String customerId = "";

                                if (document.getData().get("customerId") != null) {
                                    customerId = document.getData().get("customerId").toString();
                                }

                                int courierId = Integer.parseInt(document.getData().get("courierId").toString());


                                ArrayList<Integer> productIds = new ArrayList<>();
                                if (document.getData().get("productIds") != null) {
                                    Object pIds = document.getData().get("productIds");
                                    String[] values = String.valueOf(pIds).replace("[", "").replace("]", "").replace(" ", "").split(",");
                                    for (int i = 0; i < values.length; i++) {
                                        productIds.add(Integer.parseInt(values[i]));

                                    }
                                }

                                if (customerId.isEmpty()){
                                    order = new Order(orderId, restaurantName, date, selectedDeliveryTime, actualDeliveryTime, open, courierId, productIds);
                                }
                                else {
                                    order = new Order(orderId, restaurantName, date, selectedDeliveryTime, actualDeliveryTime, open, Integer.parseInt(customerId), courierId, productIds);
                                }
                                break;
                            } else {
                                order = null;
                            }
                        }
                    }
                    listener.displayOrder(order);
                }
            }

        });

    }
}
