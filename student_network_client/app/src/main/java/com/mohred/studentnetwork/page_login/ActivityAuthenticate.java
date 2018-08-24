package com.mohred.studentnetwork.page_login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.page_main.ActivityMain;

import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_CHOOSE_ORGANIZATION;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_LOGIN;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_REGISTER;

/**
 * This activity is responsible for authenticating the user,
 * it has a splash fragment, login fragment, register fragment
 * Created by Redan on 11/25/2016.
 */
public class ActivityAuthenticate extends AppCompatActivity
{
    private static final String TAG = "activity_auth";
    private FragmentManager manager;
    DataManager dataManager = DataManager.getInstance();
    private GoogleApiClient mGoogleApiClient;
    private Fragment currentFragment = null;
    private ProgressBar progressBar = null;
    private int RegisterType=-1;
    public int getRegisterType() {
        return RegisterType;
    }
    public void setRegisterType(int registerType) {
        RegisterType = registerType;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        forceRTLIfSupported();

        initFragment();

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private void initFragment()
    {
        manager = getSupportFragmentManager();
        FragmentLogin fragmentSplash = FragmentLogin.newInstance();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentSplash)
                                                .addToBackStack(FRAGMENT_LOGIN)
                                                 .commit();
    }

    public void showProgressBar()
    {
        if(progressBar == null)
        {
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        }

        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar()
    {
        if(progressBar == null)
        {
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        }

        progressBar.setVisibility(View.GONE);
    }

    public void replaceFragment(String fragmentName,Bundle arguments,boolean addToBackStack)
    {
        switch (fragmentName)
        {
            case FRAGMENT_LOGIN:
                currentFragment = FragmentLogin.newInstance();
                break;

            case FRAGMENT_REGISTER:
                currentFragment = FragmentRegister.newInstance();
                break;

            case FRAGMENT_CHOOSE_ORGANIZATION:
                currentFragment = FragmentChooseOrganizationAndFaculty.newInstance();
                break;

            default:
                break;
        }
        currentFragment.setArguments(arguments);

        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out); // TODO - add animation
        transaction.replace(R.id.fragment_container, currentFragment);
        if(addToBackStack)
            transaction.addToBackStack(fragmentName);
        transaction.commit();
    }

    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void logout()
    {
        try
        {
            LoginManager.getInstance().logOut();
            Auth.GoogleSignInApi.signOut( getmGoogleApiClient())
                    .setResultCallback( new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(Status status)
                        {

                        }
                    });
        }
        catch (Exception ex)
        {

        }

        if( getmGoogleApiClient()!=null)
        {
            getmGoogleApiClient().stopAutoManage(this);
            getmGoogleApiClient().disconnect();
        }

        //PrefUtils.clearCurrentUser(this); // TODO - make this work


        replaceFragment(FRAGMENT_LOGIN,null,false);
    }

    public GoogleApiClient getmGoogleApiClient()
    {
        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient)
    {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    /**
     * This method opens the main activity after the user has been authenticated
     */
    public void openApp(User user)
    {
        Log.d(TAG,"Opening app for user:"+user);
        if(dataManager.getCurrentUser(this)==null)
            dataManager.setCurrentUser(user,this);

        Intent intent = new Intent(this, ActivityMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

}
