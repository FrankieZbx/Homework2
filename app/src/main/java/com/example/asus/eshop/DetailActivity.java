package com.example.asus.eshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView item_name;
    private TextView discribe;
    private TextView price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("key");
        imageView=(ImageView)findViewById(R.id.image);
        price=(TextView)findViewById(R.id.price);
        item_name=(TextView)findViewById(R.id.item_name);
        discribe=(TextView)findViewById(R.id.discribe);
        Glide.with(this).load(bundle.getString("url")).into(imageView);
        item_name.setText(bundle.getString("item_name"));
        discribe.setText(bundle.getString("discribe"));
        price.setText("$ "+bundle.getString("price"));
    }
}
