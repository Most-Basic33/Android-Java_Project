package com.example.bishopwalker.shopping_cart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onButtonClick(View v)
    {
        if (v.getId() ==R.id.bLogin)
        {
            EditText a = (EditText)findViewById(R.id.TFusername);
            String str = a.getText().toString();
            EditText b = (EditText)findViewById(R.id.TFpassword);
            String pass = b.getText().toString();

            String password = helper.searchPass(str);
            if(pass.equals(password))
            {
                Intent i = new Intent(LoginActivity.this, Display.class);
                i.putExtra("Username",str);
                startActivity(i);
            }
            else
            {
                Toast temp = Toast.makeText(LoginActivity.this , "Username and Password don't match!" , Toast.LENGTH_SHORT);
                temp.show();
            }
        }
        if(v.getId() == R.id.Bsignup)
        {
            Intent i = new Intent(LoginActivity.this, SignUp.class);
            startActivity(i);
        }

    }
}
