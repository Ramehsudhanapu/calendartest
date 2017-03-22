package com.richlabz.smileyserve.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.richlabz.smileyserve.R;
import com.richlabz.smileyserve.activity.Constans;
import com.richlabz.smileyserve.adapter.NavigationDrawerAdapter;
import com.richlabz.smileyserve.datasource.SmileyserveDataSource;
import com.richlabz.smileyserve.entities.NavDrawerItem;
import com.richlabz.smileyserve.entities.Userdetails;
import com.richlabz.smileyserve.entities.loginRestResponse;
import com.richlabz.smileyserve.utilities.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentDrawer extends Fragment {
    private static final String LOG = FragmentDrawer.class.getSimpleName();
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private static String[] home = null;
    private static String userid;
    private FragmentDrawerListener drawerListener;
    SmileyserveDataSource smileyserveDataSource = new SmileyserveDataSource(this);
    int[] drawericons = new int[]{R.drawable.home, R.drawable.profile, R.drawable.subscription, R.drawable.calenmder, R.drawable.smileycash,R.drawable.faqs, R.drawable.call, R.drawable.signout};
    int[] homeicon = new int[]{R.drawable.home,R.drawable.loginicon};
    TextView proifilename;
    private Matcher m;

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }
    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        if (userid != null) {
            for (int i = 0; i < titles.length; i++) {
                NavDrawerItem navItem = new NavDrawerItem();
                navItem.setTitle(titles[i]);
                data.add(navItem);
            }
            // this for guest login
        } else {
            for (int i = 0; i < home.length; i++) {
                NavDrawerItem navItem = new NavDrawerItem();
                navItem.setTitle(home[i]);
                data.add(navItem);
            }
        }
        return data;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = SharedPreferenceUtil.getInstance(getActivity()).getUserid();
        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        // this for guest login
        home = getActivity().getResources().getStringArray(R.array.nav_home_labels);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        proifilename = (TextView) layout.findViewById(R.id.proifilename);
        if (userid != null) {
            adapter = new NavigationDrawerAdapter(getActivity(), getData(), drawericons);
        }
        // this for guest login
        else {
            adapter = new NavigationDrawerAdapter(getActivity(), getData(), homeicon);
        }
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (userid != null) {
            smileyserveDataSource.getprofile(userid, 2);
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });}
    public void showresponse(Object o) {
        String response = ((loginRestResponse) o).getCode();
        if (response.equals(Constans.Response_200)) {
            Userdetails userdetails = ((loginRestResponse) o).getUser_result();
            String name = userdetails.getName();
            StringBuffer stringbf = new StringBuffer();

            if ( name  !=null) {
                m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher( name );
            }
            while (m.find()) {
                m.appendReplacement(stringbf, m.group(1).toUpperCase() + m.group(2).toLowerCase());
            }

//               holder.foodname.setText();
//            String Uppername = name.toUpperCase();

            proifilename.setText(m.appendTail(stringbf).toString());
        }
    }
    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }
    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
    @Override
    public void onResume() {
        super.onResume();
        String userid1 = SharedPreferenceUtil.getInstance(getActivity()).getUserid();
        if ((userid1 != null)) {
            smileyserveDataSource.getprofile(userid1, 2);
        }
    }
}

