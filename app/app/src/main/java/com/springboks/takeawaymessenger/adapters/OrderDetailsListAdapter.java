package com.springboks.takeawaymessenger.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.springboks.takeawaymessenger.model.Product;
import com.springboks.takeawaymessenger.R;

import java.util.List;

public class OrderDetailsListAdapter extends ArrayAdapter {
    LayoutInflater layoutInflater;
    List<Product> productList;
    private TextView quantity;
    private TextView description;
    private TextView price;

    public OrderDetailsListAdapter(Context context, List objects) {
        super(context, R.layout.order_details_list_item, objects);
        layoutInflater = LayoutInflater.from(context);
        productList = objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        Product product = productList.get(position);

        if (convertView == null) {
            convertView=layoutInflater.inflate(R.layout.order_details_list_item, parent, false);
        }

        if (convertView != null) {
            quantity = convertView.findViewById(R.id.list_quantity);
            String temp = Integer.toString(product.getQuantity());
            quantity.setText(temp + "x ");

            description = convertView.findViewById(R.id.list_description);
            description.setText(product.getDescription());

            price = convertView.findViewById(R.id.list_price);
            String temp1 = Double.toString(product.getPrice());
            price.setText(temp1+",-");
        }

        return convertView;
    }
}
