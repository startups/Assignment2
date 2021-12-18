package com.shop.onlineapp;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

public class ShoppingItemList extends ArrayAdapter<String> {
    private String[] categoryNames;
    private String[] itemNames;
    private Integer[] gadgetImageid;
    private Double[] prices;
    private Activity currentContext;

    public ShoppingItemList(Activity context, String[] countryNames, String[] capitalNames, Integer[] imageid, Double prices[]) {
        super(context, R.layout.shopping_item_activity, countryNames);
        this.currentContext = context;
        this.categoryNames = countryNames;
        this.itemNames = capitalNames;
        this.prices = prices;
        this.gadgetImageid = imageid;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parentGroup) {
        View currentRow=currentView;
        LayoutInflater layInflater = currentContext.getLayoutInflater();
        if(currentView==null)
            currentRow = layInflater.inflate(R.layout.shopping_item_activity, null, true);
        TextView categoryLabelView = (TextView) currentRow.findViewById(R.id.textViewCategory);
        TextView itemLabelView = (TextView) currentRow.findViewById(R.id.textViewItem);
        TextView itemPriceLabel = (TextView) currentRow.findViewById(R.id.itemPriceLabel);
        ImageView itemImageFlag = (ImageView) currentRow.findViewById(R.id.itemImageFlag);
        Button addItemButton = (Button) currentRow.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingItem newItem = new ShoppingItem();
                newItem.category = categoryNames[position];
                newItem.name = itemNames[position];
                newItem.price = prices[position];
                newItem.count = 1;
                ShoppingActivity.cartItems.add(newItem);
                Snackbar.make(addItemButton, "Successfully added to cart!!!", Snackbar.LENGTH_LONG).show();
            }
            });
        categoryLabelView.setText(categoryNames[position]);
        itemLabelView.setText(itemNames[position]);
        itemImageFlag.setImageResource(gadgetImageid[position]);
        itemPriceLabel.setText(String.valueOf(prices[position]) + "$");
        return  currentRow;
    }
}
