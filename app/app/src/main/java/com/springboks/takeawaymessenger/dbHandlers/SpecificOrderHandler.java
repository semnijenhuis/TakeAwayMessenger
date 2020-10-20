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

public class SpecificOrderHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;

    public interface onSpecificOrderReceivedListener{
        public void displayOrder(Order order);
    }
    private SpecificOrderHandler.onSpecificOrderReceivedListener listener;

    public SpecificOrderHandler(int OrderId) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener= null;
        getOrder(OrderId);
    }

    public void setOnSpecificOrderReceivedListener(SpecificOrderHandler.onSpecificOrderReceivedListener listener){
        this.listener = listener;
    }




    public void getOrder(int orderId) {
        CollectionReference ordersRef = db.collection("orders");

        ordersRef.whereArrayContains("orderId",orderId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Order order = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            order =document.toObject(Order.class);

                            }
                        }


                    if(order != null){
                        listener.displayOrder(order);
                    }
                } else {
                    Log.d("DBHandlerG", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
