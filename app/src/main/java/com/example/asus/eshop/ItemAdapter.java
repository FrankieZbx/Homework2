package com.example.asus.eshop;

/**
 *
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 *
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewholder>{
    private OnItemClickListener onItemClick;
    private LayoutInflater layoutInflater;
    private List<Item> itemList;
    private Context context;
    public ItemAdapter(Context context, List<Item> itemList) {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.itemList = itemList;
    }
    @Override
    public ItemAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewholder(layoutInflater.inflate(R.layout.item,parent,false));
    }
    @Override
    public void onBindViewHolder(final ItemAdapter.MyViewholder holder, final int position) {
        Glide.with(context).load(itemList.get(position).getUrl()).into(holder.imageView);
        holder.item_name.setText(itemList.get(position).getItem_name());
        holder.count.setText(String.valueOf(itemList.get(position).getCount()));
        holder.price.setText("$ "+itemList.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("url",itemList.get(position).getUrl());
                bundle.putString("item_name",itemList.get(position).getItem_name());
                bundle.putString("discribe",itemList.get(position).getDiscribe());
                bundle.putString("price",itemList.get(position).getPrice());
                intent.putExtra("key",bundle);
                context.startActivity(intent);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k=itemList.get(position).getCount();
                k++;
                itemList.get(position).setCount(k);
                holder.count.setText(String.valueOf(k));
                onItemClick.OnItemClick(position,itemList);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k=itemList.get(position).getCount();
                if(k>0)
                {
                    k--;
                }
                itemList.get(position).setCount(k);
                holder.count.setText(String.valueOf(k));
                onItemClick.OnItemClick(position,itemList);
            }
        });


    }
    @Override
    public int getItemCount() {
        return itemList !=null? itemList.size():0;
    }
    public   class MyViewholder extends  RecyclerView.ViewHolder
    {
        Button add=(Button)itemView.findViewById(R.id.add);
        Button delete=(Button)itemView.findViewById(R.id.delete);
        TextView count=(TextView)itemView.findViewById(R.id.count);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.image);
        TextView item_name=(TextView)itemView.findViewById(R.id.item_name);
        TextView price=(TextView)itemView.findViewById(R.id.price);
        public MyViewholder(View itemView) {
            super(itemView);
        }

    }
    public interface OnItemClickListener {
        void OnItemClick(int x,List<Item> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

}
