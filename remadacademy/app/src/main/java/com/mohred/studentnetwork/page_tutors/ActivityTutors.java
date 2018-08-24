package com.mohred.studentnetwork.page_tutors;

import android.os.Bundle;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.User;

/**
 * Created by Redan on 1/21/2017.
 */
public class ActivityTutors extends ActivityBase
{
    private static final String TAG = "activtiy_tutors";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.tutoring));
        viewNavigation.setCheckedItem(R.id.drawer_tutors);
        User user = DataManager.getInstance().getCurrentUser(getBaseContext());

        if(user.isTutor()){
            Bundle arguements = getIntent().getExtras();
            initFragment(FragmentTutorTabsPager.newInstance(),arguements);
        }else {
            initFragment(FragmentTutorsList.newInstance());
        }
    }
}
