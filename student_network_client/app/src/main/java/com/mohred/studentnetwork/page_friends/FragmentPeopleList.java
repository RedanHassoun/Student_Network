package com.mohred.studentnetwork.page_friends;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.DataPostLoaderTask;
import com.mohred.studentnetwork.connection.GetOrgFriends;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.OrgFriendsList;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;

import java.util.List;

import static com.facebook.GraphRequest.TAG;


/**
 * Created by m7md on 1/7/2017.
 */
public class FragmentPeopleList extends Fragment
{
    private User currentUser;
    private LoaderManager loaderManager;
    private View view;
    private ListView listViewAllPeople;
    private TextView textNoPeopleFound;

    protected static final String GET_ORG_FRIENDS_COMPLETED_SUCCESFULLY = "get_org_friends_completed_succesfully";
    protected static final String GET_ORG_FRIENDS_FAILD = "get_org_friends_faild";
    private static final int USER_UPDATE_UIQUE_ID = 2;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_people_list, container, false);
        initViews();
        downloadAllPeopleList();

        return view;
    }


    private void initViews()
    {
        currentUser = DataManager.getInstance().getCurrentUser(getActivity());
        listViewAllPeople = (ListView) view.findViewById(R.id.simpleListView);
        textNoPeopleFound = view.findViewById(R.id.text_no_people);
    }

    private void downloadAllPeopleList()
    {
        loaderManager = getLoaderManager();
        UserMessage usertoPost = currentUser.getUserMessage();
        loaderManager.restartLoader(USER_UPDATE_UIQUE_ID,null,new callbackGetMessagesTask(usertoPost));
    }

    private class callbackGetMessagesTask implements LoaderManager.LoaderCallbacks<ConnectionObject>
    {
        private UserMessage userMessage;
        public callbackGetMessagesTask(UserMessage userMessage)
        {
            this.userMessage = userMessage;
        }
        @Override
        public Loader<ConnectionObject> onCreateLoader(int id, Bundle args)
        {
            return new DataPostLoaderTask(getActivity(),new GetOrgFriends(),userMessage);
        }
        @Override
        public void onLoadFinished(
                Loader<ConnectionObject> loader, ConnectionObject data)
        {
            // Display our data, for instance updating our adapter
            if(data == null){
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                Log.d(TAG,"NULL");
                return;
            }
            try {
                OrgFriendsList listPeople = (OrgFriendsList)data;
                List<UserMessage> listOfPeople = getAllPeopleExceptCurrentUser(listPeople);
                if((listOfPeople != null) && (listOfPeople.size()==0)){
                    textNoPeopleFound.setVisibility(View.VISIBLE);
                }
                switch (listPeople.getStatus())
                {
                    case GET_ORG_FRIENDS_COMPLETED_SUCCESFULLY:
                        AdapterAllPeopleList adapter = new AdapterAllPeopleList(getActivity(),
                                R.layout.item_list_people_on_org,
                                listOfPeople);
                        listViewAllPeople.setAdapter(adapter);
                        break;
                    case GET_ORG_FRIENDS_FAILD:
                        AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
            }

        }
        @Override
        public void onLoaderReset(Loader<ConnectionObject> loader)
        {
        }
    }

    private List<UserMessage> getAllPeopleExceptCurrentUser(OrgFriendsList listPeople)
    {
        List<UserMessage> listOfPeople = listPeople.getOrgFriendsList();
        User currentUser = DataManager.getInstance().getCurrentUser(getContext());

        int indexToRemove = -1;
        for(int i=0;i<listOfPeople.size();i++){
            if(listOfPeople.get(i).getUserId().equals(currentUser.getId())){
                indexToRemove = i;
            }
        }

        if(indexToRemove != -1){
            listOfPeople.remove(indexToRemove);
        }

        return listOfPeople;
    }
}
