package com.mohred.studentnetwork.page_courses;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.DataGetLoaderTask;
import com.mohred.studentnetwork.connection.MessageGetCourses;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.Course;
import com.mohred.studentnetwork.model.CourseMessage;
import com.mohred.studentnetwork.model.User;

import java.util.List;

/**
 * Created by Redan on 1/7/2017.
 */

public class FragmentCourses extends ListFragment
{
    private View view;
    private static final int COURSES_LOADER_ID = 0;
    private LoaderManager loaderManager;
    private User user;
    private ProgressBar progressBar;
    private static final String TAG = "courses";

    public static final FragmentCourses newInstance()
    {
        FragmentCourses fragment = new FragmentCourses();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_courses, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        user = DataManager.getInstance().getCurrentUser(getActivity());

        loaderManager = getLoaderManager();
        loaderManager.restartLoader(COURSES_LOADER_ID, null, loaderCallbackForCourses);

        return view;
    }


    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForCourses =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    return new DataGetLoaderTask(getActivity(),
                                                 new MessageGetCourses(user.getFaculityID()));
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    progressBar.setVisibility(View.GONE);
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        AppUtils.showToastMessage(getContext(),
                                                        getString(R.string.error_connecting_to_server));
                        return;
                    }

                    try {
                        CourseMessage courseMessage = (CourseMessage)data;
                        List<Course> courses = courseMessage.getCourses();
                        AdapterListCourses adapter = new AdapterListCourses(getActivity(),
                                                                  R.layout.item_list_course,courses);
                        setListAdapter(adapter);
                    }catch (Exception ex){
                        ex.printStackTrace();
                        AppUtils.showToastMessage(getContext(),
                                                  getString(R.string.general_error_message));
                    }
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                }
            };


}
