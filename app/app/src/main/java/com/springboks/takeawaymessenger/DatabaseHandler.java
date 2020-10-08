package com.springboks.takeawaymessenger;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;

    public interface onOrdersReceivedListener{
        public void displayOrders(List<Order> orders);
    }
    private onOrdersReceivedListener listener;

    public DatabaseHandler(int id) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener= null;
        getOrders(id);
    }

    public void setOnOrdersReceivedListener(onOrdersReceivedListener listener){
        this.listener = listener;
    }




    public void getOrders(int accountId) {
        CollectionReference ordersRef = db.collection("orders");

        ordersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Order> orders = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            int orderId =Integer.parseInt( document.getData().get("orderId").toString());
                            String restaurantName = document.getData().get("restaurantName").toString();
                            String date = document.getData().get("date").toString();
                            String selectedDeliveryTime = document.getData().get("selectedDeliveryTime").toString();
                            String actualDeliveryTime = document.getData().get("actualDeliveryTime").toString();
                            boolean open = Boolean.parseBoolean( document.getData().get("open").toString());

                            Order order = new Order( orderId, restaurantName, date, selectedDeliveryTime, actualDeliveryTime, open, 1, 1);
                            orders.add(order);
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
}

//        /

//        }




//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Integer orderId = ds.child("orderId").getValue(Integer.class);
//                    String restaurantName = ds.child("restaurantName").getValue(String.class);
//                    Integer courierId = ds.child("courierId").getValue(Integer.class);
//                    Integer customerId = ds.child("customerId").getValue(Integer.class);
//                    String date = ds.child("date").getValue(String.class);
//                    String selectedDeliverTime= ds.child("selectedDeliveryTime").getValue(String.class);
//                    String actualDeliverTime= ds.child("actualDeliveryTime").getValue(String.class);
//                    Boolean open = ds.child("open").getValue(Boolean.class);
//                    Order order = new Order(orderId,restaurantName,date,selectedDeliverTime,actualDeliverTime,open,customerId,courierId);
//
//                    System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ restaurantName);
//                    orders.add(order);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        return orders;
//    }

//    public List<Order> getProducts(int orderId){
//        DatabaseReference tripsRef = rootRef.child("products");
//        final List<Product> products = new ArrayList<Product>();
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Integer orderId = ds.child("orderId").getValue(Integer.class);
//                    String restaurantName = ds.child("restaurantName").getValue(String.class);
//                    Integer courierId = ds.child("courierId").getValue(Integer.class);
//                    Integer customerId = ds.child("customerId").getValue(Integer.class);
//                    String date = ds.child("date").getValue(String.class);
//                    String selectedDeliverTime= ds.child("selectedDeliveryTime").getValue(String.class);
//                    String actualDeliverTime= ds.child("actualDeliveryTime").getValue(String.class);
//                    Order order = new Order()
//                    orders.add(time);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        tripsRef.addListenerForSingleValueEvent(valueEventListener);
//        return orders;
//    }
//}

