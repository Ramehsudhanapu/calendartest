package coms.landaryapp.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import coms.landaryapp.R;
import coms.landaryapp.activity.adapter.MyordertabsAdapter;


public class MyOrderActivity extends ActionBarActivity {
    private static final Object LOG = MyOrderActivity.class.getSimpleName();
    ProgressDialog pDialog;
    Toolbar mToolbar;
    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbarcolor));
        TextView textView1 = (TextView)findViewById(R.id.toolbar_titleorders);
        textView1.setText(R.string.myorders);
        textView1.setTextColor(getResources().getColor(R.color.toolbartextcolor));
        final TabLayout tabLayout3 = (TabLayout)findViewById(R.id.tab_layout3);
        tabLayout3.addTab(tabLayout3.newTab().setText("Individual Order"));
        tabLayout3.addTab(tabLayout3.newTab().setText("Package Order"));
        tabLayout3.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager3 = (ViewPager) findViewById(R.id.viewpager3);
        final MyordertabsAdapter adapter3 = new MyordertabsAdapter(getSupportFragmentManager(), tabLayout3.getTabCount());
        viewPager3.setAdapter(adapter3);
        viewPager3.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout3));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            position = bundle.getString("status");
        }
        if (position != null) {
            viewPager3.setCurrentItem(Integer.parseInt(position));
            tabLayout3.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    viewPager3.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }

        if(position==null) {
            tabLayout3.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager3.setCurrentItem(tab.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        mToolbar.setNavigationIcon(R.drawable.navigationiconblackback);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i =new Intent(MyOrderActivity.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
          }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
@Override
    public void onBackPressed() {
            super.onBackPressed();
        Intent Main = new Intent(MyOrderActivity.this, MainActivity.class);
        Main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(Main);
       finish();
}



}

