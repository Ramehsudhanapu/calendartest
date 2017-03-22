package com.richlabz.smileyserve.activity;

import android.content.Intent;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.richlabz.smileyserve.R;
import com.richlabz.smileyserve.datasource.CartResponse;
import com.richlabz.smileyserve.datasource.SmileyserveDataSource;
import com.richlabz.smileyserve.fragment.FragmentDrawer;
import com.richlabz.smileyserve.fragment.HomeFragment;
import com.richlabz.smileyserve.fragment.LogoutFragment;

import com.richlabz.smileyserve.utilities.SharedPreferenceUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private CharSequence mDrawerTitle;
    private FragmentDrawer drawerFragment;
    @Bind(R.id.drawerList)
    RecyclerView drawerlist;
    @Bind(R.id.toolbar_title)
    TextView textView;
    String value;
    SmileyserveDataSource smileyserveDataSource = new SmileyserveDataSource(this);
    @Bind(R.id.cartcount)
    TextView txt_cartcount;
    boolean doubleBackToExitPressedOnce = false;
    String userid;
    private String apartmentid,orderdate;
    @OnClick(R.id.cart)
    void cart() {

            Intent i = new Intent(this, CartActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView.setText("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        userid = SharedPreferenceUtil.getInstance(this).getUserid();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            value = bundle.getString("id");
            apartmentid=bundle.getString("apartment");
            orderdate=bundle.getString("orderdate");
        }
        if (userid != null) {
            if (value != null) {
                if (value.equals("2")) {
                    displayView(2);
                } else if (value.equals("1")) {
                    displayView(1);
                } else if (value.equals("3")) {
                    displayView(3);
                } else if (value.equals("4")) {
                    displayView(4);
                }
            }
                else {
                    displayView(0);
                }
        }
            else {
                displayView(0);
            }

            if (userid != null) {
                smileyserveDataSource.getCartCount(userid, 1);
            }
        }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = null;
        if(userid==null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    title = getString(R.string.app_name);
                    Bundle args = new Bundle();
                    args.putString("apartment", apartmentid);
                    fragment.setArguments(args);
                    title = getString(R.string.app_name);
                    break;
                case 1:
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    title = getString(R.string.nav_item_login);
                    finish();
                    break;

            }
        }
            else {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putString("apartment", apartmentid);
                    args.putString("orderdate",orderdate);
                    fragment.setArguments(args);
                    title = getString(R.string.app_name);
                    break;
                case 1:
                    Intent  intent=new Intent(this,ProfileActivity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);
                    break;
                case 2:
                    Intent  subscribe=new Intent(this,SubcriptionActivity.class);
                    subscribe.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(subscribe);
                    break;
                case 3:
                    Intent  calendar=new Intent(this,CalendarActivity.class);
                    calendar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(calendar);
                    break;
                case 4:
                    Intent smileycash=new Intent(this,SmileyCashActivity.class);
                    smileycash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(smileycash);
                    break;
                case 5:
                    Intent faq=new Intent(this,FAQActivity.class);
                    faq.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(faq);
                    break;
                case 6:
                    Intent  contact=new Intent(this,ContactActivity.class);
                    contact.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(contact);
                    break;
                case 7:
                    fragment = new LogoutFragment();
                    title = getString(R.string.nav_item_Logout);
                    break;
            }
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, Constans.backpressed, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void showCartCountDisplay(Object o) {
        String response = ((CartResponse) o).getCart_count();
        if (response != null) {
            if (response.equals("0")) {
                txt_cartcount.setVisibility(View.GONE);
            } else {
                txt_cartcount.setVisibility(View.VISIBLE);
                 txt_cartcount.setText(response);

            }
        }

    }
}