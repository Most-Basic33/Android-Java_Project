package com.example.bishopwalker.shopping_cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Model.Product;

import static com.example.bishopwalker.shopping_cart.Sample.DataProvider.dataItemList;

public class MainActivity extends AppCompatActivity {
//TextView textView;
private TextView selections;

//private final List<String>itemNames=new ArrayList<>();
private final List<Product>products= dataItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);

                startActivity(intent);
                Snackbar.make(view, "Click da Google to choose your Location", Snackbar.LENGTH_LONG)
                    .setAction("Action", null) .show();
            }
        });



         Collections.sort(dataItemList, new Comparator<Product>() {
            @Override
           public int compare(Product o1, Product o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });


//for(Product item: products){
    //textView.append(item.getItemName() + "\n");
  //      itemNames.add(item.getItemName());
//}
//Collections.sort(itemNames);


        Toast.makeText(this,"Another Project by the UOP Team C MLB402.",Toast.LENGTH_SHORT).show();
selections=findViewById(R.id.Selections);
String text="Welcome To FROM KITCHEN 2 CRIB's Online Food Services";

selections.setText(text);
        ProductListAdapter  adapter = new ProductListAdapter (this,products);
       RecyclerView listView = findViewById(R.id.rvList);
        listView.setAdapter(adapter);

    }

}
