package com.mohred.studentnetwork.page_main;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.connection.MessageUpdateFirebaseToken;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.Status;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;

public class ActivityMain extends ActivityBase
{
    private static final String TAG = "activity_main";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.home_page));

        initFragment(FragmentNewsFeed.newInstance());

        //initFragment(new FragmentChatRoom());

        handleUpdatingFirebaseToken();

    }

    private void handleUpdatingFirebaseToken()
    {
        boolean isTokenChanged = DataManager.getInstance()
                                        .getCurrentUser(getBaseContext()).
                                         isTokenChanged();
        if(isTokenChanged){
            new SendFirebaseTokenTask().execute();
        }

    }

    private class SendFirebaseTokenTask extends AsyncTask<Void,Void,Status>
    {

        @Override
        protected com.mohred.studentnetwork.model.Status doInBackground(Void... params) {
            com.mohred.studentnetwork.model.Status toReturn = null;
            try{
                /* Get the firebase token from firebase-service */
                String firebaseToken = FirebaseInstanceId.getInstance().getToken();

                /* Send the firebase token to the App server */
                User userInStorage = DataManager.getInstance().getCurrentUser(getBaseContext());
                UserMessage user = userInStorage.getUserMessage();
                user.setFirebaseToken(firebaseToken);

                MessageUpdateFirebaseToken message = new MessageUpdateFirebaseToken();
                toReturn = (com.mohred.studentnetwork.model.Status) message.sendMessage(user);

                userInStorage.setTokenChanged(false);
                DataManager.getInstance().setCurrentUser(userInStorage,getBaseContext());
            }catch (Exception ex){
                ex.printStackTrace();
                // TODO - handle exception
                toReturn = null;
            }

            return toReturn;
        }


        @Override
        protected void onPostExecute(com.mohred.studentnetwork.model.Status status) {
            super.onPostExecute(status);

            // TODO
        }
    }
}
