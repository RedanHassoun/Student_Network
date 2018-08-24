package com.mohred.studentnetwork.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.dialog.DialogFragmentError;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.page_courses.ActivityCourses;
import com.mohred.studentnetwork.page_friends.ActivityFindPeople;
import com.mohred.studentnetwork.page_login.ActivityAuthenticate;
import com.mohred.studentnetwork.page_main.ActivityMain;
import com.mohred.studentnetwork.page_profile.ActivityProfile;
import com.mohred.studentnetwork.page_tutors.ActivityTutors;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Redan on 12/1/2016.
 */
public class ActivityBase extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener
{
    protected NavigationView viewNavigation;
    protected FragmentManager manager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar myToolbar;
    protected String activityName = getClass().getName();
    private ProgressBar progressBar=null;
    private static final String TAG= "base_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);


        overridePendingTransition(0, 0);

        initViews();

    }


    private void initViews()
    {
        /* init progress bar */
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        /* init action-bar */
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /* init the drawer */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.open,R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        viewNavigation = (NavigationView) findViewById(R.id.left_drawer);
        viewNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                //Snackbar.make(R.id.content_frame, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                selectItem(menuItem);
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        RelativeLayout headerView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.header_drawer, null);

        /* Set the profile image */
        CircleImageView profileImage = (CircleImageView)  headerView.findViewById(R.id.profile_image);
        User user = DataManager.getInstance().getCurrentUser(this);

        try {
            if(!user.getImageURL().equals(""))
                Picasso.with(this)
                        .load(user.getImageURL())
                        .placeholder(R.drawable.ic_perm_identity_black_24dp)
                        .error(R.drawable.ic_perm_identity_black_24dp)
                        .into(profileImage);
        }catch (NullPointerException ex)
        {

        }


        /* Set the username */
        TextView textUsername = (TextView) headerView.findViewById(R.id.text_username);
        textUsername.setText(user.getUsername());

        viewNavigation.addHeaderView(headerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId() == android.R.id.home) {
            //Timber.d("Home pressed");
            AppUtils.showToastMessage(this,"home pressed !");
        }


        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items_toolbar, menu);
        return true;
    }

    protected void initFragment(Fragment fragment)
    {
        manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment)
                .commit();
    }

    protected void initFragment(Fragment fragment,Bundle arguments)
    {
        manager = getSupportFragmentManager();
        fragment.setArguments(arguments);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public void onBackStackChanged()
    {
        if (manager.getBackStackEntryCount() > 0)
            manager.getFragments().get(manager.getBackStackEntryCount() - 1).onResume();

    }


    public void openActivity(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void showErrorMessage(String message)
    {
        DialogFragmentError newFragment = DialogFragmentError.newInstance(message);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void logout()
    {
        DataManager.getInstance().clearCurrentUser(this);

        openActivity(ActivityAuthenticate.class);
    }

    private void selectItem(MenuItem menuItem)
    {
        int pageId = menuItem.getItemId();

        viewNavigation.setCheckedItem(menuItem.getItemId());

        switch (pageId)
        {
            case R.id.drawer_home:
                if(! activityName.equals(ActivityMain.class.getName()))
                {
                    Intent intent = new Intent(this, ActivityMain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;

            case R.id.drawer_profile:
                if(! activityName.equals(ActivityProfile.class.getName()))
                    openActivity(ActivityProfile.class);
                break;

            case R.id.drawer_courses:
                if(! activityName.equals(ActivityCourses.class.getName()))
                    openActivity(ActivityCourses.class);
                break;

            case R.id.drawer_logout:
                logout();
                break;

            case R.id.drawer_tutors:
                if(! activityName.equals(ActivityTutors.class.getName()))
                {
                    AppUtils.openActivity(this,ActivityTutors.class);
                }
                break;
            case R.id.drawer_find_people:
                if(! activityName.equals(ActivityFindPeople.class.getName()))
                    openActivity(ActivityFindPeople.class);
                break;

            case R.id.drawer_share_app:
                shareIt();
                break;

        }

    }


    public void replaceFragment(Fragment fragment,String fragmentName,Bundle arguments,boolean addToBackStack)
    {
        fragment.setArguments(arguments);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        transaction.replace(R.id.content_frame, fragment);
        if(addToBackStack)
            transaction.addToBackStack(fragmentName);
        transaction.commit();
    }


    public void addFragment(Fragment fragment,String fragmentName,Bundle arguments,boolean addToBackStack)
    {
        fragment.setArguments(arguments);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        transaction.add(R.id.content_frame, fragment);
        if(addToBackStack)
            transaction.addToBackStack(fragmentName);
        transaction.commit();
    }

    public void popBackStack(FragmentManager manager){
        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void showProgressBar()
    {
        if(progressBar == null){
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        }

        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar()
    {
        if(progressBar == null){
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        }

        progressBar.setVisibility(View.GONE);
    }

    /**
     * This method will contain the implementation code for sharing content
     * from the application
     */
    private void shareIt()
    {
		/*
		 * Create a Send Intent
		 */
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

		/*
		 * Set the sharing type as plain text
		 */
        sharingIntent.setType("text/plain");

		/*
		 * Build the Share strings
		 */

        String shareSubject = getString(R.string.share_subject);
        String shareContent = getString(R.string.share_content);

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareContent);

        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_with)));
    }
}
