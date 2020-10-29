package com.springboks.takeawaymessenger.dbHandlers;

import android.util.Log;
import androidx.annotation.Nullable;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.springboks.takeawaymessenger.model.Message;
import com.springboks.takeawaymessenger.model.Order;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    private Order thisOrder;
    private Message msg = null;
    private List<Integer> courierIds;

    public interface onMessagesReceivedListener {
        public void displayMessages(List<Message> messages);
    }

    private MessageHandler.onMessagesReceivedListener listener;


    public MessageHandler(int userId, Order order, ArrayList<Integer> courierIds) {
        db = FirebaseFirestore.getInstance();
        this.listener = null;
        getMessages(userId, order);
        this.courierIds = courierIds;
    }

    public void setOnMessagesReceivedListener(MessageHandler.onMessagesReceivedListener listener) {
        this.listener = listener;
    }

    public void getMessages(final int userId, final Order order) {
        final List<Message> msgsList = new ArrayList<>();

        //Getting msgs from the order
        CollectionReference allMessage = db.collection("messages");
        Query orderMsgs = allMessage.whereEqualTo("orderId", order.getOrderID());

        //Looping on msgs belonging to orderId
        orderMsgs.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.i("TAG", "listen:error", error);
                    return;
                }

                //looping on all the msgs belonging to order
                for (DocumentChange dc : value.getDocumentChanges()) {
                    msg = dc.getDocument().toObject(Message.class);

                    if ((Long) dc.getDocument().getData().get("senderId") == null) {
                        msg.setSenderId(-1);
                    }

                    //if logged in by user credentials
                    if (userId != -1) {
                        if (userId == msg.getSenderId()) {
                            msg.setMe(true);
                        } else {
                            msg.setMe(false);
                        }
                    }

                    //if logged in by order number
                    else {

                        boolean isCourier = false;

                        if (courierIds.contains(msg.getSenderId())) {
                            msg.setMe(false);
                        } else {
                            msg.setMe(true);

                        }

                        msgsList.add(msg);

                        switch (dc.getType()) {
                            case ADDED:
                                Log.i("msg", "New Msg: " + msg);
                                break;
                            case MODIFIED:
                                Log.i("msg", "Modified Msg: " + msg);
                                break;
                            case REMOVED:
                                Log.i("msg", "Removed Msg: " + msg);
                                break;
                        }
                    }
                    Log.i("orderlist", Arrays.toString(msgsList.toArray()));
                    listener.displayMessages(msgsList);
                }

            }
        });
    }
}


