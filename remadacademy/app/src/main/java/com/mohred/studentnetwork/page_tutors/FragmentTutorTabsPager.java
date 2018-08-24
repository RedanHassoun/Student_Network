package com.mohred.studentnetwork.page_tutors;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohred.studentnetwork.R;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_GOTO_REQUESTS;


/**
 * TODO - continue
 * Created by Redan on 12/2/2016.
 */
public class FragmentTutorTabsPager extends Fragment
{
    private static final int NUM_ITEMS = 4;
    private AdapterCustom pagerAdapter;
    private ViewPager pager;
    private TabLayout tabLayout;
    private View view;
    private static final String TAG = "fragment_tutor_pager";

    public static final FragmentTutorTabsPager newInstance()
    {
        FragmentTutorTabsPager fragment = new FragmentTutorTabsPager();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_assets_top_level,container,false);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        initTabs();


        Log.d(TAG,"getting arguments");
        Bundle args = getArguments();
        if(args != null){
            Log.d(TAG,"arguments not null");
            String gotoTutorTab = args.getString(ARG_GOTO_REQUESTS);

            if(gotoTutorTab != null){
                Log.d(TAG,"arguments not null");
                TabLayout.Tab tab = tabLayout.getTabAt(3);
                tab.select();
                Log.d(TAG,"tab selected");
            }
        }

    }

    private void initTabs()
    {
        pagerAdapter = new AdapterCustom(getChildFragmentManager());
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setVisibility(View.VISIBLE);
        pager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_assets);
        tabLayout.setupWithViewPager(pager);
        Resources res = getResources();
        tabLayout.getTabAt(0).setText(getString(R.string.tutors_list));
        tabLayout.getTabAt(1).setText(getString(R.string.tutored_coures_list));
        tabLayout.getTabAt(2).setText(getString(R.string.tutored_students_list));
        tabLayout.getTabAt(3).setText(getString(R.string.requests));
        tabLayout.setSelectedTabIndicatorColor(res.getColor(R.color.light_blue));
    }


    private class AdapterCustom extends FragmentStatePagerAdapter
    {
        public AdapterCustom(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position)
        {
            Log.d(TAG,"getting item :  "+position);
            if(position == 0)
                return FragmentTutorsList.newInstance();
            else if (position == 1)
                return FragmentListTutoredCourses.newInstance();
                else  if (position == 2 )
                    return  FragmentTutoredStudents.newInstance();
                        else
                            return FragmentTutorRequests.newInstance();
        }
    }

}
