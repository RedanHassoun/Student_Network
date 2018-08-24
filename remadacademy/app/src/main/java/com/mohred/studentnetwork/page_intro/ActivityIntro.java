package com.mohred.studentnetwork.page_intro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.adapters.IntroPageTransformer;
import com.mohred.studentnetwork.page_login.ActivityAuthenticate;

/**
 * Created by Redan on 11/25/2016.
 */
public class ActivityIntro extends AppCompatActivity implements View.OnClickListener
{
    private ViewPager mViewPager;
    private Button skipButton;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_intro);

        initViews();
    }

    private void initViews()
    {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

        // Set a PageTransformer
        mViewPager.setPageTransformer(false,
                new IntroPageTransformer(this.getWindow()
                        .getDecorView()
                        .findViewById(android.R.id.content)));

	      /*
	       * Configure the skip button
	       */
        skipButton = (Button) findViewById(R.id.skipButton);
        skipButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.skipButton:
				/*
				 * Start the register activity
				 */
                startActivity(new Intent(ActivityIntro.this,ActivityAuthenticate.class));
                this.finish();
                break;
            default:
                break;
        }
    }
}
