package com.mohred.studentnetwork.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mohred.studentnetwork.R;
import com.squareup.picasso.Picasso;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_IMAGE_URL;

/**
 * Created by Redan on 1/1/2017.
 */
public class ActivityFullScreenImage extends AppCompatActivity
{
    private String imageURL;
    private ImageView imaegView;
    private Button exitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        initImageURLFromArguments();
        initViews();
    }

    private void initViews()
    {
        imaegView = (ImageView) findViewById(R.id.image_to_show);

        Picasso.with(this)
                .load(imageURL)
                .into(imaegView);

        exitButton =  (Button) findViewById(R.id.button_close);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityFullScreenImage.this.finish();
            }
        });
    }

    private void initImageURLFromArguments()
    {
        Bundle b = getIntent().getExtras();
        if(b != null)
            imageURL = b.getString(ARG_IMAGE_URL);
    }
}
