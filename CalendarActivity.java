package com.richlabz.smileyserve.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.richlabz.smileyserve.Customcalendar.CustomCalendar;
import com.richlabz.smileyserve.Customcalendar.EventData;
import com.richlabz.smileyserve.R;
import com.richlabz.smileyserve.datasource.CalendarResults;
import com.richlabz.smileyserve.datasource.CartResponse;
import com.richlabz.smileyserve.datasource.GETCalendarDates;
import com.richlabz.smileyserve.datasource.SmileyserveDataSource;
import com.richlabz.smileyserve.utilities.SharedPreferenceUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class CalendarActivity extends AppCompatActivity {
    private CustomCalendar customCalendar;
    String arr,count,userid;
    SmileyserveDataSource smileyserveDataSource,smileyserveDataSource1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.cartcount)
    TextView txt_cartcount;
    @OnClick(R.id.cart) void cart() {
        Intent i = new Intent(this, CartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarmainfragment);
        ButterKnife.bind(this);
        customCalendar = (CustomCalendar)findViewById(R.id.customCalendar);
        smileyserveDataSource=new SmileyserveDataSource(this);
        smileyserveDataSource1=new SmileyserveDataSource(this);
        userid = SharedPreferenceUtil.getInstance(this).getUserid();
        if(Constans.haveInternet(this)) {
            if (userid != null) {
                smileyserveDataSource.getCalendarDates(userid);
                smileyserveDataSource1.getCartCount(userid, 5);
                pDialog = ProgressDialog.show(this, "", Constans.progressmessage, true, false);
            }
        }
        else
        {
           Constans.IntenetSettings(this);

        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(CalendarActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


    }
    public void showCalendarResponse(Object o) {
        String response = ((GETCalendarDates) o).getCode();
        List<CalendarResults> getCalendarresult =((GETCalendarDates) o).getCalender_result();
        if (response.equals(Constans.Response_200)) {
           if (getCalendarresult.size() > 0) {
                if(pDialog!=null)
                {
                    pDialog.dismiss();
                }
                for (int i = 0; i < getCalendarresult.size(); i++) {
                    arr = getCalendarresult.get(i).getOrderdate();
                    count = getCalendarresult.get(i).getQty();
                    int eventCount = Integer.parseInt(count);
                    customCalendar.addAnEvent(arr,eventCount,getEventDataList(eventCount));
                }
            }
            else
            {
                if(pDialog!=null)
                {
                    pDialog.dismiss();
                }
            }
        }
        else
        {
            if(pDialog!=null)
            {
                pDialog.dismiss();
            }
        }
    }
    public ArrayList<EventData> getEventDataList(int count) {
        ArrayList<EventData> eventDataList = new ArrayList();
        return  eventDataList;
    }
    // cart response
    public void showCartCountDisplay(Object o) {
        String response = ((CartResponse) o).getCart_count();
        if (response != null) {
            if (!response.equals("0")) {
                txt_cartcount.setVisibility(View.VISIBLE);
                txt_cartcount.setText(response);
            } else {

                txt_cartcount.setVisibility(View.GONE);
            }
        }

    }
}
