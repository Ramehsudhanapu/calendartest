package com.richlabz.smileyserve.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richlabz.smileyserve.R;
import com.richlabz.smileyserve.activity.CartActivity;
import com.richlabz.smileyserve.datasource.FagResult;
import com.richlabz.smileyserve.datasource.GetCartResult;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ramesh on 13/10/2015.
 */
public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.Service> {
    private static final String LOG = FaqAdapter.class.getSimpleName();
    private Context context;
    List<FagResult> fagResults;
    private int lastcheckedposition = -1;
    private int pos;
    public FaqAdapter(FragmentActivity activity, List<FagResult> fagResults) {
        this.context = activity;
        this.fagResults = fagResults;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    private OnItemClickListener mListener;
    @Override
    public Service onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faqlistfragment, viewGroup, false);
        Service Sh = new Service(v);
        v.setTag(i);
        return Sh;
    }
    @Override
    public void onBindViewHolder(Service service, int position) {
        FagResult fagResult = fagResults.get(position);
        service.faqquestion.setText(fagResult.getQuestion());
        service.faqAnswer.setText(fagResult.getAsnswer());
        if (position == lastcheckedposition) {
            service.faqAnswerlayout.setVisibility(View.VISIBLE);
        } else {
            service.faqAnswerlayout.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return fagResults.size()>0?fagResults.size():0;
    }
    public class Service extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.faqQuestion)
        TextView faqquestion;
        @Bind(R.id.faqAnswerlayout)
        LinearLayout faqAnswerlayout;
        @Bind(R.id.faqanswer)
        TextView faqAnswer;
        boolean isvalidate = false;
        public Service(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @OnClick(R.id.faqQuestion) void submiquestion() {
            if(!isvalidate) {
                 notifyItemRangeChanged(0, fagResults.size());
                lastcheckedposition = getAdapterPosition();
                pos=getAdapterPosition();
                isvalidate=true;
            }
            else
            {
                notifyItemRangeChanged(0, fagResults.size());
                lastcheckedposition = getAdapterPosition();
                lastcheckedposition=-1;
                pos=-2;
                isvalidate=false;
            }
        }
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}


