package com.richlabz.myfunctionhall.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.HLMSolutions.MyfunctionHall.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.richlabz.myfunctionhall.commonutilities.SharedPreferenceUtil;
import com.richlabz.myfunctionhall.datasource.FunctionhalBidding;
import com.richlabz.myfunctionhall.datasource.MyFunctionHallDatasource;
import com.richlabz.myfunctionhall.entities.RestResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.HLMSolutions.MyfunctionHall.R.id.enddate;
import static com.HLMSolutions.MyfunctionHall.R.id.endtime;
import static com.HLMSolutions.MyfunctionHall.R.id.functionhal;
import static com.HLMSolutions.MyfunctionHall.R.id.hours;
import static com.HLMSolutions.MyfunctionHall.R.id.nonac;
import static com.HLMSolutions.MyfunctionHall.R.id.pandithaddress;
import static com.HLMSolutions.MyfunctionHall.R.id.pandithstatus;

public class FunctionhallpostBidActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.Dateofjourney)
    TextView dateofjourney;
    @Bind(R.id.returnofjourneys)
    TextView returnjourney;
    Button poojabidnow;
    private int hours1, mins1, hours2, mins2;
    private static final int TIME_DIALOG_ID = 9999;
    private static final int TIME_DIALOG_ID1 = 1888;
    private static final int TIME_DIALOG_ID2 = 1988;
    int time = 0;
    int times = 0;
    String checkdate, journeytime, checkeddate, returndate, userid,typeofAc,typeofNonac,functionendtime,endcheckdate,typeoffood;
    private int year, month, day, hour, min;
    Calendar startdatecalendar;
    @Bind(R.id.actype)
    RadioGroup actype;
    @Bind(R.id.nonacradiogroup)
    RadioGroup nonacradiogroup;
    @Bind(R.id.seatingcapacity)
    EditText seatingcapacity;
    @Bind(R.id.deliverytime)
    TextView deliverytime;
    @Bind(R.id.textViewDayTime)
    TextView editTextTime;
    @Bind(R.id.enddate)
    TextView enddate;
    @Bind(R.id.endtime)
    TextView endtime;
    @Bind(R.id.pandithaddress)
    TextView poojaaddress;
    @Bind(R.id.foodpreference)
    RadioGroup foodpreference;
    @Bind(R.id.title)
    EditText title;

    MyFunctionHallDatasource myFunctionHallDatasource=new MyFunctionHallDatasource(this);
    ProgressDialog pdilogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionhallpost_bid);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        startdatecalendar = Calendar.getInstance();
        startdatecalendar.add(Calendar.DAY_OF_MONTH, 1);
        userid = SharedPreferenceUtil.getInstance(this).getUserId();
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        checkeddate = df.format(c1.getTime());
        typeofAc = "1";
       actype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.ac:
                     typeofAc =" 1";
                        nonacradiogroup.setVisibility(View.GONE);
                        break;
                    case R.id.nonac:
                        typeofAc =" 2";
                        nonacradiogroup.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        if(typeofAc.equals("2")) {
            typeofNonac = " 1";
            typeofAc = " 2";
        }
        nonacradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.closed:
                        typeofNonac =" 1";
                        typeofAc =" 2";
                        break;
                    case R.id.open:
                        typeofNonac =" 2";
                        typeofAc =" 2";
                        break;
                    case R.id.garden:
                        typeofNonac =" 3";
                        typeofAc =" 2";
                        break;

                }
            }

        });
        typeoffood="1";
        foodpreference.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.inside:
                        typeoffood="1";
                        break;
                    case R.id.outside:
                        typeoffood="2";
                        break;
                    case R.id.inside_cat:
                        typeoffood="3";
                        break;


                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
    @OnClick(R.id.pandithaddress)
    void getTravel(View view) {
        findlocation(view);
    }
    private void findlocation(View view) {
        try {
            Intent intent = new PlaceAutocomplete
                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
        } catch (GooglePlayServicesNotAvailableException e) {
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //progressDialog.dismiss();
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                // Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                ((TextView) findViewById(R.id.pandithaddress))
                        .setText(place.getName()+","+place.getAddress());

                // picuplocation = poo.getText().toString();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                /// TODO: Handle the error.
                //Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
    }
    // here check   startdatedate of journey
    @OnClick(R.id.Dateofjourney)
    protected void onCalendarClick() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                checkdate = String.valueOf(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
                dateofjourney.setText(checkdate);
            }
        },

                startdatecalendar.YEAR, startdatecalendar.MONTH, startdatecalendar.DAY_OF_MONTH);
        dialog.getDatePicker().setMinDate(startdatecalendar.getTimeInMillis());
        dialog.show();
    }

    // here check   enddatedatedate of journey
    @OnClick(R.id.enddate)
    protected void onCalendarendClick() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
               endcheckdate = String.valueOf(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
              enddate.setText(endcheckdate);
            }
        },

                startdatecalendar.YEAR, startdatecalendar.MONTH, startdatecalendar.DAY_OF_MONTH);
        dialog.getDatePicker().setMinDate(startdatecalendar.getTimeInMillis());
        dialog.show();
    }
    // here check date of startime select
    @OnClick(R.id.deliverytime)
    void delivertime() {
        time = TIME_DIALOG_ID;
        Dialog dialog = new TimePickerDialog(this, timePickerListener1, hour, min, DateFormat.is24HourFormat(this));
        dialog.show();
    }
    private TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {

            hours1 = selectedHour;
            mins1 = selectedMinute;
            if (checkdate != null) {
                deliverytime.setText(new StringBuilder().append(hours1).append(":").append(mins1));
                if(del)
               journeytime = deliverytime.getText().toString();
                //Callchecktime(view);
            } else {
                Toast.makeText(FunctionhallpostBidActivity.this, "Choose Your Date of Journey", Toast.LENGTH_SHORT).show();
            }
        }
    };


    // here check date of startime select
    @OnClick(R.id.endtime)
    void endtimetime() {
        time = TIME_DIALOG_ID2;
        Dialog dialog = new TimePickerDialog(this, timePickerListener2, hour, min, DateFormat.is24HourFormat(this));
        dialog.show();
    }
    private TimePickerDialog.OnTimeSetListener timePickerListener2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hours1 = selectedHour;
            mins1 = selectedMinute;
            if (endcheckdate!= null) {
                //endtime.setText(new StringBuilder().append(hours1).append(":").append(mins1));
                //journeytime = deliverytime.getText().toString();
                 Callchecktime(view);
            } else {
                Toast.makeText(FunctionhallpostBidActivity.this, "Choose Your  end Date of Function", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /* call method for check wheather bid end date is less than  delivery time 1 hours or not *************/
    private void Callchecktime(TimePicker view) {
        String selectedtime = String.valueOf(new StringBuilder().append(hours1).append(":").append(mins1));
        String Selecteddate = checkdate + " " + journeytime;
        String comparedate=endcheckdate+" "+selectedtime;
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
            Date date = null;
            Date date1 = null;
            try {
                date = dateFormat.parse(Selecteddate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Date futerdate = calendar.getTime();
                date1=dateFormat.parse(comparedate);
                Calendar enddate=Calendar.getInstance();
                enddate.setTime(date1);
                Date functionendate=enddate.getTime();
                long diff = functionendate.getTime()-futerdate.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                int checkhours = (int) hours;
                if (checkhours >= 3) {
                    if (time == TIME_DIALOG_ID2) {
                       endtime.setText(new StringBuilder().append(hours1).append(":").append(mins1));
                        functionendtime = endtime.getText().toString();
                    }
                } else {
                    Toast.makeText(view.getContext(), "Allowing Time  After 3 hours of function starttime ", Toast.LENGTH_SHORT).show();
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    // here check date of return journey
    @OnClick(R.id.returnofjourneys)
    protected void returnjourey() {
        DatePickerDialog dialog = new DatePickerDialog(FunctionhallpostBidActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                returndate = String.valueOf(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
                returnjourney.setText(returndate);
            }
        },
                startdatecalendar.YEAR, startdatecalendar.MONTH, startdatecalendar.DAY_OF_MONTH);
        dialog.getDatePicker().setMinDate(startdatecalendar.getTimeInMillis());
        dialog.show();

    }
    // here the check Bidclosed On
    @OnClick(R.id.textViewDayTime)
    void timesubmit() {
        times = TIME_DIALOG_ID1;
        Dialog dialog = new TimePickerDialog(this, timePickerListener, hours, min, DateFormat.is24HourFormat(this));
        dialog.show();
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int Hour, int Minute) {
            hours2 = Hour;
            mins2 = Minute;
            if (returndate != null) {
                Callreturntimechecktime(view);
            } else {
                Toast.makeText(FunctionhallpostBidActivity.this, "Choose Your date", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @OnClick(R.id.poojabidnow) void bidnow()
    {
        submitOrder();
    }

    private void submitOrder() {

        if(title.getText().toString().matches(""))
        {
            Toast.makeText(this, R.string.title, Toast.LENGTH_SHORT).show();
            title.setBackgroundResource(R.drawable.red_background);
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
        }

       else if (seatingcapacity.getText().toString() == null) {
            Toast.makeText(this, R.string.seating, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.red_background);
            title.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
        } else if (dateofjourney.getText().toString().matches("")) {
            Toast.makeText(this, R.string.enterpojatype, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.red_background);
            title.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
        } else if (deliverytime.getText().toString().matches("")) {
            Toast.makeText(this, R.string.enterpojatime, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.red_background);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
        } else if (enddate.getText().toString().matches("")) {
            Toast.makeText(this, R.string.enterenddate, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.red_background);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
            title.setBackgroundResource(R.drawable.button_posttravel);
        } else if (endtime.getText().toString().matches("")) {
            Toast.makeText(this, R.string.enterendTime, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            title.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.red_background);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
        } else if (returnjourney.getText().toString().matches("")) {
            Toast.makeText(this, R.string.enterbidclosed, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.red_background);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
            title.setBackgroundResource(R.drawable.button_posttravel);

        } else if (editTextTime.getText().toString().matches(""))

        {
            Toast.makeText(this, R.string.enterbidclosedtime, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.red_background);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
            title.setBackgroundResource(R.drawable.button_posttravel);
        }
         else if (poojaaddress.getText().toString().matches("")) {
            Toast.makeText(this, R.string.enterpanidthaddress, Toast.LENGTH_SHORT).show();
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.red_background);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
            title.setBackgroundResource(R.drawable.button_posttravel);
        }
        else
        {
            seatingcapacity.setBackgroundResource(R.drawable.button_posttravel);
            dateofjourney.setBackgroundResource(R.drawable.button_posttravel);
            deliverytime.setBackgroundResource(R.drawable.button_posttravel);
            editTextTime.setBackgroundResource(R.drawable.button_posttravel);
            returnjourney.setBackgroundResource(R.drawable.button_posttravel);
            poojaaddress.setBackgroundResource(R.drawable.button_posttravel);
            enddate.setBackgroundResource(R.drawable.button_posttravel);
            endtime.setBackgroundResource(R.drawable.button_posttravel);
            title.setBackgroundResource(R.drawable.button_posttravel);
            FunctionhalBidding functionhalBidding=new FunctionhalBidding();
            functionhalBidding.setTitle(" Need Function Hall for"+title.getText().toString().trim());
            functionhalBidding.setUser_id(userid);
            String stardate = dateofjourney.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = null;
            if (stardate != null) {
                try {
                    date = dateFormat.parse(stardate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String stardates = dateFormat1.format(calendar.getTimeInMillis());
            functionhalBidding.setStartdate(stardates + " " + deliverytime.getText().toString());
            /**** function hall bid closed date ********************/
            String returnjourneydate = returnjourney.getText().toString();
            if (!returnjourneydate.matches("")) {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date1 = null;
                try {
                    date1 = dateFormat2.parse(returnjourneydate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormat21 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(date1);
                String enddate = dateFormat21.format(calendar1.getTimeInMillis());
                functionhalBidding.setBiddenddate(enddate + " " + editTextTime.getText().toString());
            } else {
                functionhalBidding.setBiddenddate(returnjourney.getText().toString() + " " + editTextTime.getText().toString());
            }
            // function hall End date
            String checkout=enddate.getText().toString().trim();

            if(!checkout.matches(""))
            {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date1 = null;
                try {
                    date1 = dateFormat2.parse(checkout);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormat21 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(date1);
                String enddate = dateFormat21.format(calendar1.getTimeInMillis());
                functionhalBidding.setClosedate(enddate + " " + endtime.getText().toString());
            }
            else
            {
                functionhalBidding.setClosedate(enddate.getText().toString().trim() + " " + endtime.getText().toString());
            }
            functionhalBidding.setSeatingcapacity(seatingcapacity.getText().toString().trim());
            functionhalBidding.setMhtype(typeofAc);
            if(typeofNonac!=null) {
                functionhalBidding.setNonactype(typeofNonac);
            }
            else
            {
                functionhalBidding.setNonactype("0");
            }
            functionhalBidding.setFoodtype(typeoffood);
            functionhalBidding.setLocation(poojaaddress.getText().toString().trim());
            myFunctionHallDatasource.postfunctionhalbidding(functionhalBidding);
            pdilogue=ProgressDialog.show(this," ","Please wait while Posting Functionhall Bids",true,true);


        }
    }

    // CALL METHOD for Bid closed ON  check conditions minum 5 hours of  Start DATE of Function
    private void Callreturntimechecktime(TimePicker view) {
        String selectedtime = String.valueOf(new StringBuilder().append(hours2).append(":").append(mins2));
        String returnjourney = returndate + "" + selectedtime;
        String Compareddate = checkdate + " " + journeytime;
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
            Date date = null;
            Date date1 = null;
            try {
                date = dateFormat.parse(Compareddate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Date comparedate = calendar.getTime();
                date1 = dateFormat.parse(returnjourney);
                Calendar compre = Calendar.getInstance();
                compre.setTime(date1);
                Date selcteddate = compre.getTime();
                long compare = comparedate.getTime()-selcteddate.getTime();
                long seconds = compare / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                int checkhours = (int) hours;
                if (checkhours > 0) {
                    if (checkhours > 5) {
                        if (times == TIME_DIALOG_ID1) {
                            editTextTime.setText(new StringBuilder().append(hours2).append(":").append(mins2));
                        }
                    } else {
                        Toast.makeText(view.getContext(), "Allowing Time below 5 hours of Function start Time ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(view.getContext(), "Allowing Time  below 5 hours of Function start Time", Toast.LENGTH_SHORT).show();

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void showRes(Object o) {
        String res = ((RestResponse) o).getCode();
        String message = ((RestResponse) o).getMessage();
        if (res.equals("200")) {
            if (pdilogue != null) {
                pdilogue.dismiss();
            }
            finish();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, BiddingSucessFullyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
    }

