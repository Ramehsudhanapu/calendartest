package com.richlabz.myfunctionhall.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.HLMSolutions.MyfunctionHall.R;
import com.richlabz.myfunctionhall.activity.PostBidActivity;
import com.richlabz.myfunctionhall.datasource.CategoryNonveg;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

;

/**
 * Created by Ramesh on 3/30/2015.
 */
public class PostingBiddingBreakfastNonVegAdapter extends RecyclerView.Adapter<PostingBiddingBreakfastNonVegAdapter.Service> {
    Context context;
    List<CategoryNonveg> catergorynonvegitemses;
    ArrayList<String> savingnonvegitems = new ArrayList<>();
    ArrayList<String> nonvegitemsname = new ArrayList<>();
    PostBidActivity postBidActivity;
    private int currentSelectedPosition = -1;
    private Matcher m;
    private String checkvalue;
    public PostingBiddingBreakfastNonVegAdapter(PostBidActivity postBidActivity, Context applicationContext, List<CategoryNonveg> catergorynonvegitemses) {
        this.context = applicationContext;
        this.catergorynonvegitemses = catergorynonvegitemses;
        this.postBidActivity = postBidActivity;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public OnItemClickListener mListener;
    @Override
    public Service onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postbidnonveg, viewGroup, false);
        Service Sh = new Service(v);
        v.setTag(i);
        return Sh;
    }
    @Override
    public void onBindViewHolder(PostingBiddingBreakfastNonVegAdapter.Service holder, int position) {
        CategoryNonveg categoryNonveg = catergorynonvegitemses.get(position);
        String id =categoryNonveg.getId();
        String name=categoryNonveg.getItem_name();
        StringBuffer stringbf = new StringBuffer();
        String foodname=categoryNonveg.getItem_name();
        if ( foodname !=null) {
            m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher( foodname );
        }
        while (m.find()) {
            m.appendReplacement(stringbf, m.group(1).toUpperCase() + m.group(2).toLowerCase());
        }
        holder.foodname.setText(m.appendTail(stringbf).toString());
        if (categoryNonveg.isvalidate()) {
            categoryNonveg.setIsvalidate(true);
            holder.foodname.setTextColor(Color.RED);
            if(checkvalue.equals("1")) {
                savingnonvegitems.add(id);
                nonvegitemsname.add(name);
                checkvalue="0";
                postBidActivity.setNonvegitems(savingnonvegitems, nonvegitemsname);

            }

        } else {
            categoryNonveg.setIsvalidate(false);
            holder.foodname.setTextColor(R.color.itemscolor);
            savingnonvegitems.remove(id);
            nonvegitemsname.remove(name);
            postBidActivity.setNonvegitems(savingnonvegitems,nonvegitemsname);
        }
    }
    @Override
    public int getItemCount() {
        return catergorynonvegitemses.size();
    }
    public class Service extends RecyclerView.ViewHolder {
        @Bind(R.id.vegname)
        TextView foodname;
        @Bind(R.id.veglayout)
        LinearLayout linearLayout;
        public Service(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           foodname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentSelectedPosition=getAdapterPosition();
                    if (currentSelectedPosition!=-1) {
                        checkvalue="1";
                       CategoryNonveg catergorynonvegitems  =catergorynonvegitemses.get(currentSelectedPosition);
                        catergorynonvegitems.setIsvalidate(!catergorynonvegitems.isvalidate());
                        catergorynonvegitemses.set(currentSelectedPosition,catergorynonvegitems);
                        notifyItemChanged(currentSelectedPosition);
                    }
                }
            });
        }


    }
}


