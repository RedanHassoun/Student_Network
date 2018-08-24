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
import com.mohred.studentnetwork.connection.MessageGetTutorRequests;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.TutorRequest;
import com.mohred.studentnetwork.model.TutorRequestList;

import java.util.Collections;
import java.util.List;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by Redan on 2/3/2017.
 */

public class FragmentTutorRequests extends ListFragment
{
    private View view;
    private ProgressBar progressBar;
    private static final int REQUESTS_LOADER_ID = 0;

    public static final FragmentTutorRequests newInstance()
    {
        FragmentTutorRequests fragment = new FragmentTutorRequests();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_list_tutor_requests, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(REQUESTS_LOADER_ID, null, loaderCallbackForRequests);
        return view;
    }

    public void setProgressBarVisible(boolean isVisible){
        if(isVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForRequests =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    Log.d(TAG,"creating loader task");
                    progressBar.setVisibility(View.VISIBLE);
                    String tutorID = DataManager.getInstance().getCurrentUser(getActivity()).getId();
                    return new DataGetLoaderTask(getActivity(),new MessageGetTutorRequests(tutorID,0)); // TODO implement index
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    progressBar.setVisibility(View.GONE);
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                        Log.d(TAG,"NULL");
                        return;
                    }
                    TutorRequestList respond = (TutorRequestList)data;
                    List<TutorRequest> tutors = respond.getRequestList();
                    Collections.sort(tutors);
                    AdapterTutorRequests adapter = new AdapterTutorRequests(FragmentTutorRequests.this,
                            R.layout.item_list_tutor_request,
                            tutors);
                    setListAdapter(adapter);
                    Log.d(TAG,"the respond size = "+tutors.size());
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                    progressBar.setVisibility(View.GONE);
                }


            };
}
