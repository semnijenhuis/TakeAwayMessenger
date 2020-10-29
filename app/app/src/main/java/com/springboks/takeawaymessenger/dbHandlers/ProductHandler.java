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
import com.springboks.takeawaymessenger.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductHandler {
    private FirebaseFirestore db;
    private DatabaseReference rootRef;
    private Order specificOrder;

    public interface onProductsReceivedListener{
        public void displayProducts(List<Product> products);
    }
    private onProductsReceivedListener listener;

    public ProductHandler(Order order) {
        db = FirebaseFirestore.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        this.listener= null;
        getProducts(order);
    }

    public void setOnProductsReceivedListener(onProductsReceivedListener listener){
        this.listener = listener;
    }

    public void getProducts(Order order) {
        CollectionReference productRef = db.collection("products");
        specificOrder = order;

        productRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> products = new ArrayList<>();
                    ArrayList<Product> orderProducts = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getData().isEmpty()) {
                            double price =Double.parseDouble( document.getData().get("price").toString());
                            int productId = Integer.parseInt(document.getData().get("productId").toString());
                            String name = document.getData().get("name").toString();

                            Product product = new Product(name,price,productId);
                            products.add(product);
                        }
                    }
                    for (int i = 0; i < specificOrder.getProductIds().size() ; i++) {
                        for (Product prod: products
                        ) {
                            if (specificOrder.getProductIds().get(i) == prod.getProductId()){
                                orderProducts.add(prod);
                            }
                        }
                    }
                    listener.displayProducts(orderProducts);
                }
            }
        });
    }
}
