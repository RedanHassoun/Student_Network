package com.mohred.studentnetwork.page_courses;

import android.os.Bundle;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;

/**
 * Created by Redan on 1/7/2017.
 */

public class ActivityCourses extends ActivityBase
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(FragmentCourses.newInstance());
        setTitle(getString(R.string.courses_page));

        viewNavigation.setCheckedItem(R.id.drawer_courses);
    }
}
