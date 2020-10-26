package com.springboks.takeawaymessenger.dbHandlers;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.springboks.takeawaymessenger.model.Message;
import com.springboks.takeawaymessenger.model.Order;


import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    private Order thisOrder;

    public interface onMessagesReceivedListener{
        public void displayMessages(List<Message> messages);
    }
    private MessageHandler.onMessagesReceivedListener listener;

    public MessageHandler(int userId, Order order) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference("messages");
        this.listener= null;
        getMessages(userId, order);
    }

    public void setOnMessagesReceivedListener(MessageHandler.onMessagesReceivedListener listener){
        this.listener = listener;
    }




    public void getMessages(final int userId, final Order order) {
        final ArrayList<Message> messages = new ArrayList<>();
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()
                ) {
                    System.out.println(ds.toString());
                    int dbOrderId = Integer.parseInt(snapshot.child("orderId").getValue().toString());
                    System.out.println( "AAAAAAAAAAAAAAAAA why is this " +dbOrderId);
                    if (dbOrderId == order.getOrderID()){
                        String body = ds.child("body").toString();
                        int dbUserId =Integer.parseInt(ds.child("senderId").getValue().toString());

                        Message message;
                        if(userId != dbUserId) {
                            message = new Message(body,false);
                        } else {
                            message = new Message(body,true);
                        }
                        messages.add(message);
                    }
                }
                listener.displayMessages(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
