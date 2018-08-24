package com.mohred.studentnetwork.page_courses;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.connection.MessageGetCourseAbout;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.Course;
import com.mohred.studentnetwork.model.CourseAbout;


/**
 * Created by Redan on 12/2/2016.
 */
public class FragmentCourseAbout extends Fragment
{
    private View rootView;
    private String courseId = null;
    private Course theCourse = null;
    private TextView textName;
    private TextView textFaculity;
    private TextView textPoints;
    private TextView textTeacherName;
    private TextView textDescription;
    private TextView textAboutNotFound;
    private static final String TAG = "course_about";


    public static final FragmentCourseAbout newInstance(String id)
    {
        FragmentCourseAbout fragment = new FragmentCourseAbout();
        fragment.setCourseId(id);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_course_about, container, false);

        textName = (TextView) rootView.findViewById(R.id.text_course_name);
        textFaculity = (TextView) rootView.findViewById(R.id.text_course_facuility);
        textPoints = (TextView) rootView.findViewById(R.id.text_points);
        textTeacherName = (TextView) rootView.findViewById(R.id.text_teacher_name);
        textDescription = (TextView) rootView.findViewById(R.id.text_description);
        textAboutNotFound = (TextView) rootView.findViewById(R.id.about_not_found);

        Log.d(TAG,"checking the course id = "+courseId);
        if(courseId != null){
            new GetCourseAboutTask(getCourseId()).execute();
        }

        return rootView;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }


    private class GetCourseAboutTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private String courseID;
        public GetCourseAboutTask(String courseID)
        {
            this.courseID = courseID;
        }
        @Override
        protected ConnectionObject doInBackground(Void... params) {
            try{
                Log.d(TAG,"sending message .. ");
                MessageGetCourseAbout message = new MessageGetCourseAbout(getCourseId());
                return message.sendMessage();
            }catch (Exception ex){
                ex.printStackTrace();
                Log.d(TAG,"EXCEPTION: "+ex.getMessage());
                return null;
            }
        }


        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            if(connectionObject == null){
                //AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                textAboutNotFound.setVisibility(View.VISIBLE);
                Log.d(TAG,"connectionObject=NULL");
            }else{
                textAboutNotFound.setVisibility(View.GONE);
                Log.d(TAG,"rendering course-about");
                CourseAbout courseAbout = (CourseAbout) connectionObject;
                textName.setText(courseAbout.getCourseName());
                textDescription.setText(courseAbout.getDescription());
                textFaculity.setText(courseAbout.getFaculityName());
                textTeacherName.setText(getString(R.string.teacher)+": "+courseAbout.getTeacherName());
                textPoints.setText(getString(R.string.points)+": "+courseAbout.getPoints());
                Log.d(TAG,"rendered course-about");
            }
        }
    }

}
