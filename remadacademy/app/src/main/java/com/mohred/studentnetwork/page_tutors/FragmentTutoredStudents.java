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
import com.mohred.studentnetwork.connection.MessageGetTutoredStudents;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.OrgFriendsList;
import com.mohred.studentnetwork.model.UserMessage;
import java.util.List;
import static android.view.View.GONE;

/**
 * Created by Redan on 1/27/2017.
 */

public class FragmentTutoredStudents extends ListFragment
{
    private static final String TAG = "fragment_tutored_courss";
    private View view;
    private ProgressBar progressBar;
    private static final int TUTORED_STUDENTS_LOADER_ID = 240;

    public static FragmentTutoredStudents newInstance()
    {
        FragmentTutoredStudents fragment = new FragmentTutoredStudents();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tutored_students, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        LoaderManager loaderManager = getLoaderManager();
        Log.d(TAG,"initing tutored students screen !");
        loaderManager.restartLoader(TUTORED_STUDENTS_LOADER_ID, null, loaderCallbackForTutors);
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
                    return new DataGetLoaderTask(getActivity(),new MessageGetTutoredStudents(tutorID,0)); // TODO - implement index
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    progressBar.setVisibility(GONE);
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        // TODO - handle error
                        Log.d(TAG,"NULL student list returned");
                        AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                        return;
                    }
                    OrgFriendsList respond = (OrgFriendsList)data;
                    List<UserMessage> tutors = respond.getOrgFriendsList();
                    AdapterTutoredStudents adapter = new AdapterTutoredStudents(getActivity(),
                                                                                  R.layout.item_tutored_student,
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
