package com.springboks.takeawaymessenger.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.springboks.takeawaymessenger.R;
import com.springboks.takeawaymessenger.adapters.MessageAdapter;
import com.springboks.takeawaymessenger.dbHandlers.MessageHandler;
import com.springboks.takeawaymessenger.dbHandlers.SpecificOrderHandler;
import com.springboks.takeawaymessenger.model.Message;
import com.springboks.takeawaymessenger.model.Order;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText userInput;
    private RecyclerView recyclerView;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    DatabaseReference ref;
    Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userInput = findViewById(R.id.chat_user_input);
        Intent intent = getIntent();
        final int orderId = intent.getIntExtra("orderId",-1);
        final int userId = intent.getIntExtra("userId", -1);

        System.out.println( "userId = " + userId + ", orderId = "+ orderId);

        SpecificOrderHandler soh = new SpecificOrderHandler(orderId);
        soh.setOnSpecificOrderReceivedListener(new SpecificOrderHandler.onSpecificOrderReceivedListener() {
            @Override
            public void displayOrder(Order order) {
                System.out.println( "AAAAAAAAAAAAAA" + order.getName());
                currentOrder = order;

                recyclerView = findViewById(R.id.conversation);
                messageList = new ArrayList<>();

                MessageHandler mh = new MessageHandler(userId,currentOrder);
                mh.setOnMessagesReceivedListener(new MessageHandler.onMessagesReceivedListener() {
                    @Override
                    public void displayMessages(List<Message> messages) {
                        messageAdapter = new MessageAdapter(messages);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(messageAdapter);
                    }
                });


//                MessageHandler mh = new MessageHandler(userId, order);
//                mh.setOnAccountsReceivedListener(new MessageHandler.onMessagesReceivedListener() {
//                    @Override
//                    public void displayMessages(List<Message> sentMessages, List<Message> receivedMessages) {
//                        messageList.addAll(sentMessages);
//                        messageList.addAll(receivedMessages);
//
//
//                    }
//                });

            }
        });

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 if (actionId == EditorInfo.IME_ACTION_SEND) {
                     Message message = new Message(userInput.getText().toString(), true);
                     messageList.add(message);
                     Message message2 = new Message(userInput.getText().toString(), false);
                     messageList.add(message2);
                     messageAdapter.notifyDataSetChanged();
                 }
                 return true;
            }
        });
    }
}