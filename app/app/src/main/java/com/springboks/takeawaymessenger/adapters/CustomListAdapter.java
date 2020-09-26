package com.springboks.takeawaymessenger.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.springboks.takeawaymessenger.model.Order;
import com.springboks.takeawaymessenger.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter {

    LayoutInflater layoutInflater;
    List<Order> orderList;
    private ImageView imageView;
    private TextView orderId;
    private TextView name;
    private TextView addres;
    private TextView deliveryTime;

    public CustomListAdapter(Context context, List objects) {
        super(context, R.layout.order_list_item, objects);
        layoutInflater = LayoutInflater.from(context);
        orderList = objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        Order order = orderList.get(position);

        if (convertView == null) {
            convertView=layoutInflater.inflate(R.layout.order_list_item, parent, false);
        }

        if (convertView != null) {
            orderId = convertView.findViewById(R.id.orderNrID);
            String temp = Integer.toString(order.getOrderID());
            orderId.setText(temp);

            name = convertView.findViewById(R.id.orderNameID);
            name.setText(order.getName());

            addres = convertView.findViewById(R.id.orderAddresID);
            addres.setText(order.getCustomer().getAddress());

            deliveryTime = convertView.findViewById(R.id.orderDeliverytimeID);
            deliveryTime.setText("Delivery Time: " + order.getActualDeliveryTime().toString());

            imageView = convertView.findViewById(R.id.orderImageID);

            InputStream inputStream = null;
            try {
                String imageFile = order.getImageFile();
                Log.i("Yoo", imageFile);

                inputStream = getContext().getAssets().open(imageFile);
                Drawable d = Drawable.createFromStream(inputStream, null);
                imageView.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return convertView;
    }
}
