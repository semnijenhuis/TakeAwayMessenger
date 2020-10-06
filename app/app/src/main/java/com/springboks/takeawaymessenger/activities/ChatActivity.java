package com.springboks.takeawaymessenger.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.springboks.takeawaymessenger.R;
import com.springboks.takeawaymessenger.adapters.MessageAdapter;
import com.springboks.takeawaymessenger.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText userInput;
    private RecyclerView recyclerView;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userInput = findViewById(R.id.chat_user_input);
        recyclerView = findViewById(R.id.conversation);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);
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