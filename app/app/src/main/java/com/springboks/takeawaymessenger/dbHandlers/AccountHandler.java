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
import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.model.User;

import java.util.ArrayList;
import java.util.List;

public class AccountHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;

    public interface onAccountsReceivedListener{
        public void displayAccounts(List<User> accounts);
    }
    private AccountHandler.onAccountsReceivedListener listener;

    public AccountHandler() {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener= null;
        getAccounts();
    }

    public void setOnAccountsReceivedListener(AccountHandler.onAccountsReceivedListener listener){
        this.listener = listener;
    }




    public void getAccounts() {
        CollectionReference ordersRef = db.collection("users");

        ordersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<User> accounts = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            int userId =Integer.parseInt( document.getData().get("userId").toString());
                            String firstName = document.getData().get("firstName").toString();
                            String lastName = document.getData().get("lastName").toString();
                            String userName = document.getData().get("userName").toString();
                            String password = document.getData().get("password").toString();
                            boolean isCourier = Boolean.parseBoolean( document.getData().get("isCourier").toString());

                            if(isCourier){
                                Courier courier = new Courier(userId,firstName,lastName,userName,password);
                                accounts.add(courier);
                            } else {
                                String address = document.getData().get("address").toString();
                                Customer customer = new Customer(userId,firstName,lastName,userName,password,address);
                                accounts.add(customer);
                            }
                        }

                        Log.d("DBHandlerG", document.getId() + " => " + document.getData());
                    }
                    listener.displayAccounts(accounts);
                } else {
                    Log.d("DBHandlerG", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}


