package com.springboks.takeawaymessenger.dbHandlers;

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
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrderHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    private Map<String,Object> orderData = Collections.emptyMap();


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
                            String customerId = "";

                            if (document.getData().get("customerId") != null) {

                                customerId = document.getData().get("customerId").toString();
                            }

                            Order order = null;

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

                            if (!customerId.isEmpty() && (accountId == courierId || accountId == Integer.parseInt(customerId))) {
                                orders.add(order);
                            }
                        }
                    }
                    listener.displayOrders(orders);
                }
            }
        });
    }
}

//

