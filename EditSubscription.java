package com.richlabz.smileyserve.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.richlabz.smileyserve.R;
import com.richlabz.smileyserve.datasource.CartResponse;
import com.richlabz.smileyserve.datasource.Edisubscriptionresponse;
import com.richlabz.smileyserve.datasource.Editsubscriptionclass;
import com.richlabz.smileyserve.datasource.EndSubscription;
import com.richlabz.smileyserve.datasource.GetCalendareSubscriotionDays;
import com.richlabz.smileyserve.datasource.Geteditsubscritpionresult;
import com.richlabz.smileyserve.datasource.GettingProductDetails;
import com.richlabz.smileyserve.datasource.PaymentRespone;
import com.richlabz.smileyserve.datasource.Paymentdetails;
import com.richlabz.smileyserve.datasource.Prodductdetails;
import com.richlabz.smileyserve.datasource.ProductdetailsResponse;
import com.richlabz.smileyserve.datasource.SmileyCashResponse;
import com.richlabz.smileyserve.datasource.Smileycash;
import com.richlabz.smileyserve.datasource.SmileyserveDataSource;
import com.richlabz.smileyserve.datasource.Subscribeproduct;
import com.richlabz.smileyserve.datasource.SubscriptionSucess;
import com.richlabz.smileyserve.datasource.UpdateSubscription;
import com.richlabz.smileyserve.datasource.Updateresponse;
import com.richlabz.smileyserve.entities.RestResponse;
import com.richlabz.smileyserve.utilities.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class EditSubscription extends AppCompatActivity implements PaymentResultWithDataListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.subscriptionimage)
    ImageView imgSubscription;
    @Bind(R.id.productname)
    TextView txt_productName;
    @Bind(R.id.productqty)
    TextView txt_Productqty;
    @Bind(R.id.productmrp)
    TextView txt_Productmrp;
    @Bind(R.id.startdate)
    TextView txt_startdate;
    @Bind(R.id.end_date)
    TextView txt_enddate;
    String checkingscreen;
    @Bind(R.id.titletext)
    TextView txt_title;
    @Bind(R.id.End)
    Button btn_end;
    @Bind(R.id.update)
    Button btn_update;
    @Bind(R.id.sundaytxt)
    TextView sunday_txt;
    @Bind(R.id.Mondaydaytxt)
    TextView monday_txt;
    @Bind(R.id.Tuesdaytxt)
    TextView tuesday_txt;
    @Bind(R.id.wedtxt)
    TextView wedday_txt;
    @Bind(R.id.thustxt)
    TextView thusday_txt;
    @Bind(R.id.Fridaytxt)
    TextView friday_txt;
    @Bind(R.id.sattxt)
    TextView satday_txt;
    @Bind(R.id.cartcount)
    TextView txt_cartcount;
    @Bind(R.id.titlelayout)
    LinearLayout titlelayout;
    Button button;
    SmileyserveDataSource smileyserveDataSource = new SmileyserveDataSource(this);
    String[] ddmmyy;
    int countsun, countmon, counttues, countwed, countthu, countfri, countsat, day, day1, month, month1, year, year1,  mrp;
    String startdate1, id, enddate1,changeEnddate, yy, dd, mm, sunday, Monday, Tuesday, Wednesday, Thusday, Friday, Satday, itemid, sundayqty, mondayqty, tuesdayqty, weddayqty, thusdayqty, fridayqty, satdayqty;
    List<Date> dates = new ArrayList<Date>();
    Calendar startdatecalendar, enddatecalendar, todaydate;
    private String loginvalues = "1";
    private String Subcriptionendate, subcriptionstartdate, apartmentid, userid, subscriptionenddate;
    CheckBox smileycheck;
    String debit, credit, smileycash;
    TextView txt, smileycash_text;
    private String smiley;
    private String debitamount;
    private float totaldebit,Total;
    private String endmyDate;
    private String updateenddate;
    private String Updatestartdate;
    ProgressDialog progressDialog;
    private String changeStartdate;
    String mobile, name, email;
    DatePickerDialog startdialog,enddialog;
    private String Editdate;
    private String StatmyDate;
    private String debits;
    private String smileycheckedvalue;
    private String todaydates;
    private String checkingtodaydate;
    private String paymetpercentage,tax;
    @Bind(R.id.addsun)
    Button addsun;
    @Bind(R.id.minusun)
    Button minussun;
    @Bind(R.id.addmonday)
    Button addmonday;
    @Bind(R.id.minusmon)
    Button minusmonday;
    @Bind(R.id.addtues)
    Button addtus;
    @Bind(R.id.minustues)
    Button minustues;
    @Bind(R.id.addwed)
    Button addwed;
    @Bind(R.id.minuswed)
    Button minuswed;
    @Bind(R.id.addthus)
    Button addthus;
    @Bind(R.id.minusthus)
    Button minusthus;
    @Bind(R.id.addfriday)
    Button addfriday;
    @Bind(R.id.minusfriday)
    Button minusfriday;
    @Bind(R.id.addsat)
    Button addsat;
    @Bind(R.id.minussat)
    Button minusat;
    String orderdate;
    private String insertstardate;
    private String insertdates;

    @OnClick(R.id.cart)
    void cart() {
        Intent i = new Intent(this, CartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    // here the write the Functionalities for + or-
    @OnClick({R.id.addsun, R.id.minusun, R.id.addmonday, R.id.minusmon, R.id.addtues, R.id.minustues, R.id.addwed, R.id.minuswed, R.id.addthus, R.id.minusthus, R.id.addfriday, R.id.minusfriday, R.id.addsat, R.id.minussat})
    void addweekdates(View view) {
        switch (view.getId()) {
            case R.id.addsun:
                if (sunday_txt == null) {
                    sunday_txt.setText("1");
                }
                countsun = Integer.parseInt(sunday_txt.getText().toString());
                countsun++;
                sunday_txt.setText(Integer.toString(countsun));
                //btn_update.setVisibility(View.VISIBLE);
                break;
            case R.id.minusun:
                countsun = Integer.parseInt(sunday_txt.getText().toString());
                countsun--;
                if (countsun >= 0) {
                    sunday_txt.setText(Integer.toString(countsun));
                }
                break;

            case R.id.addmonday:
                if (monday_txt == null) {
                    monday_txt.setText("1");
                }
                countmon = Integer.parseInt(monday_txt.getText().toString());
                countmon++;
                monday_txt.setText(Integer.toString((countmon)));
                break;
            case R.id.minusmon:
                countmon = Integer.parseInt(monday_txt.getText().toString());
                countmon--;
                if (countmon >= 0) {
                    monday_txt.setText(Integer.toString(countmon));
                }
                break;
            case R.id.addtues:
                if (tuesday_txt == null) {
                    tuesday_txt.setText("1");
                }
                counttues = Integer.parseInt(tuesday_txt.getText().toString());
                counttues++;
                tuesday_txt.setText(Integer.toString(counttues));
                break;
            case R.id.minustues:
                counttues = Integer.parseInt(tuesday_txt.getText().toString());
                counttues--;
                if (counttues >= 0) {
                    tuesday_txt.setText(Integer.toString(counttues));
                }
                break;
            case R.id.addwed:
                if (wedday_txt == null) {
                    wedday_txt.setText("1");
                }
                countwed = Integer.parseInt(wedday_txt.getText().toString());
                countwed++;
                wedday_txt.setText(Integer.toString(countwed));
                break;
            case R.id.minuswed:
                countwed = Integer.parseInt(wedday_txt.getText().toString());
                countwed--;
                if (countwed >= 0) {
                    wedday_txt.setText(Integer.toString(countwed));
                }
                break;
            case R.id.addthus:
                if (thusday_txt == null) {
                    thusday_txt.setText("1");
                }
                countthu = Integer.parseInt(thusday_txt.getText().toString());
                countthu++;
                thusday_txt.setText(Integer.toString(countthu));
                btn_update.setVisibility(View.VISIBLE);
                break;
            case R.id.minusthus:
                countthu = Integer.parseInt(thusday_txt.getText().toString());
                countthu--;
                if (countthu >= 0) {
                    thusday_txt.setText(Integer.toString(countthu));
                }
                break;
            case R.id.addfriday:
                if (friday_txt == null) {
                    friday_txt.setText("1");
                }
                countfri = Integer.parseInt(friday_txt.getText().toString());
                countfri++;
                friday_txt.setText(Integer.toString(countfri));
                break;
            case R.id.minusfriday:
                countfri = Integer.parseInt(friday_txt.getText().toString());
                countfri--;
                if (countfri >= 0) {
                    friday_txt.setText(Integer.toString(countfri));
                }
                break;
            case R.id.addsat:
                if (satday_txt == null) {
                    satday_txt.setText("1");
                }
                countsat = Integer.parseInt(satday_txt.getText().toString());
                countsat++;
                satday_txt.setText(Integer.toString(countsat));
                break;
            case R.id.minussat:
                countsat = Integer.parseInt(satday_txt.getText().toString());
                countsat--;
                if (countsat >= 0) {
                    satday_txt.setText(Integer.toString(countsat));
                }
                break;
            default:
                break;
        }
    }

    SmileyserveDataSource smileyserveDataSource1, smileyserveDataSource2, smileyserveDataSource3, smileyserveDataSource4,percentagedatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.subscriptionedit);
        ButterKnife.bind(this);
        smileyserveDataSource1 = new SmileyserveDataSource(this);
        smileyserveDataSource2 = new SmileyserveDataSource(this);
        smileyserveDataSource3 = new SmileyserveDataSource(this);
        smileyserveDataSource4 = new SmileyserveDataSource(this);
        percentagedatasource=new SmileyserveDataSource(this);
        addsun.setVisibility(View.VISIBLE);
        minussun.setVisibility(View.VISIBLE);
        addmonday.setVisibility(View.VISIBLE);
        minusmonday.setVisibility(View.VISIBLE);
        addtus.setVisibility(View.VISIBLE);
        minustues.setVisibility(View.VISIBLE);
        addwed.setVisibility(View.VISIBLE);
        minuswed.setVisibility(View.VISIBLE);
        addthus.setVisibility(View.VISIBLE);
        minusthus.setVisibility(View.VISIBLE);
        addfriday.setVisibility(View.VISIBLE);
        minusfriday.setVisibility(View.VISIBLE);
        addsat.setVisibility(View.VISIBLE);
        minusat.setVisibility(View.VISIBLE);
        percentagedatasource.getpaymentpercentage(2);
        setSupportActionBar(toolbar);
        startdatecalendar = Calendar.getInstance();
        todaydate = Calendar.getInstance();
        startdatecalendar.add(Calendar.DAY_OF_MONTH, 1);
        enddatecalendar = Calendar.getInstance();
        final Calendar c1 = Calendar.getInstance();
        day = c1.get(Calendar.DAY_OF_MONTH);
        month = c1.get(Calendar.MONTH);
        year = c1.get(Calendar.YEAR);
        todaydates = String.valueOf(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));

        // get current date for checking For Restrit
        final Calendar c2 = Calendar.getInstance();
        day = c2.get(Calendar.DAY_OF_MONTH);
        month = c2.get(Calendar.MONTH);
        year = c2.get(Calendar.YEAR);
       String checkingtoday = String.valueOf(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" "));
       // convert date to 03-02-2017 to 03-feb-2017
        checkingtodaydate = Constans.getFormattedDate("dd-MMM-yyyy","dd-MM-yyyy",checkingtoday);
        name = SharedPreferenceUtil.getInstance(this).getName();
        mobile = SharedPreferenceUtil.getInstance(this).getMobil();
        email = SharedPreferenceUtil.getInstance(this).getEmail();
        userid = SharedPreferenceUtil.getInstance(this).getUserid();
        if (userid != null) {
            smileyserveDataSource1.getCartCount(userid, 3);
        }
        /**** get smiley amount**********/
        userid = SharedPreferenceUtil.getInstance(this).getUserid();
        Smileycash smileycash = new Smileycash();
        if (userid != null) {
            smileycash.setUserid(userid);
        }
        smileycash.setPage_number("1");
        smileycash.setRequired_count("10");
        smileyserveDataSource3.getSmileyCash(smileycash, 4);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            itemid = bundle.getString("itemid");
            checkingscreen = bundle.getString("checkingscreen");
            apartmentid = bundle.getString("apartment");
            orderdate=bundle.getString("orderdate");
        }
        if (Constans.haveInternet(this)) {
            if (id != null) {
                Prodductdetails prodductdetails = new Prodductdetails();
                if (userid != null) {
                    prodductdetails.setUserid(userid);
                } else {
                    prodductdetails.setUserid("0");
                }
                prodductdetails.setProductid(id);
                if (apartmentid != null) {
                    prodductdetails.setApartmentid(apartmentid);
                } else {
                    prodductdetails.setApartmentid("0");
                }
                smileyserveDataSource.getproductdetials(prodductdetails);
                progressDialog = ProgressDialog.show(this, "", Constans.progressmessage, true, false);
            }
        } else {
            Constans.haveInternet(this);
        }
        // here checking screen which screen display either order or Edit Screen bcz here both pages use one layout
        if (checkingscreen != null) {
            if (checkingscreen.equals("1")) {
                //chk_date.setVisibility(View.VISIBLE);
                txt_title.setText("Order Now");
                btn_end.setVisibility(View.GONE);
                btn_update.setText("Add to Cart");

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // showdailoues();
                        // here checking the login user or not
                        if (userid != null) {
                            try {
                                postSubcribeorder();
                                progressDialog = ProgressDialog.show(v.getContext(), "", Constans.progressmessage, true, false);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {

                            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                            login.putExtra("login", loginvalues);
                            startActivity(login);
                        }
                    }
                });
            }
        } else {
            txt_title.setText("Edit Subscription ");
            //btn_update.setText("Update");
            btn_update.setVisibility(View.GONE);
//            btn_update.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    try {
//                        updatetSubcribeorder();
//                        progressDialog = ProgressDialog.show(view.getContext(), "", Constans.progressmessage, true, false);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
            addsun.setVisibility(View.GONE);
            minussun.setVisibility(View.GONE);
            addmonday.setVisibility(View.GONE);
            minusmonday.setVisibility(View.GONE);
            addtus.setVisibility(View.GONE);
            minustues.setVisibility(View.GONE);
            addwed.setVisibility(View.GONE);
            minuswed.setVisibility(View.GONE);
            addthus.setVisibility(View.GONE);
            minusthus.setVisibility(View.GONE);
            addfriday.setVisibility(View.GONE);
            minusfriday.setVisibility(View.GONE);
            addsat.setVisibility(View.GONE);
            minusat.setVisibility(View.GONE);

            if (itemid != null) {
                Editsubscriptionclass editsubscriptionclass = new Editsubscriptionclass();
                if (userid != null) {
                    editsubscriptionclass.setUserid(userid);
                }
                editsubscriptionclass.setItemid(itemid);
                smileyserveDataSource.geteditssubcriptiondetails(editsubscriptionclass);
                progressDialog = ProgressDialog.show(this, "", Constans.progressmessage, true, false);
            }
            btn_end.setVisibility(View.VISIBLE);
            btn_end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Alerdailogues(view);
                }
            });
        }
        // Here check order time conditions for startdate

        String currentime = Constans.getCurrentTime();
        int currenttime = Integer.parseInt(currentime);
        if (currenttime < 21) {
            // this dates comming from the calendarpage
            if(orderdate!=null)
            {
                 insertstardate = Constans.getFormattedDate("dd-MMM-yyyy","yyyy-MM-dd",orderdate);
                txt_startdate.setText(insertstardate);
            }
            else {
                getstartdateTime(startdatecalendar);
            }
        } else {
            startdatecalendar.add(Calendar.DAY_OF_MONTH, 1);
            getstartdateTime(startdatecalendar);

        }txt_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkingscreen != null) {
                    if (checkingscreen.equals("1")) {
                        if (orderdate == null) {
                         Startdate();
                    }
                    }
                }
            }
        });
        // Here checking time conditions for End date;
        if (currenttime < 21) {
            // write code for end date
            if(orderdate!=null) {
                txt_enddate.setText(insertstardate);
            }
            else {
                enddatecalendar.add(Calendar.DAY_OF_MONTH, 1);
                getEndateTime(enddatecalendar);
            }
        } else {
            String endcomparedate = startdate1;
            SimpleDateFormat datefomat = new SimpleDateFormat("dd-MM-yy", Locale.ENGLISH);
            try {
                Date endcheckdate = datefomat.parse(endcomparedate);
                enddatecalendar.setTime(endcheckdate);
                getEndateTime(enddatecalendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        txt_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkingscreen != null) {
                    if (checkingscreen.equals("1")) {
                         if(orderdate==null) {
                             Enddate();
                         }

                    }
                }

            }
        });
//        allVisible();
        // end for checking code
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // here for checking date range between Start Date and End Date;
        txt_enddate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkingscreen != null) {
                    if (checkingscreen.equals("1"))
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            String checkdate = String.valueOf(s);
                            Date date1 = sdf.parse(checkdate);
                            Date date2 = null;
                            if (changeStartdate != null) {
                                date2 = sdf.parse(changeStartdate);
                            } else {
                                date2 = sdf.parse(StatmyDate);
                            }
                            if (date2.before(date1)) {
                                Enddate();
                                enddialog.dismiss();
                            } else if (date1.equals(date2)) {
                                Enddate();
                                enddialog.dismiss();
                            } else {
                                txt_enddate.setText(" ");
                                Toast.makeText(getApplicationContext(), "End date always greater than or equal to start date", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        String checkdate = String.valueOf(s);
                        Date date1 = sdf.parse(checkdate);
                        Date date2 = null;
                        if (changeStartdate != null) {
                            date2 = sdf.parse(changeStartdate);
                            if (date2.before(date1)) {
                                Enddate();
                                enddialog.dismiss();
                            } else if (date1.equals(date2)) {
                                Enddate();
                                enddialog.dismiss();
                            } else {
                                txt_enddate.setText(" ");
                                Toast.makeText(getApplicationContext(), "End date always greater than or equal to start date", Toast.LENGTH_SHORT).show();
                            }
                        } else if (subcriptionstartdate != null) {
                            date2 = sdf.parse(subcriptionstartdate);
                            if (date2.before(date1)) {
                                Enddate();
                                enddialog.dismiss();
                            } else if (date1.equals(date2)) {
                                Enddate();
                                enddialog.dismiss();
                            } else {
                                txt_enddate.setText(" ");
                                Toast.makeText(getApplicationContext(), "End date always greater than or equal to start date", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                changeEnddate = String.valueOf(s);
                ChangeEndDate();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        if (checkingscreen != null) {
            if (checkingscreen.equals("1")) {
                ChangeEndDate();
            }
        }

        txt_startdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(checkingscreen!=null) {
                    if (checkingscreen.equals("1"))

                        changeStartdate = String.valueOf(s);
                    ChangeEndDate();
                }


            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // eductucate users to how to use week bar dates
    @OnClick(R.id.viewweekbar)
    void showweekbarinfo() {
        Showweekbardailogues();
    }

    private boolean Showweekbardailogues() {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setMessage(Constans.weekbarmessage);
        alertbox.setTitle("Info");
        alertbox.setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertbox.setCancelable(true);

                    }
                });
        alertbox.show();
        return true;
    }

    private boolean Alerdailogues(View view) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getContext());
        alertbox.setMessage(Constans.endsubsciption);
        alertbox.setTitle(Constans.deleteTitle);
        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                EndSubscriptiondata();
            }
        });
        alertbox.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertbox.setCancelable(true);

                    }
                });
        alertbox.show();
        return true;

    }

    //  write code for End subscription api
    private void EndSubscriptiondata() {
        EndSubscription endSubscription = new EndSubscription();
        endSubscription.setItemid(itemid);
        if (userid != null) {
            endSubscription.setUserid(userid);
        }
        final Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DAY_OF_MONTH, 1);
        day = c1.get(Calendar.DAY_OF_MONTH);
        month = c1.get(Calendar.MONTH);
        year = c1.get(Calendar.YEAR);
        String endsubribe = String.valueOf(new StringBuilder().append(day)
                .append("-").append(month + 1).append("-").append(year)
                .append(" "));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(endsubribe);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String orderdate = dateFormat1.format(calendar.getTimeInMillis());
            endSubscription.setOrderdate(orderdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        smileyserveDataSource.endSubscription(endSubscription);
    }

    // End DATE and Time
    private void getEndateTime(Calendar enddatecalendar) {
        day1 = enddatecalendar.get(Calendar.DAY_OF_MONTH);
        month1 = enddatecalendar.get(Calendar.MONTH);
        year1 = enddatecalendar.get(Calendar.YEAR);
        enddate1 = String.valueOf(new StringBuilder().append(day1)
                .append("-").append(month1 + 1).append("-").append(year1)
                .append(" "));
        String[] enddateArr = enddate1.split(" ");
        ddmmyy = enddateArr[0].split("-");
        yy = ddmmyy[2];
        dd = ddmmyy[0];
        mm = Constans.months[Integer.parseInt(ddmmyy[1]) - 1];
        endmyDate = dd + "-" + mm + "-" + yy;
        txt_enddate.setText(endmyDate);
    }

    private void getstartdateTime(Calendar calendar) {
        //calendar.add(Calendar.DAY_OF_MONTH,1);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        startdate1 = String.valueOf(new StringBuilder().append(day)
                .append("-").append(month + 1).append("-").append(year)
                .append(" "));
        String[] dateArr = startdate1.split(" ");
        ddmmyy = dateArr[0].split("-");
        yy = ddmmyy[2];
        dd = ddmmyy[0];
        mm = Constans.months[Integer.parseInt(ddmmyy[1]) - 1];
        StatmyDate = dd + "-" + mm + "-" + yy;
        txt_startdate.setText(StatmyDate);
    }

    // here write code for starTdate

    private void Startdate() {
        startdialog = new DatePickerDialog(EditSubscription.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                startdate1 = String.valueOf(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
                String[] dateArr = startdate1.split(" ");
                ddmmyy = dateArr[0].split("-");
                String yy = ddmmyy[2];
                String dd = ddmmyy[0];
                String mm = Constans.months[Integer.parseInt(ddmmyy[1]) - 1];
                String myDate = dd + "-" + mm + "-" + yy;
                txt_startdate.setText(myDate);
            }
        },
                startdatecalendar.YEAR, startdatecalendar.MONTH, startdatecalendar.DAY_OF_MONTH);
        startdialog.getDatePicker().setMinDate(startdatecalendar.getTimeInMillis());
        startdialog.show();
    }

    // here write the code for end date
    private void Enddate() {
        enddialog = new DatePickerDialog(EditSubscription.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                enddate1 = String.valueOf(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
                String[] dateArr = enddate1.split(" ");
                ddmmyy = dateArr[0].split("-");
                String yy = ddmmyy[2];
                String dd = ddmmyy[0];
                String mm = Constans.months[Integer.parseInt(ddmmyy[1]) - 1];
                String myDate = dd + "-" + mm + "-" + yy;
                txt_enddate.setText(myDate);
            }
        },
                enddatecalendar.YEAR, enddatecalendar.MONTH, enddatecalendar.DAY_OF_MONTH);
        enddialog.getDatePicker().setMinDate(enddatecalendar.getTimeInMillis());
        enddialog.show();
    }

    // Getting Current time
    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("HH",Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    // here control week date depending upon the selection of end date
    private void allVisible() {
        sunday_txt.setVisibility(View.VISIBLE);
        monday_txt.setVisibility(View.VISIBLE);
        tuesday_txt.setVisibility(View.VISIBLE);
        wedday_txt.setVisibility(View.VISIBLE);
        thusday_txt.setVisibility(View.VISIBLE);
        friday_txt.setVisibility(View.VISIBLE);
        satday_txt.setVisibility(View.VISIBLE);
    }

    // here write code for Order Submit
    private void postSubcribeorder() throws ParseException {

        Subscribeproduct subscribeproduct = new Subscribeproduct();
        subscribeproduct.setProductid(id);
        if(insertstardate!=null) {
            insertdates = Constans.getFormattedDate("dd-MM-yyyy", "dd-MMM-yyyy", insertstardate);
        }
        String userid = SharedPreferenceUtil.getInstance(this).getUserid();
        if (userid != null) {
            subscribeproduct.setUserid(userid);
        }
        // here the code convert to  yyyy-mm-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        if (startdate1 != null) {
            date = dateFormat.parse(startdate1);
        }
        else
        {
            date = dateFormat.parse(insertdates);
        }
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String stardate = dateFormat1.format(calendar.getTimeInMillis());
        subscribeproduct.setStartdate(stardate);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date1 = null;
        if (enddate1 != null) {
            date1 = dateFormat2.parse(enddate1);
        }
        else
        {
            date1 = dateFormat2.parse(insertdates);
        }
        SimpleDateFormat dateFormat21 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        String enddate = dateFormat21.format(calendar1.getTimeInMillis());
        subscribeproduct.setEnddate(enddate);
        sunday = sunday_txt.getText().toString().trim();
        subscribeproduct.setSunday(Integer.parseInt(sunday));
        Monday = monday_txt.getText().toString().trim();
        subscribeproduct.setMonday(Integer.parseInt(Monday));
        Tuesday = tuesday_txt.getText().toString().trim();
        subscribeproduct.setTuesday(Integer.parseInt(Tuesday));
        Wednesday = wedday_txt.getText().toString().trim();
        subscribeproduct.setWednesday(Integer.parseInt(Wednesday));
        Thusday = thusday_txt.getText().toString().trim();
        subscribeproduct.setThursday(Integer.parseInt(Thusday));
        Friday = friday_txt.getText().toString().trim();
        subscribeproduct.setFriday(Integer.parseInt(Friday));
        Satday = satday_txt.getText().toString().trim();
        subscribeproduct.setSatday(Integer.parseInt(Satday));
        smileyserveDataSource1.postsubcribeorder(subscribeproduct);
    }
    // show Product Details
    public void showProductDetailsResponse(Object o) {
        String resonse = ((ProductdetailsResponse) o).getCode();
        if (resonse.equals(Constans.Response_200)) {
            GettingProductDetails gettingProductDetails = ((ProductdetailsResponse) o).getProduct_details();
            if (gettingProductDetails != null) {
                if (progressDialog != null)
                {
                    progressDialog.dismiss();
                }
                Picasso.with(this).load(gettingProductDetails.getProductimage()).into(imgSubscription);
                txt_productName.setText(gettingProductDetails.getTitle());
//                mrp = Integer.parseInt(gettingProductDetails.getProductprice());
                txt_Productmrp.setText("Rs. " + gettingProductDetails.getProductprice() + " \t\t D.c : Rs. " + gettingProductDetails.getDeliverycharges());
                txt_Productqty.setText(gettingProductDetails.getProductunits());
            }
        }
        else {
                if (progressDialog != null)

                {
                    progressDialog.dismiss();
                }
            }
        }

    // Order Submit Response method
    public void showResponseproduct(Object o) {
        String response = ((RestResponse) o).getCode();
        String description = ((RestResponse) o).getDescription();
        if (response.equals(Constans.Response_200)) {
            if (progressDialog!= null) {
                progressDialog.dismiss();
            }
            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EditSubscription.this, CartActivity.class);
            i.putExtra("checkingscreen", checkingscreen);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();

            if (progressDialog!= null) {
                progressDialog.dismiss();
            }
        }
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

    // edit update price dailogues
    private void showdailoues(final String debit_amount, String credit_amount, String smiley_cash) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.subscribeorderdailogues, null, false);
        button = (Button) view.findViewById(R.id.orderpayment);
        txt = (TextView) view.findViewById(R.id.orderprice);
        button.setText("Proceed to Pay Rs." + debitamount);
        //txt.setText("Total Amount  to pay :" + debit_amount);
        debit = debit_amount;
        credit = credit_amount;
        smileycash_text = (TextView) view.findViewById(R.id.smileycash);
        if (smiley != null) {
            float smileypay = Float.parseFloat(smiley);
           float creditprice = Float.parseFloat(credit);
            float totalsmileyamount = creditprice + smileypay;
            smileycash = String.valueOf(totalsmileyamount);
            smileycash_text.setText(smileycash);
        }
        smileycheck = (CheckBox) view.findViewById(R.id.smileycheck);
        smileycheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (!checked) {
                    smileycheckedvalue = "0";
                    callamount1();

                } else {
                    smileycheckedvalue = "1";
                    callchecked();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float smileyamount = Float.parseFloat(smileycash);
                Total = smileyamount;
                if (Total > 0) {
                    if (smileycheckedvalue != null) {
                        callamount();
                    } else {
                        startpaymnet(debit_amount);
                    }
                } else {
                    startpaymnet(debit_amount);
                }
                dialog.dismiss();
            }
        });
        TextView imgcancel = (TextView) view.findViewById(R.id.cancel);
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void callchecked() {
        txt.setVisibility(View.VISIBLE);
        String Checksmiley = smileycash;
        if (Checksmiley != null) {
            float smileyamount = Float.parseFloat(smileycash);
            Total = smileyamount;
            if (Total > 0) {
                if (Total < totaldebit) {
                   float  payableamount = totaldebit - Total;
                    button.setText("Proceed to Pay Rs. " + String.valueOf(payableamount));
                } else {
                    //callamount();
                    button.setText("Proceed to Pay Rs. 0");
                }
            }
        }
    }

    private void callamount() {
        txt.setVisibility(View.VISIBLE);
//        int debitprice = Integer.parseInt(debit);
        String Checksmiley = smileycash;
        if (Checksmiley != null) {
           float smileyamount = Float.parseFloat(smileycash);
            Total = smileyamount;
            if (Total > 0) {
                if (Total < totaldebit) {
                    float payableamount = totaldebit - Total;
                    startpaymnet(String.valueOf(payableamount));
                } else {
                    txt.setVisibility(View.GONE);
                    final Calendar c1 = Calendar.getInstance();
                    day = c1.get(Calendar.DAY_OF_MONTH);
                    month = c1.get(Calendar.MONTH);
                    year = c1.get(Calendar.YEAR);
                    String todaydate = String.valueOf(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
                    SubscriptionSucess sub = new SubscriptionSucess();
                    sub.setUserid(userid);
                    sub.setCheckoutdate(todaydate);
                    sub.setEnablesmileycash("1");
                    smileyserveDataSource4.subscriptionSucess(sub, 1);
                }
            }
        }
    }

    private void startpaymnet(String debit_amount) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "SmileyServe");
            options.put("description", name);
            //You can omit the image option to fetch the image from dashboard
            //options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            if (debit_amount != null) {
                float amount = Float.parseFloat(debit_amount);
                float paymnetres= Float.parseFloat(paymetpercentage);
                float paymnetamount= (float) (amount+(amount)*paymnetres);
                options.put("amount",Math.round(paymnetamount) * 100);
            }

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobile);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    private void callamount1() {
        //txt.setText("Total Amount  to pay :" + debitamount);
        button.setText("Proceed to Pay Rs." + debitamount);
    }

    // get smileycash amount;
    public void showCashresponse(Object o) {
        smiley = ((SmileyCashResponse) o).getSmiley_cash();
    }

    // edit subscription response
    public void showeditSubscriptiondetails(Object o) {
        String response = ((Edisubscriptionresponse) o).getCode();
        if (response.equals(Constans.Response_200)) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Geteditsubscritpionresult geteditsubscritpionresults = ((Edisubscriptionresponse) o).getCart_result();
            Picasso.with(this).load(geteditsubscritpionresults.getPro_image()).into(imgSubscription);
            txt_productName.setText(geteditsubscritpionresults.getPro_name());
            //mrp = Integer.parseInt(geteditsubscritpionresults.getProduct_original_price());
            txt_Productmrp.setText("Rs. " + geteditsubscritpionresults.getProduct_original_price() + " \t \t D.c : Rs. " + geteditsubscritpionresults.getDelivery_charges());
            String endsubsriptionstartdate = geteditsubscritpionresults.getStart_date();
            txt_Productqty.setText(geteditsubscritpionresults.getProductunits());
            subscriptionenddate = geteditsubscritpionresults.getEnd_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date date = dateFormat.parse(subscriptionenddate);
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String startdate1 = dateFormat1.format(calendar.getTimeInMillis());
                String[] dateArr = startdate1.split(" ");
                ddmmyy = dateArr[0].split("-");
                //Now index to the above array will be your yymmdd[1]-1 coz array index starts from 0
                String yy = ddmmyy[2];
                String dd = ddmmyy[0];
                String mm = Constans.months[Integer.parseInt(ddmmyy[1]) - 1];
                //Now you have the dd,mm,and yy as you need, So you can concatenate and display it
                Subcriptionendate = dd + "-" + mm + "-" + yy;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // CHECK FOR END Subscription
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date1 = null;
            try {
                date1 = sdf.parse(startdate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date2 = null;
            try {
                date2 = sdf.parse(subscriptionenddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date2.after(date1)) {
                txt_enddate.setText(Subcriptionendate);
                updateenddate = txt_enddate.getText().toString();
                btn_end.setVisibility(View.VISIBLE);
            } else {
                txt_enddate.setVisibility(View.GONE);
                btn_end.setVisibility(View.GONE);
            }
            // assigned editsubsciptionorder date to stardate

            SimpleDateFormat editstartdate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date date = editstartdate.parse(endsubsriptionstartdate);
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String editstartdates = dateFormat1.format(calendar.getTimeInMillis());
                String[] dateArr = editstartdates.split(" ");
                ddmmyy = dateArr[0].split("-");
                //Now index to the above array will be your yymmdd[1]-1 coz array index starts from 0
                String yy = ddmmyy[2];
                String dd = ddmmyy[0];
                String mm = Constans.months[Integer.parseInt(ddmmyy[1]) - 1];
                //Now you have the dd,mm,and yy as you need, So you can concatenate and display it
                subcriptionstartdate = dd + "-" + mm + "-" + yy;
                txt_startdate.setText(subcriptionstartdate);
                Updatestartdate = txt_startdate.getText().toString();
                if (Updatestartdate != null) {
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        String checkdate = String.valueOf(Updatestartdate);
                        Date date3 = sd.parse(checkdate);
                        Date date4 = null;
                        if (checkingtodaydate != null) {
                            date4 = sd.parse(checkingtodaydate);
                            if (date3.equals(date4)) {
                                changeStartdate = Updatestartdate;
                                ChangeEndDate();
                            } else if (date3.after(date4)) {
                                changeStartdate = Updatestartdate;
                                ChangeEndDate();
                            } else {
                                changeStartdate = checkingtodaydate;
                                ChangeEndDate();
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }

                    //ChangeEndDate();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        List<GetCalendareSubscriotionDays> getCalendarProductsResults = ((Edisubscriptionresponse) o).getCalender();
        if (getCalendarProductsResults.size() > 0) {
            for (int i = 0; i < getCalendarProductsResults.size(); i++) {
                String days = getCalendarProductsResults.get(i).getDay_name();
                String qty = getCalendarProductsResults.get(i).getQty();
                if (days != null) {
                    int selectDay = Integer.parseInt(days);
                    if (selectDay == 1) {
                        mondayqty = qty;
                        monday_txt.setText(qty);
                    } else if (selectDay == 2) {
                        tuesdayqty = qty;
                        tuesday_txt.setText(qty);
                    } else if (selectDay == 3) {
                        weddayqty = qty;
                        wedday_txt.setText(qty);
                    } else if (selectDay == 4) {
                        thusdayqty = qty;
                        thusday_txt.setText(qty);
                    } else if (selectDay == 5) {
                        fridayqty = qty;
                        friday_txt.setText(qty);
                    } else if (selectDay == 6) {
                        satdayqty = qty;
                        satday_txt.setText(qty);
                    } else {
                        sundayqty = qty;
                        sunday_txt.setText(qty);
                    }

                }
            }
        }
    }
    }

    /********
     * write code for update
     ************/
    private void updatetSubcribeorder() throws ParseException {
        UpdateSubscription subscribeproduct = new UpdateSubscription();
        subscribeproduct.setProductid(itemid);
        String userid = SharedPreferenceUtil.getInstance(this).getUserid();
        if (userid != null) {
            subscribeproduct.setUserid(userid);
        }
        // here the code convert to  yyyy-mm-dd
        String updatestartdate = Constans.getFormattedDate("dd-MM-yyyy", "dd-MMM-yyyy", Updatestartdate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        if (updatestartdate != null) {
            date = dateFormat.parse(updatestartdate);
        }
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String stardate = dateFormat1.format(calendar.getTimeInMillis());
        subscribeproduct.setStartdate(stardate);
        if (changeEnddate != null) {
            Editdate = Constans.getFormattedDate("dd-MM-yyyy", "dd-MMM-yyyy", changeEnddate);
        } else {
            Editdate = Constans.getFormattedDate("dd-MM-yyyy", "dd-MMM-yyyy", updateenddate);
        }
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date1 = null;
        if (Editdate != null) {
            date1 = dateFormat2.parse(Editdate);
        }
        SimpleDateFormat dateFormat21 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        String enddate = dateFormat21.format(calendar1.getTimeInMillis());
        subscribeproduct.setEnddate(enddate);
        sunday = sunday_txt.getText().toString().trim();
        subscribeproduct.setSunday(Integer.parseInt(sunday));
        Monday = monday_txt.getText().toString().trim();
        subscribeproduct.setMonday(Integer.parseInt(Monday));
        Tuesday = tuesday_txt.getText().toString().trim();
        subscribeproduct.setTuesday(Integer.parseInt(Tuesday));
        Wednesday = wedday_txt.getText().toString().trim();
        subscribeproduct.setWednesday(Integer.parseInt(Wednesday));
        Thusday = thusday_txt.getText().toString().trim();
        subscribeproduct.setThursday(Integer.parseInt(Thusday));
        Friday = friday_txt.getText().toString().trim();
        subscribeproduct.setFriday(Integer.parseInt(Friday));
        Satday = satday_txt.getText().toString().trim();
        subscribeproduct.setSatday(Integer.parseInt(Satday));
        smileyserveDataSource2.editUpdateSubscription(subscribeproduct);

    }


    private void ChangeEndDate() {
        if (dates.size() == 0) {
            sunday_txt.setVisibility(View.INVISIBLE);
            monday_txt.setVisibility(View.INVISIBLE);
            tuesday_txt.setVisibility(View.INVISIBLE);
            wedday_txt.setVisibility(View.INVISIBLE);
            thusday_txt.setVisibility(View.INVISIBLE);
            friday_txt.setVisibility(View.INVISIBLE);
            satday_txt.setVisibility(View.INVISIBLE);
        }
        String str_date = null;
        String end_date = null;
//        if (Updatestartdate != null) {
//            str_date = Constans.getFormattedDate("dd-MM-yyyy", "dd-MMM-yyyy", Updatestartdate);
//        } else
//
            if (changeStartdate != null) {
            str_date = Constans.getFormattedDate("dd-MM-yyyy", "dd-MMM-yyyy", changeStartdate);
        } else {
            if (startdate1 != null) {
                str_date = startdate1;
            }
                else if(insertstardate!=null)
            {
                str_date = Constans.getFormattedDate("dd-MM-yyyy", "dd-MMM-yyyy", insertstardate);

            }
        }
        if (changeEnddate != null) {
            end_date = Constans.getFormattedDate("dd-MM-yyyy","dd-MMM-yyyy", changeEnddate);
        } else  if(enddate1!=null) {
            end_date = enddate1;
        }
        else if(insertstardate!=null)
        {
            end_date=Constans.getFormattedDate("dd-MM-yyyy","dd-MMM-yyyy",insertstardate);
        }
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date startDate = null;
        try {
            startDate = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = (Date) formatter.parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long interval = 24 * 1000 * 60 * 60;// 1 hour in millis
        long endTime = 0;
        if (endDate != null) {
            endTime = endDate.getTime();
        }
        // create your endtime here, possibly using Calendar or Date
        long curTime = 0;
        if (startDate != null) {
            curTime = startDate.getTime();
        }
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        if (dates.size() <= 7) {
            for (int j = 0; j < dates.size(); j++) {
                Date lDate = (Date) dates.get(j);
                String ds = formatter.format(lDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                try {
                    Date date = dateFormat.parse(ds);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int specifiedday = (calendar.get(Calendar.DAY_OF_WEEK));
                    String weekday1 = new DateFormatSymbols().getShortWeekdays()[specifiedday];
                    if (weekday1.equals("Sun")) {
                        sunday_txt.setVisibility(View.VISIBLE);
                        if (sundayqty != null) {
                            sunday_txt.setText(sundayqty);
                        } else {
                            sunday_txt.setText("0");
                        }
                    } else if (weekday1.equals("Mon")) {

                        monday_txt.setVisibility(View.VISIBLE);
                        if (mondayqty != null) {
                            monday_txt.setText(mondayqty);
                        } else {
                            monday_txt.setText("0");
                        }
                    } else if (weekday1.equals("Tue")) {
                        tuesday_txt.setVisibility(View.VISIBLE);
                        if (tuesdayqty != null) {
                            tuesday_txt.setText(tuesdayqty);
                        } else {
                            tuesday_txt.setText("0");
                        }
                    } else if (weekday1.equals("Wed")) {
                        wedday_txt.setVisibility(View.VISIBLE);
                        if (weddayqty != null) {
                            wedday_txt.setText(weddayqty);
                        } else {
                            wedday_txt.setText("0");
                        }
                    } else if (weekday1.equals("Thu")) {
                        thusday_txt.setVisibility(View.VISIBLE);
                        if (thusdayqty != null) {
                            thusday_txt.setText(thusdayqty);
                        } else {
                            thusday_txt.setText("0");
                        }
                    } else if (weekday1.equals("Fri")) {
                        friday_txt.setVisibility(View.VISIBLE);
                        if (fridayqty != null) {
                            friday_txt.setText(fridayqty);
                        } else {
                            friday_txt.setText("0");
                        }
                    } else {
                        satday_txt.setVisibility(View.VISIBLE);
                        if (satdayqty != null) {
                            satday_txt.setText(satdayqty);
                        } else {
                            satday_txt.setText("0");
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dates.clear();              // clear all days
        } else {
            allVisible();
            dates.clear();
        }
    }

    public void showendsubscription(Object o) {
        String code = ((RestResponse) o).getCode();
        String Response = ((RestResponse) o).getDescription();
        if (code.equals(Constans.response200)) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, Response, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, Response, Toast.LENGTH_SHORT).show();
    }

    // edit Update Response
    public void showEditSubscripitonResponse(Object o) {
        String code = ((Updateresponse) o).getCode();
        String Response = ((Updateresponse) o).getDescription();
        // Toast.makeText(this, Response, Toast.LENGTH_SHORT).show();
        if (code.equals(Constans.Response_200)) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Paymentdetails paymentdetails = ((Updateresponse) o).getPayment_detais();
            debits = paymentdetails.getDebit_amount();
            if (debits != null) {
                if (!debits.equals("0")) {
                    float checkcredit = Float.parseFloat(paymentdetails.getCredit_amount());
                    float checkdebit = Float.parseFloat(paymentdetails.getDebit_amount());
                    if (checkdebit > checkcredit) {
                        totaldebit = checkdebit - checkcredit;
                        debitamount = String.valueOf(totaldebit);
                        // debitamount = String.valueOf(totaldebit);
                    } else if (checkdebit < checkcredit) {
//                        totaldebit = checkcredit - checkdebit;
//                        debitamount = String.valueOf(totaldebit);
                        totaldebit = 0;
                        debitamount = String.valueOf(totaldebit);
                    } else if (checkcredit == checkdebit) {
                        totaldebit = checkcredit - checkdebit;
                        debitamount = String.valueOf(totaldebit);
                    }
                    if (!debitamount.equals("0.0")) {

                        showdailoues(paymentdetails.getDebit_amount(), paymentdetails.getCredit_amount(), paymentdetails.getSmiley_cash());
                    } else {
                        //();
                        callsuccesspayment();
                    }
                } else {
                    callsuccesspayment();
                }
            }
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
                Toast.makeText(this, Response, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callsuccesspayment() {
        final Calendar c1 = Calendar.getInstance();
        day = c1.get(Calendar.DAY_OF_MONTH);
        month = c1.get(Calendar.MONTH);
        year = c1.get(Calendar.YEAR);
        String todaydate = String.valueOf(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
        SubscriptionSucess sub = new SubscriptionSucess();
        sub.setUserid(userid);
        sub.setCheckoutdate(todaydate);
        sub.setEnablesmileycash("0");
        smileyserveDataSource4.subscriptionSucess(sub, 1);
        finish();
    }
    // Call Subscitpionsucess Response

    public void showSucessmessage(Object o) {
        String code = ((RestResponse) o).getCode();
        String Response = ((RestResponse) o).getDescription();
        Toast.makeText(this, Response, Toast.LENGTH_SHORT).show();
        if (code.equals(Constans.response200)) {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((userid == null)) {
            userid = SharedPreferenceUtil.getInstance(this).getUserid();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        if (smileycheckedvalue != null) {
            if (smileycheckedvalue.equals("0")) {
                SubscriptionSucess sub = new SubscriptionSucess();
                sub.setUserid(userid);
                sub.setCheckoutdate(todaydates);
                sub.setEnablesmileycash("0");
                smileyserveDataSource4.subscriptionSucess(sub, 1);
            } else {
                SubscriptionSucess sub = new SubscriptionSucess();
                sub.setUserid(userid);
                sub.setCheckoutdate(todaydates);
                sub.setEnablesmileycash("1");
                smileyserveDataSource4.subscriptionSucess(sub, 1);
            }
        } else {
            SubscriptionSucess sub = new SubscriptionSucess();
            sub.setUserid(userid);
            sub.setCheckoutdate(todaydates);
            sub.setEnablesmileycash("0");
            smileyserveDataSource4.subscriptionSucess(sub, 1);
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        finish();
        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
        Intent main =new Intent(this,MainActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);



    }

    public void showpaymnetper(Object o) {
        paymetpercentage=((PaymentRespone)o).getPayment_percentage();
        tax=((PaymentRespone)o).getTax();
    }
}


