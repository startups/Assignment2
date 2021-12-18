package com.shop.onlineapp;


import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.app.ListActivity;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
import java.util.ArrayList;

public class ShoppingActivity extends ListActivity {
    private ListView shoppingListView;
    public static ArrayList<ShoppingItem> cartItems = new ArrayList<ShoppingItem>();
    private String categories[] = {
            "Laptop",
            "Mobile",
            "Tablet",
            "Headphone",
            "IPad"
    };

    private String itemNames[] = {
            "Dell Vostro 2420",
            "Samsung Galaxy A5",
            "Lenovo A series",
            "IBall headphone",
            "Apple 3rd gen"
    };

    private Integer imageid[] = {
            R.drawable.laptop,
            R.drawable.mobile,
            R.drawable.tablet,
            R.drawable.headphone,
            R.drawable.ipad
    };

    private Double prices[] = {
            700.00,
            300.00,
            400.00,
            150.00,
            600.00
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_activity);
        TextView headerTextView = new TextView(this);
        headerTextView.setTypeface(Typeface.DEFAULT_BOLD);
        headerTextView.setTextSize(20);
        headerTextView.setText("Shop from below list");
        ListView shListView=(ListView)findViewById(android.R.id.list);
        shListView.addHeaderView(headerTextView);
        ShoppingItemList shippingItemsList = new ShoppingItemList(this, categories, itemNames, imageid, prices);
        shListView.setAdapter(shippingItemsList);

        Button checkOutButton = (Button) findViewById(R.id.checkoutButton);
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShoppingActivity.cartItems.size()<=0) {
                    Toast.makeText(getApplicationContext(),"Please add items to cart to checkout", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent checkoutIntent=new Intent(ShoppingActivity.this,CheckoutActivity.class);
                startActivity(checkoutIntent);
                finish();
            }
        });

        Button logOutButton = (Button) findViewById(R.id.logoutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItems.clear();
                Intent logInIntent=new Intent(ShoppingActivity.this,ShopLoginActivity.class);
                startActivity(logInIntent);
                finish();
            }
        });
    }
}