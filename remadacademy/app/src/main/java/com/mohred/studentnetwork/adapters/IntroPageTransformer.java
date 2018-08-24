package com.mohred.studentnetwork.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.mohred.studentnetwork.R;

/**
 * Created by Redan on 11/25/2016.
 */
public class IntroPageTransformer implements ViewPager.PageTransformer
{
    private View mainView;
    private TextView[] dots = new TextView[3];

    public IntroPageTransformer(View theMainView)
    {
        this.mainView = theMainView;
    }

    @SuppressLint("NewApi")
    @Override
    public void transformPage(View page, float position)
    {
        // Get the page index from the tag. This makes
        // it possible to know which page index you're
        // currently transforming - and that can be used
        // to make some important performance improvements.
        int pagePosition = 0;
        if(page.getTag()!=null)
            pagePosition = (Integer) page.getTag();

        // Here you can do all kinds of stuff, like get the
        // width of the page and perform calculations based
        // on how far the user has swiped the page.
        int pageWidth = page.getWidth();
        float pageWidthTimesPosition = pageWidth * position;
        float absPosition = Math.abs(position);

        // Now, we want the image to move to the right,
        // i.e. in the opposite direction of the rest of the
        // content while fading out
        View firstPage = page.findViewById(R.id.first_page);
        View secondPage = page.findViewById(R.id.second_page);
        View thirdPage = page.findViewById(R.id.third_page);

        /*
         * Initialize the three dots which are placed on bottom of
         * the screen
         */
        dots[0] = (TextView) mainView.findViewById(R.id.dot1);
        dots[1] = (TextView) mainView.findViewById(R.id.dot2);
        dots[2] = (TextView) mainView.findViewById(R.id.dot3);



        // Now it's time for the effects
        if (position <= -1.0f || position >= 1.0f)
        {

            // The page is not visible. This is a good place to stop
            // any potential work / animations you may have running.

        } else if (position == 0.0f)
        {
            if(firstPage != null)
            {
                dots[0].setTextColor(Color.parseColor("#FFFFFF"));
                dots[1].setTextColor(Color.parseColor("#000000"));
                dots[2].setTextColor(Color.parseColor("#000000"));
            }
            if(secondPage != null)
            {
                dots[0].setTextColor(Color.parseColor("#000000"));
                dots[1].setTextColor(Color.parseColor("#FFFFFF"));
                dots[2].setTextColor(Color.parseColor("#000000"));
            }

            if(thirdPage != null)
            {
                dots[0].setTextColor(Color.parseColor("#000000"));
                dots[1].setTextColor(Color.parseColor("#000000"));
                dots[2].setTextColor(Color.parseColor("#FFFFFF"));

            }
            // The page is selected. This is a good time to reset Views
            // after animations as you can't always count on the PageTransformer
            // callbacks to match up perfectly.

        } else
        {

            // The page is currently being scrolled / swiped. This is
            // a good place to show animations that react to the user's
            // swiping as it provides a good user experience.



            // Now the description. We also want this one to
            // fade, but the animation should also slowly move
            // down and out of the screen
            View description = page.findViewById(R.id.description);
            description.setTranslationY(-pageWidthTimesPosition / 2f);
            description.setAlpha(1.0f - absPosition);



            // We're attempting to create an effect for a View
            // specific to one of the pages in our ViewPager.
            // In other words, we need to check that we're on
            // the correct page and that the View in question
            // isn't null.
            if(page.getTag()!=null)
            {
                if (pagePosition == 0 && firstPage != null)
                {

                    firstPage.setAlpha(1.0f - absPosition);
                    firstPage.setTranslationX(-pageWidthTimesPosition * 1.5f);

                }

                if(pagePosition == 0 && secondPage != null)
                {

                    secondPage.setAlpha(1.0f - absPosition);
                    secondPage.setTranslationY(-pageWidthTimesPosition * 1.5f);


                }
                if(pagePosition == 0 && thirdPage != null)
                {

                }
            }


            // Finally, it can be useful to know the direction
            // of the user's swipe - if we're entering or exiting.
            // This is quite simple:
            if (position < 0)
            {

                // Create your out animation here


            }
            else
            {
                // Create your in animation here
            }
        }
    }
}
