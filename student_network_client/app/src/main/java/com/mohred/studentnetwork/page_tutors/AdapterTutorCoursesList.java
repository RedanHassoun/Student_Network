package com.mohred.studentnetwork.page_tutors;

import android.app.Activity;
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
import com.mohred.studentnetwork.page_courses.FragmentTabsPager;

import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_SINGLE_COURSE;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_ID_COURSE;

/**
 * Created by Redan on 1/27/2017.
 */

public class AdapterTutorCoursesList extends ArrayAdapter<Course>
{
    private int resource;
    private Activity activity;
    private List<Course> listTutors;


    public AdapterTutorCoursesList(Activity activity, int resource, List<Course> listTutors)
    {
        super(activity, resource, listTutors);

        this.resource = resource;
        this.activity = activity;
        this.listTutors = listTutors;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view;
        view = activity.getLayoutInflater().inflate(resource, parent, false);

        final Course course = listTutors.get(position);
        Holder holder = new Holder();
        holder.textName = (TextView) view.findViewById(R.id.text_course_name);
        holder.imageCourse = (ImageView)view.findViewById(R.id.item_icon);

        holder.textName.setText(course.getName());
        Drawable drawable = AppUtils.customizeIcon(getContext(),
                                                   holder.imageCourse,
                                                    R.drawable.ic_library_books_white_24dp);

        holder.imageCourse.setImageDrawable(drawable);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTabsPager fragment = new FragmentTabsPager();
                Bundle args = new Bundle();
                args.putString(ARG_ID_COURSE,course.getId());
                fragment.setArguments(args);
                ((ActivityBase)activity).addFragment(fragment,FRAGMENT_SINGLE_COURSE,args,true);
            }
        });

        return view;
    }

    private class Holder
    {
        private ImageView imageCourse;
        private TextView textName;
    }
}