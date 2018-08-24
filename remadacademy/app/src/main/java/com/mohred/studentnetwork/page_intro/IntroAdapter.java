package com.mohred.studentnetwork.page_intro;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Redan on 11/25/2016.
 */
public class IntroAdapter extends FragmentPagerAdapter
{
    //..
    public IntroAdapter(FragmentManager fm)
    {
        super(fm);

    }

    @Override
    public Fragment getItem(int position)
    {

        switch (position)
        {
            case 0:
                return FragmentIntro.newInstance(Color.parseColor("#ffffff"), position); // white
            case 1:
                return FragmentIntro.newInstance(Color.parseColor("#0a80d1"), position); // blue
            default:
                return FragmentIntro.newInstance(Color.parseColor("#4CAF50"), position); // green
        }
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}
