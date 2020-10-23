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
import java.util.List;
import java.util.Map;

//public class SpecificOrderHandler {
//    private FirebaseFirestore db;
//    private DatabaseReference rootRef;
//
//    public interface onSpecificOrderReceivedListener{
//        public void displayOrder(Order order);
//    }
//    private SpecificOrderHandler.onSpecificOrderReceivedListener listener;
//
//    public SpecificOrderHandler(int OrderId) {
//        db = FirebaseFirestore.getInstance();
//        rootRef = FirebaseDatabase.getInstance().getReference();
//        this.listener= null;
//        getOrderByOrderNumber(OrderId);
//    }
//
//    public void setOnSpecificOrderReceivedListener(SpecificOrderHandler.onSpecificOrderReceivedListener listener){
//        this.listener = listener;
//    }
//
//
//
//
//    public Map<String, Object> getOrderByOrderNumber(final int orderNumberLoggedIn) {
//        Log.i("luc", "im at get order by order number");
//
//        CollectionReference ordersRef = db.collection("orders");
//        ordersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if (!document.getData().isEmpty()) {
//                            int orderId = Integer.parseInt(document.getData().get("orderId").toString());
//                            if (orderId == orderNumberLoggedIn){
//                                orderData.putAll(document.getData());
//                            }
//                        }
//                    }
//                }
//            }
//        });
//        listener.displayOrder(orderData);
//    }
//}
