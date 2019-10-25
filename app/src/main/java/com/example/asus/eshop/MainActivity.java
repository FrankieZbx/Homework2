package com.example.asus.eshop;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private List<Item>items;
    private Button pay;
    private TextView totalcost;
    private float total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview1);
        pay=(Button)findViewById(R.id.pay);
        totalcost=(TextView)findViewById(R.id.total);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemList=getItemList();
        itemAdapter=new ItemAdapter(this,itemList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int x, List<Item> list) {
                total=0;
                for(int i=0;i<list.size();i++)
                {
                    if(list.get(i).getCount()>0)
                    {
                        float k=Float.parseFloat(list.get(i).getPrice());
                        k=k*list.get(i).getCount();
                        total=total+k;
                    }
                }
                totalcost.setText(String.valueOf(total));
                items=list;
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(total==0)
               {
                   Toast.makeText(MainActivity.this,"your cart is empty",Toast.LENGTH_LONG);
               }
               else {
                   Intent intent=new Intent(MainActivity.this,OrderedActivity.class);
                   Gson gs = new Gson();
                   String listStr = gs.toJson(items);
                   Bundle bundle=new Bundle();
                   bundle.putString("list",listStr);
                   intent.putExtra("key",bundle);
                   startActivity(intent);
               }
            }
        });

    }
    public List<Item> getItemList() {
        ArrayList<Item> list2=new ArrayList<>();
        String itemJSON="";
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("content.json")));
            String line;
            while ((line = bf.readLine()) !=null ) {
                itemJSON+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject=new JSONObject(itemJSON);
            JSONArray myitem=jsonObject.getJSONArray("item");
            int length = myitem.length();
            for (int i=0;i<length;i++)
            {
                Item item=new Item();
                JSONObject o=myitem.getJSONObject(i);
                item.setDiscribe(o.getString("discribe"));
                item.setUrl(o.getString("url"));
                item.setPrice(o.getString("price"));
                item.setItem_name(o.getString("item_name"));
                item.setCount(0);
                list2.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  list2;
    }
}
