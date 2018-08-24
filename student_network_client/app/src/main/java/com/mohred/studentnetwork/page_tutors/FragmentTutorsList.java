package com.mohred.studentnetwork.page_tutors;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.DataGetLoaderTask;
import com.mohred.studentnetwork.connection.MessageGetAllTutorsByOrg;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.MessageListTutors;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;

import java.util.List;

/**
 * Created by Redan on 1/21/2017.
 */

public class FragmentTutorsList extends ListFragment implements View.OnClickListener
{
    private View view;
    private static final int TUTORS_LOADER_ID = 212;
    private static final String TAG = "fragment_tutors_list";
    private static final String TAG_DIALOG = "become_tutor";
    private ProgressBar progressBar;
    private FloatingActionButton buttonBecomeTutor;

    public static final FragmentTutorsList newInstance()
    {
        FragmentTutorsList fragment = new FragmentTutorsList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tutors_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        buttonBecomeTutor = (FloatingActionButton) view.findViewById(R.id.button_become_tutor);
        User userFromStorage = DataManager.getInstance().getCurrentUser(getContext());
        if(!userFromStorage.isTutor()){
            buttonBecomeTutor.setVisibility(View.VISIBLE);
            buttonBecomeTutor.setOnClickListener(this);
        }else
            buttonBecomeTutor.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"loading things up");
        //LoaderManager loaderManager = getLoaderManager();
        //loaderManager.restartLoader(TUTORS_LOADER_ID, null, loaderCallbackForTutors);
        new GetDataTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_become_tutor:
                openBecomeTutorDialog();
            break;
        }
    }

    private void openBecomeTutorDialog()
    {
        DialogFragmentBecomeTutor dialog = DialogFragmentBecomeTutor.newInstance(this);
        dialog.show(getFragmentManager(),TAG_DIALOG);
    }

    private class GetDataTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {
            try {
                Log.d(TAG,"creating loader task");
                String orgID = DataManager.getInstance().getCurrentUser(getActivity()).getOrganizationId();
                Log.d(TAG,"the org id = "+orgID);
                MessageGetAllTutorsByOrg msg = new MessageGetAllTutorsByOrg(orgID);
                return msg.sendMessage();
            }catch (Exception ex){
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            progressBar.setVisibility(View.GONE);
            // Display our data, for instance updating our adapter
            if(connectionObject == null){
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                Log.d(TAG,"NULL");
                return;
            }
            MessageListTutors respond = (MessageListTutors)connectionObject;
            List<UserMessage> tutors = respond.getListTutors();
            removeCurrentUserFromListIfExist(tutors);
            AdapterTutorsList adapter = new AdapterTutorsList((ActivityBase)getActivity(),
                    R.layout.item_list_tutor,
                    tutors);
            setListAdapter(adapter);
            Log.d(TAG,"the respond size = "+tutors.size());
        }

        private void removeCurrentUserFromListIfExist(List<UserMessage> tutors)
        {
            for(int i=0;i<tutors.size();i++){
                String userID = DataManager.getInstance().getCurrentUser(getContext()).getId();
                if(tutors.get(i).getUserId().equals(userID)){
                    tutors.remove(i);
                }
            }
        }
    }
    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForTutors =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    Log.d(TAG,"creating loader task");
                    ((ActivityBase)getActivity()).showProgressBar();
                    String orgID = DataManager.getInstance().getCurrentUser(getActivity()).getOrganizationId();
                    Log.d(TAG,"the org id = "+orgID);
                    return new DataGetLoaderTask(getActivity(),new MessageGetAllTutorsByOrg(orgID));
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    ((ActivityBase)getActivity()).hideProgressBar();
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                        Log.d(TAG,"NULL");
                        return;
                    }
                    MessageListTutors respond = (MessageListTutors)data;
                    List<UserMessage> tutors = respond.getListTutors();
                    AdapterTutorsList adapter = new AdapterTutorsList((ActivityBase)getActivity(),
                                                                       R.layout.item_list_tutor,
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
