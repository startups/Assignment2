package com.shop.onlineapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    ListView itemsPurchasedList;

    public double getTotalAmount() {
        ArrayList<ShoppingItem> items = ShoppingActivity.cartItems;
        double total = 0;
        for (int i =0 ; i <items.size(); i++) {
            ShoppingItem sItem = items.get(i);
            total += sItem.price;
        }
        return total;
    }

    @Override
    protected void onCreate(Bundle currentState)
    {
        super.onCreate(currentState);
        setContentView(R.layout.checkout_activity);
        itemsPurchasedList = findViewById(R.id.checkout_list);
        TextView amountLabelView =(TextView) findViewById(R.id.amountLabel);
        double totalPrice = getTotalAmount();
        amountLabelView.setText(String.valueOf(totalPrice));
        ArrayList<ShoppingItem> items = ShoppingActivity.cartItems;
        String[] strItems = new String[items.size()];
        for (int i =0 ; i <items.size(); i++) {
            String item = "";
            ShoppingItem sItem = items.get(i);
            item = String.valueOf(i +1) + ". " + items.get(i).name + " X " +
            String.valueOf(sItem.count)+" " + String.valueOf(sItem.price) + "$";
            strItems[i] = item;
        }
        ArrayAdapter<String> listArrayAdapter;
        listArrayAdapter  = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                strItems);
        itemsPurchasedList.setAdapter(listArrayAdapter);
        Button placeOrderButton = (Button) findViewById(R.id.placeOrderButton);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Your order is placed successfully", Toast.LENGTH_SHORT).show();
                ShoppingActivity.cartItems.clear();
                Intent shopActivity = new Intent(CheckoutActivity.this, ShoppingActivity.class);
                startActivity(shopActivity);
                finish();
            }
        });

        Button cancelOrderButon = (Button) findViewById(R.id.cancelOrderButton);
        cancelOrderButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Your order is cancelled", Toast.LENGTH_SHORT).show();
                ShoppingActivity.cartItems.clear();
                Intent shopActivity=new Intent(CheckoutActivity.this,ShoppingActivity.class);
                startActivity(shopActivity);
                finish();
            }
        });
    }
}