package com.mohred.studentnetwork.page_courses;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.app.AppController;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.model.CourseMaterial;
import com.mohred.studentnetwork.page_fullscreen_image.ActivityFullScreenImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_IMAGE_URL;

/**
 * Created by Redan on 1/14/2017.
 */

public class AdapterMaterialList extends BaseAdapter
{
    private static final String TAG = "feed_adapter";
    private Activity activity;
    private LayoutInflater inflater;
    private List<CourseMaterial> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public AdapterMaterialList(Activity activity, List<CourseMaterial> feedItems)
    {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount()
    {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location)
    {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_list_course_material, null);

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon_item) ;
        TextView title = (TextView) convertView.findViewById(R.id.text_title);

        TextView url = (TextView) convertView.findViewById(R.id.text_url);
        ImageView image = (ImageView) convertView
                .findViewById(R.id.image_material);

        CourseMaterial currentItem = feedItems.get(position);
        /*
            Set color for the icon
         */
        Drawable mDrawable = activity.getResources().getDrawable(R.drawable.ic_local_library_white_24dp);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(activity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY));
        icon.setImageDrawable(mDrawable);

        /*
            Set the title
         */
        title.setText(currentItem.getTitle());

        /*
            Set the external URL
         */
        String theURL = currentItem.getExternalURL();
        if(theURL == null || theURL.equals("")){
            url.setVisibility(View.GONE);
        }else {
            url.setText(Html.fromHtml("<a href=\"" + currentItem.getExternalURL() + "\">"
                    + currentItem.getExternalURL() + "</a> "));
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        }

        /*
            Set the image
         */
        final String theImageURL = currentItem.getImageURL();
        if(theImageURL == null || theImageURL.equals("")){
            image.setVisibility(View.GONE);
        }else {
            Picasso.with(activity)
                    .load(theImageURL)
                    .placeholder(R.drawable.loading_icon)
                    .error(R.drawable.ic_perm_identity_black_24dp)
                    .into(image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ARG_IMAGE_URL,theImageURL);
                    AppUtils.openActivity(activity,ActivityFullScreenImage.class,arguments);
                }
            });
        }

        return convertView;
    }


}
