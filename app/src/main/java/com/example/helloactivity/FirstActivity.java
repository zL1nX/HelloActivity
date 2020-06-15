package com.example.helloactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class FirstActivity extends AppCompatActivity {

    public int pwdLen = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first);
        Button aesBtn = findViewById(R.id.button);
        Button aesKeyGenBtn = findViewById(R.id.button3);
        Button sm4Btn = findViewById(R.id.button2);
        Button sm4KeyGenBtn = findViewById(R.id.button4);

        final EditText aesKey = findViewById(R.id.editTextTextPersonName);
        final EditText sm4Key = findViewById(R.id.editTextTextPersonName3);


        aesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String key = aesKey.getText().toString().trim();
                Intent it = setKeyIntent(key, "aes");
                if(it != null){
                    Toast.makeText(FirstActivity.this, "We will run AES", Toast.LENGTH_LONG).show();
                    startActivity(it); // call the Second Activity
                }
            }
        });

        sm4Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String key = sm4Key.getText().toString();
                Intent it = setKeyIntent(key, "sm4");
                if(it != null){
                    Toast.makeText(FirstActivity.this, "We will run SM4", Toast.LENGTH_LONG).show();
                    startActivity(it); // call the Second Activity
                }
            }
        });

        aesKeyGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rpwd = getRandomString();
                aesKey.setText(rpwd);
            }
        });

        sm4KeyGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rpwd = getRandomString();
                sm4Key.setText(rpwd);
            }
        });
    }




    public Intent setKeyIntent(String key, String type){
        Intent it;
        if(key.length() == 0){
            // make an alert
            Toast.makeText(FirstActivity.this, "Please input your Key", Toast.LENGTH_LONG).show();
            it = null;
        }
        else{
            it = new Intent(FirstActivity.this, SecondActivity.class);
            it.putExtra("type", type);
            it.putExtra("key", key);
        }
        return it;
    }

    public String getRandomString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<pwdLen;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}