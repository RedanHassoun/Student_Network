package com.mohred.studentnetwork.page_profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.managers.DataManager;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_PROGILE_USER_MAIL;


/**
 * Created by Redan on 12/23/2016.
 */

public class ActivityProfile extends ActivityBase
{
    private String TAG = "my_profile";
    public static final String FRAGMENT_SHOW_PROFILE = "FragmentShowProfile";
    public static final String FRAGMENT_EDIT_PROFILE = "FragmentEditProfile";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FragmentShowProfile fragment = new FragmentShowProfile();
        Bundle arguments = new Bundle();
        String userEmail = DataManager.getInstance().getCurrentUser(getBaseContext()).getEmail();
        arguments.putString(ARG_PROGILE_USER_MAIL,userEmail);
        fragment.setArguments(arguments);
        initFragment(fragment);

        setTitle(getString(R.string.profile_page));

        viewNavigation.setCheckedItem(R.id.drawer_profile);
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