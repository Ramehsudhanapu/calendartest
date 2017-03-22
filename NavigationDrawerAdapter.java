package com.richlabz.smileyserve.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.richlabz.smileyserve.R;
import com.richlabz.smileyserve.entities.NavDrawerItem;

import java.util.Collections;
import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    int[] drawericons;
    public NavigationDrawerAdapter(FragmentActivity activity, List<NavDrawerItem> data, int[] drawericons) {
        this.context = activity;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.drawericons = drawericons;
    }
    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.imgdrawer.setImageResource(drawericons[position]);
    }
    @Override
    public int getItemCount() {
        return drawericons.length;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imgdrawer;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            imgdrawer = (ImageView) itemView.findViewById(R.id.drawericon);
        }
    }
}
