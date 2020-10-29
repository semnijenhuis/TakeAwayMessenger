package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import com.springboks.takeawaymessenger.R;
import com.springboks.takeawaymessenger.adapters.MessageAdapter;
import com.springboks.takeawaymessenger.dbHandlers.AccountHandler;
import com.springboks.takeawaymessenger.dbHandlers.MessageHandler;
import com.springboks.takeawaymessenger.dbHandlers.SpecificOrderHandler;
import com.springboks.takeawaymessenger.model.Courier;
import com.springboks.takeawaymessenger.model.Message;
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText userInput;
    private RecyclerView recyclerView;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private DatabaseReference ref;
    private Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userInput = findViewById(R.id.chat_user_input);
        Intent intent = getIntent();
        final int orderId = intent.getIntExtra("orderId", -1);
        final int userId = intent.getIntExtra("userId", -1);

        System.out.println("userId = " + userId + ", orderId = " + orderId);

        SpecificOrderHandler soh = new SpecificOrderHandler(orderId);

        soh.setOnSpecificOrderReceivedListener(new SpecificOrderHandler.onSpecificOrderReceivedListener() {
            @Override
            public void displayOrder(Order order) {

                currentOrder = order;

                recyclerView = findViewById(R.id.conversation);
                AccountHandler accountHandler = new AccountHandler();

                accountHandler.setOnAccountsReceivedListener(new AccountHandler.onAccountsReceivedListener() {
                    @Override
                    public void displayAccounts(List<User> accounts) {

                        ArrayList<Integer> courierIds = new ArrayList<>();
                        for (User user : accounts) {
                            if (user instanceof Courier) {
                                courierIds.add(user.getUserID());
                            }
                        }
                        messageList = new ArrayList<>();

                        MessageHandler mh = new MessageHandler(userId, currentOrder, courierIds);

                        mh.setOnMessagesReceivedListener(new MessageHandler.onMessagesReceivedListener() {
                            @Override
                            public void displayMessages(List<Message> messages) {
                                messageAdapter = new MessageAdapter(messages);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(messageAdapter);
                            }
                        });
                    }
                });
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