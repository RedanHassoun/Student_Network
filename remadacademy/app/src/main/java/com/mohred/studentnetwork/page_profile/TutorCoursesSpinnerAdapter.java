package com.mohred.studentnetwork.page_profile;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.model.Course;

import java.util.List;

/**
 * Created by Redan on 2/4/2017.
 */

public class TutorCoursesSpinnerAdapter extends ArrayAdapter<Course>
{
    private int resource;
    private Context context;
    private List<Course> listCourses;
    public TutorCoursesSpinnerAdapter(Context context, int resource, List<Course> listCourses)
    {
        super(context, resource, listCourses);

        this.resource = resource;
        this.context = context;
        this.listCourses = listCourses;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        view = ((Activity)context).getLayoutInflater().inflate(resource, parent, false);

        Course course = listCourses.get(position);
        Holder holder = new Holder();
        holder.name = (TextView) view.findViewById(R.id.text_course_name);
        holder.name.setText(course.getName());


        return view;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view = ((Activity)context).getLayoutInflater().inflate(resource, parent, false);

        Course course = listCourses.get(position);
        Holder holder = new Holder();
        holder.name = (TextView) view.findViewById(R.id.text_course_name);
        holder.name.setText(course.getName());

        return view;
    }

    private class Holder
    {
        private TextView name;
    }
}
