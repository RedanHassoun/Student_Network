package com.mohred.studentnetwork.page_splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.page_login.ActivityAuthenticate;
import com.mohred.studentnetwork.page_intro.ActivityIntro;
import com.mohred.studentnetwork.page_main.ActivityMain;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_OPEN_APP;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.TYPE_RUN;
import static com.mohred.studentnetwork.common.AppConstants.SharedPrefsKeys.PREF_FIRST_LAUNCH;

/**
 * This activity is responsible for loading the basic information from the
 * server.
 * Created by Redan on 11/25/2016.
 */
public class ActivitySplash extends AppCompatActivity
{
    private DataManager dataManager = DataManager.getInstance();
    private SharedPreferences prefs = null;
    private static final String TAG = "splash_tag";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();

        prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG,"11111");
        doAppropriateOperation();
    }

    private void doAppropriateOperation()
    {
        Bundle b = getIntent().getExtras();

        if (b == null) {
            new TastGetBasicInfo().execute();
            return;
        }
        String operation = b.getString(TYPE_RUN);
        if(operation != null)
        {
            if (operation.equals(ARG_OPEN_APP)){
                AppUtils.openActivity(this,ActivityMain.class);
            }
        }
        else
        {
            new TastGetBasicInfo().execute();
        }

    }

    private void initViews()
    {
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }


    private class TastGetBasicInfo extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            openAppropriateActivity();

            return null;
        }
    }

    private void openAppropriateActivity()
    {
        /*
            Check if first launch
         */
        if (prefs.getBoolean(PREF_FIRST_LAUNCH, true))
        {
            /* then open the introduction screen  */
            Intent intent = new Intent(ActivitySplash.this, ActivityIntro.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            prefs.edit().putBoolean(PREF_FIRST_LAUNCH, false).commit();
            finish();
        }
        else
        {
            if(dataManager.getCurrentUser(this)!=null)
            {
                Intent intent = new Intent(ActivitySplash.this, ActivityMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(ActivitySplash.this, ActivityAuthenticate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }
    }
}
