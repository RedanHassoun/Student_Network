package com.mohred.studentnetwork.page_tutors;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.page_courses.FragmentCourseAbout;
import com.mohred.studentnetwork.page_courses.FragmentCourseMaterial;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_ID_COURSE;

/**
 * Created by Redan on 1/28/2017.
 */
public class FragmentSingleCourse extends Fragment
{
    private static final int NUM_ITEMS = 2;
    private AdapterCustom pagerAdapter;
    private ViewPager pager;
    private TabLayout tabLayout;
    private View view;
    private String courseId = null;
    private static final String TAG = "single_course_frag";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_assets_top_level,container,false);

        Bundle args = getArguments();
        courseId = args.getString(ARG_ID_COURSE);
        if(courseId == null){
            AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
        }

        initTabs();

        return view;
    }

    private void initTabs()
    {
        pagerAdapter = new AdapterCustom(getActivity().getSupportFragmentManager());
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setVisibility(View.VISIBLE);
        pager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_assets);
        tabLayout.setupWithViewPager(pager);
        Resources res = getResources();
        tabLayout.getTabAt(0).setText(getString(R.string.about_course));
        tabLayout.getTabAt(1).setText(getString(R.string.course_material));
        tabLayout.setSelectedTabIndicatorColor(res.getColor(R.color.light_blue));
    }


    private class AdapterCustom extends FragmentPagerAdapter
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
            Log.d(TAG,"getting item + "+position);
            if(position==0)
                return FragmentCourseAbout.newInstance(courseId);
            return FragmentCourseMaterial.newInstance(courseId);
        }
    }
}
