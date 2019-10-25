package com.example.asus.eshop;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderedActivity extends AppCompatActivity {
  private List<Item>itemList;
    private TextView totalcost;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private float total;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview1);
        submit=(Button)findViewById(R.id.submit);
        totalcost=(TextView)findViewById(R.id.total);
        itemList=new ArrayList<>();
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("key");
        if(bundle.getString("list")!=null)
        {
            Gson gson=new Gson();
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            final ArrayList<Item> items = gson.fromJson(bundle.getString("list"), type);
            for(int i=0;i<items.size();i++)
            {
                if(items.get(i).getCount()>0)
                {
                    itemList.add(items.get(i));
                }
            }
            for(int i=0;i<itemList.size();i++)
            {
                if(itemList.get(i).getCount()>0)
                {
                    float k=Float.parseFloat(itemList.get(i).getPrice());
                    k=k*itemList.get(i).getCount();
                    total=total+k;
                }
            }
            totalcost.setText(String.valueOf(total));
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
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
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(OrderedActivity.this);
                    alterDiaglog.setTitle("Submited");
                    alterDiaglog.setMessage("Successful!");
                    alterDiaglog.show();
                }
            });
        }

    }
}
