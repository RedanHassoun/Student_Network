package com.mohred.studentnetwork.page_courses;

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
import com.mohred.studentnetwork.common.AppUtils;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_ID_COURSE;


/**
 * Created by Redan on 12/2/2016.
 */
public class FragmentTabsPager extends Fragment
{
    private static final int NUM_ITEMS = 2;
    private AdapterCustom pagerAdapter;
    private ViewPager pager;
    private TabLayout tabLayout;
    private View view;
    private String courseId = null;
    private static final String TAG = "tabs_pager";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_assets_top_level,container,false);

        Bundle args = getArguments();
        courseId = args.getString(ARG_ID_COURSE);

        Log.d(TAG,"course id = "+courseId);

        if(courseId == null){
            AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
        }

        initTabs();

        return view;
    }

    private void initTabs()
    {
        Log.d(TAG,"Initiating tabs");
        pagerAdapter = new AdapterCustom(getChildFragmentManager());
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setVisibility(View.VISIBLE);
        pager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_assets);
        tabLayout.setupWithViewPager(pager);
        Resources res = getResources();
        tabLayout.getTabAt(0).setText(getString(R.string.about_course));
        tabLayout.getTabAt(1).setText(getString(R.string.course_material));
        tabLayout.setSelectedTabIndicatorColor(res.getColor(R.color.light_blue));
        Log.d(TAG,"tabs initiated");
    }


    private class AdapterCustom extends FragmentStatePagerAdapter
    {
        public AdapterCustom(FragmentManager fm)
        {
            super(fm);
            Log.d(TAG,"Building adapter for pager");
        }

        @Override
        public int getCount()
        {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position)
        {
            Log.d(TAG,"Getting item on position : "+position);
            if(position==0)
                return FragmentCourseAbout.newInstance(courseId);
            return FragmentCourseMaterial.newInstance(courseId);
        }
    }

}
