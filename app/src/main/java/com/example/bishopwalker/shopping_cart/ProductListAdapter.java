package com.example.bishopwalker.shopping_cart;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bishopwalker.shopping_cart.Sample.DataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Model.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    // public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    private final List<Product> mItems;
    private final Context mContext;

    static final List<Product> cartChoice = new ArrayList<>();//=dataItemList;


    public ProductListAdapter(Context context, List<Product> items) {
        this.mContext = context;
        this.mItems = items;

    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        final Product item = mItems.get(position);

        try {
            holder.tvName.setText(item.getItemName());
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You selected " + item.getItemName(), Toast.LENGTH_SHORT).show();
                // String itemID= item.getItemId();
                Intent intent = new Intent(mContext, DetailActivity.class);

                intent.putExtra(ITEM_KEY, item);
                ProductListAdapter.this.mContext.startActivity(intent);open terminlope
                //mContext.startActivity(intent1);
            }
        });
        holder.myView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getItemName(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(mContext, ShoppingActivity.class);
                intent1.putExtra(ITEM_KEY, item);
                DataProvider.addShoppingItem(item);
                Intent intent3 = new Intent(mContext, MainActivity.class);
                intent3.putExtra(ITEM_KEY, item);
                //Special Code attempt
                cartChoice.add(item);

                mContext.startActivity(intent3);
                mContext.startActivity(intent1);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvName;
        final ImageView imageView;
        final View myView;

        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.itemNameText);
            imageView = itemView.findViewById(R.id.imageView);
            myView = itemView;
        }
    }



    }


