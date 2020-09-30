package com.springboks.takeawaymessenger;

import android.os.AsyncTask;
import android.util.Log;

import com.springboks.takeawaymessenger.model.Product;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler extends AsyncTask<String,String,String> {

    public ArrayList getData(){
        ArrayList<Product> productsList = new ArrayList<>();

        try {
            String url = "jdbc:postgresql://83.163.112.227:5432/TakeAwayDB";
            String username = "postgres";
            String password = "cheeseking";

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url,username,password);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from Product");
            while(rs.next()){
                String pn = rs.getString("name");
                double pr = rs.getDouble("price");


                Product p = new Product(pn,pr,1);
                productsList.add(p);
            }
            st.close();
            con.close();

            Log.i("MAXI", "getData: " + productsList.get(0).getDescription());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    @Override
    protected String doInBackground(String... strings) {
        getData();
        return "";
    }


}
