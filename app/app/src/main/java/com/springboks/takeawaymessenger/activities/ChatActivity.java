package com.springboks.takeawaymessenger.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.firestore.FirebaseFirestore;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private EditText userInput;
    private RecyclerView recyclerView;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private DatabaseReference ref;
    private Order currentOrder;
    public int orderId;
    public int userId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userInput = findViewById(R.id.chat_user_input);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", -1);
        userId = intent.getIntExtra("userId", -1);

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
                                recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
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

    public void sendMessageOnClick(View view) {
        Map<String, Object> message = new HashMap<>();

        message.put("body", userInput.getText().toString());

        //if logged in by order number onlygit
        if (userId != -1){
            message.put("senderId", userId);
        }
        else {
            message.put("senderId", -1);
        }

        message.put("orderId", orderId);


        db.collection("messages").document().set(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("luc", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("luc", "Error writing document", e);
                    }
                });
        recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
        userInput.setText("");
        closeKeyboard();
    }

    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    public void chatFinish(View view) {
        finish();
    }
}


