package com.example.bishopwalker.shopping_cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Display extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String username = getIntent().getStringExtra("Username");


        TextView tv = (TextView)findViewById(R.id.TVusername);
        tv.setText(username);


    Button btNext=(Button)findViewById(R.id.btContinue);

        btNext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent int1= new Intent(Display.this, MainActivity.class);
            startActivity(int1);
        }
    });
}
}
