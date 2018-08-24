package com.mohred.studentnetwork.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.User;

/**
 * Created by Redan on 2/17/2017.
 */
public class AppFirebaseInstanceIdService extends FirebaseInstanceIdService
{
    private static final String TAG = "firebase_instance_id";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenUpdated = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"token = "+tokenUpdated);

        try {
            User user = DataManager.getInstance().getCurrentUser(getBaseContext());
            user.setTokenChanged(true);
            DataManager.getInstance().setCurrentUser(user,getBaseContext());
        }catch (Exception ex){

        }
    }


}
