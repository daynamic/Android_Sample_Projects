package com.test.asharm93.spiderg1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by asharm93 on 5/4/2016.
 */
public class DocumentPagersAdapter extends FragmentPagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    public DocumentPagersAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
     }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
            {
              return new Received();
            }
            case 1:
            {
                return new Sent();
            }
            case 2:
            {
              return new Pending();
            }
        }
        return null;
   }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
