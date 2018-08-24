package com.mohred.studentnetwork.page_friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.page_profile.FragmentEditProfile;
import com.mohred.studentnetwork.page_profile.FragmentShowProfile;
import static com.mohred.studentnetwork.page_profile.ActivityProfile.FRAGMENT_EDIT_PROFILE;
import static com.mohred.studentnetwork.page_profile.ActivityProfile.FRAGMENT_SHOW_PROFILE;

public class ActivityFindPeople extends ActivityBase
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initFragment(new FragmentPeopleList());
        setTitle(getString(R.string.find_people));
        viewNavigation.setCheckedItem(R.id.drawer_find_people);
    }

    public void replaceFragment(String fragmentName,Bundle arguments,boolean addToBackStack)
    {
        Fragment fragment = null;
        switch (fragmentName)
        {
            case FRAGMENT_EDIT_PROFILE:
                fragment  = new FragmentEditProfile();
                break;
            case FRAGMENT_SHOW_PROFILE:
                fragment  = new FragmentShowProfile();
                break;
            default:
                break;
        }

        fragment.setArguments(arguments);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        if(addToBackStack)
            transaction.addToBackStack(fragmentName);
        transaction.commit();

    }









}
