package coms.landaryapp.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

//import coms.landaryapp.activity.adapter.fragment.BasicTabs_Fragment;
//import coms.landaryapp.activity.adapter.fragment.ClassicTabs_Fragment;
import coms.landaryapp.activity.adapter.fragment.Individual_Fragment;
import coms.landaryapp.activity.adapter.fragment.Package1_Fragment;

/**
 * Created by Richlabz on 09-01-2016.
 */
public class MyordertabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MyordertabsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Individual_Fragment tab1 = new Individual_Fragment();
                return tab1;
            case 1:
                Package1_Fragment tab2 = new Package1_Fragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return mNumOfTabs;

    }
}
