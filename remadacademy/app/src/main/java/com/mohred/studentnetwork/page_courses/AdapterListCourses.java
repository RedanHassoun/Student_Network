package com.mohred.studentnetwork.page_courses;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.model.Course;

import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_ID_COURSE;

/**
 * Created by Redan on 1/7/2017.
 */

public class AdapterListCourses extends ArrayAdapter<Course>
{
    private int resource;
    private Context context;
    private List<Course> coursesList;


    public AdapterListCourses(Context context, int resource, List<Course> coursesList)
    {
        super(context, resource, coursesList);

        this.resource = resource;
        this.context = context;
        this.coursesList = coursesList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view;
        view = ((Activity)context).getLayoutInflater().inflate(resource, parent, false);

        Holder h = new Holder();
        h.name = (TextView) view.findViewById(R.id.text_course_name);
        h.name.setText(coursesList.get(position).getName());

        h.imageView = (ImageView) view.findViewById(R.id.icon_course);
        Drawable drawable = AppUtils.customizeIcon(getContext(),
                                                   h.imageView,
                                                   R.drawable.ic_library_books_white_24dp);

        h.imageView.setImageDrawable(drawable);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTabsPager fragmentTabsPager = new FragmentTabsPager();
                Bundle args = new Bundle();
                args.putString(ARG_ID_COURSE, coursesList.get(position).getId());
                ((ActivityBase)context).addFragment(fragmentTabsPager,"course_details",args,true);
            }
        });

        return view;
    }



    private class Holder
    {
        private TextView name;
        private ImageView imageView;
    }
}
