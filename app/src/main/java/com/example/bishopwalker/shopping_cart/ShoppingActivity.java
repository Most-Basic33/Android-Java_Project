package com.example.bishopwalker.shopping_cart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import Model.Product;

public class ShoppingActivity extends AppCompatActivity {
String address;
double latitude,longitude;

    private final List<Product> products = ProductListAdapter.cartChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            address=extras.getString("Address");
             latitude = extras.getDouble("Latitude");
             longitude = extras.getDouble("Longitude");
            TextView  addressview=findViewById(R.id.addressView);
            addressview.setText(address);
            TextView latlngView=findViewById(R.id.LatLngView);
            latlngView.setText("Latitude: " + latitude + ", Longitude: " + longitude);
        }

        Product item = Objects.requireNonNull(getIntent().getExtras()).getParcelable(ProductListAdapter.ITEM_KEY);
        if (item == null) {
           // throw new AssertionError("Null data item received!");
        }

        final ListAdapter listAdap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);

        ListView myList = findViewById(R.id.finalList);
        myList.setAdapter(listAdap);



Button returnButton=findViewById(R.id.returnButton);
returnButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent =new Intent(ShoppingActivity.this,MainActivity.class);
        startActivity(intent);
    }
});

    }
}
