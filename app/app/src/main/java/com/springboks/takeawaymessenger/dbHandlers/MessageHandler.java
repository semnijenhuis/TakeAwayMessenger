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
import com.springboks.takeawaymessenger.model.Courier;
import com.springboks.takeawaymessenger.model.Customer;
import com.springboks.takeawaymessenger.model.Message;
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.model.User;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    private Order thisOrder;

    public interface onMessagesReceivedListener{
        public void displayMessages(List<Message> sentMessages, List<Message> receivedMessages);
    }
    private MessageHandler.onMessagesReceivedListener listener;

    public MessageHandler(int userId, Order order) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener= null;
        getMessages(userId, order);
    }

    public void setOnAccountsReceivedListener(MessageHandler.onMessagesReceivedListener listener){
        this.listener = listener;
    }




    public void getMessages(final int userId, final Order order) {
        CollectionReference ordersRef = db.collection("messages");
        thisOrder =order;

        ordersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Message> sentMessages = new ArrayList<>();
                    List<Message> receivedMessages = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            int senderId =Integer.parseInt( document.getData().get("senderId").toString());
                            String body = document.getData().get("body").toString();
                            int orderId = Integer.parseInt(document.getData().get("orderId").toString());

                            Message message = new Message(senderId,body,orderId);
                            if(message.getSenderId() == userId && thisOrder.getOrderID() == orderId){
                                sentMessages.add(message);
                            } else if (message.getSenderId() != userId && thisOrder.getOrderID() == orderId) {
                                receivedMessages.add(message);
                            }
                        }

                        Log.d("DBHandlerG", document.getId() + " => " + document.getData());
                    }
                    listener.displayMessages(sentMessages,receivedMessages);
                } else {
                    Log.d("DBHandlerG", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
