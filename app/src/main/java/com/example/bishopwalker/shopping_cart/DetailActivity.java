package com.example.bishopwalker.shopping_cart;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bishopwalker.shopping_cart.Sample.DataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import Model.Product;

public class DetailActivity extends AppCompatActivity {
static final List<Product>cartchoice=ProductListAdapter.cartChoice;
    public static final String ITEM_KEY = "item_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Product item = Objects.requireNonNull(getIntent().getExtras()).getParcelable(ProductListAdapter.ITEM_KEY);
        if (item == null) {
            throw new AssertionError("Null data item received!");
        }

        TextView tvName = findViewById(R.id.tvItemName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvDescription = findViewById(R.id.tvDescription);
        ImageView itemImage = findViewById(R.id.itemImage);
        Button addCArt= findViewById(R.id.btCart);
        addCArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,ShoppingActivity.class);
                     intent.putExtra(ITEM_KEY,item);
                DataProvider.addShoppingItem(item);
                cartchoice.add(item);
                startActivity(intent);
            }
        });
        tvName.setText(item.getItemName());
        tvDescription.setText(item.getDescription());

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        tvPrice.setText(nf.format(item.getPrice()));

        InputStream inputStream = null;
        try {
            String imageFile = item.getImage();
            inputStream = getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            itemImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}