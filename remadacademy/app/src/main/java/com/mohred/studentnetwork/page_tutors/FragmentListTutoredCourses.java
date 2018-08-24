package com.mohred.studentnetwork.page_tutors;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.DataGetLoaderTask;
import com.mohred.studentnetwork.connection.MessageGetTutoredCourses;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.Course;
import com.mohred.studentnetwork.model.CourseMessage;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Redan on 1/21/2017.
 */
public class FragmentListTutoredCourses extends ListFragment
{
    private static final String TAG = "fragment_tutored_courss";
    private View view;
    private ProgressBar progressBar;
    private static final int TUTORED_COURSES_LOADER_ID = 20;

    public static final FragmentListTutoredCourses newInstance()
    {
        FragmentListTutoredCourses fragment = new FragmentListTutoredCourses();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tutored_courses, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(TUTORED_COURSES_LOADER_ID, null, loaderCallbackForTutors);
        return view;
    }


    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForTutors =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    String tutorID = DataManager.getInstance().getCurrentUser(getActivity()).getId();
                    return new DataGetLoaderTask(getActivity(),new MessageGetTutoredCourses(tutorID,0)); // TODO - implement index
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    progressBar.setVisibility(GONE);
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                        Log.d(TAG,"NULL");
                        return;
                    }
                    CourseMessage respond = (CourseMessage)data;
                    List<Course> tutors = respond.getCourses();
                    AdapterTutorCoursesList adapter = new AdapterTutorCoursesList(getActivity(),
                                                                                  R.layout.item_tutor_course,
                                                                                  tutors);
                    setListAdapter(adapter);
                    Log.d(TAG,"the respond size = "+tutors.size());
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                }
            };
}
