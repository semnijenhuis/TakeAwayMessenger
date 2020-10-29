package com.springboks.takeawaymessenger.dbHandlers;

import android.net.sip.SipAudioCall;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.springboks.takeawaymessenger.model.Message;
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.model.User;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MessageHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    private Order thisOrder;
    private AccountHandler accountHandler;
    private Message msg = null;

    public interface onMessagesReceivedListener{
        public void displayMessages(List<Message> messages);
    }
    private MessageHandler.onMessagesReceivedListener listener;


    public MessageHandler(int userId, Order order) {
        db = FirebaseFirestore.getInstance();
        this.listener= null;
        getMessages(userId, order);
        accountHandler = new AccountHandler();
    }


    public void setOnMessagesReceivedListener(MessageHandler.onMessagesReceivedListener listener){
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

                for (DocumentChange dc : value.getDocumentChanges()) {

                    msg = dc.getDocument().toObject(Message.class);

                    if (userId == -1){
                        //loggining by order
                    }
                    else {
                        //loggining by user
                        accountHandler.setOnAccountsReceivedListener(new AccountHandler.onAccountsReceivedListener() {
                            @Override
                            public void displayAccounts(List<User> accounts) {
                                boolean isCourier = accountHandler.isCourierId(msg.getSenderId());
                                Log.i("courier", isCourier + "");

                            }
                        });

                    }

                    if (userId == msg.getSenderId()){
                        msg.setMe(true);
                    }
                    else {
                        msg.setMe(false);
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
        });
    }
}

//if no user id = loggining by order
// if sender id = courier id == all msgs that are not froum courier is me

