package com.mohred.studentnetwork.page_fullscreen_image;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.squareup.picasso.Picasso;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_IMAGE_URL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_IS_PROFILE;

/**
 * Created by Redan on 1/14/2017.
 */

public class ActivityFullScreenImage extends AppCompatActivity
{
    private ImageView imageView;
    private Button buttonClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        initViews();
        handleArguments();
    }

    private void initViews()
    {
        imageView = (ImageView) findViewById(R.id.image_to_show);
        buttonClose = (Button) findViewById(R.id.button_close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityFullScreenImage.this.finish();
            }
        });
    }

    private void handleArguments()
    {
        Bundle bundle = getIntent().getExtras();
        String imageURL = null;
        boolean isProfileImage = false;

        if(bundle != null){
            imageURL = bundle.getString(ARG_IMAGE_URL);
            isProfileImage = bundle.getBoolean(ARG_IS_PROFILE,false);
        }
        showAppropriateImage(imageURL,isProfileImage);
    }

    private void showAppropriateImage(String imageURL, boolean isProfileImage)
    {
        if(imageURL != null){
            if(imageURL.equals("")) {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_app_icon_big);
                imageView.setImageDrawable(drawable);
            } else{
                Picasso.with(this)
                        .load(imageURL)
                        .placeholder( R.drawable.progress_animation )
                        .into(imageView);
            }

        }else {

            if(isProfileImage){
                Drawable drawable = getResources().getDrawable(R.drawable.ic_app_icon_big);
                imageView.setImageDrawable(drawable);
            }else
                showErrorMessage();
        }
    }

    private void showErrorMessage()
    {
        AppUtils.showToastMessage(getBaseContext(),getString(R.string.general_error_message));
    }
}
