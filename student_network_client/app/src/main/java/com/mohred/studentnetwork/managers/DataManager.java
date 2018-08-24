package com.mohred.studentnetwork.managers;

import android.content.Context;

import com.mohred.studentnetwork.common.ComplexPreferences;
import com.mohred.studentnetwork.connection.HttpExecutionPool;
import com.mohred.studentnetwork.model.User;

import static com.mohred.studentnetwork.common.AppConstants.SharedPrefsKeys.PREF_CURRENT_USER_OBJECT;
import static com.mohred.studentnetwork.common.AppConstants.SharedPrefsKeys.PREF_USER_SESSION;

/**
 * Created by Redan on 12/2/2016.
 */
public class DataManager
{
    private HttpExecutionPool httpExecutionPool = new HttpExecutionPool();
    private ValidationManager validationManager = new ValidationManager();
    private static final DataManager INSTANCE = new DataManager();
    private DataManager(){}

    public static final DataManager getInstance()
    {
        return INSTANCE;
    }


    public HttpExecutionPool getHttpExecutionPool() {
        return httpExecutionPool;
    }

    public void setCurrentUser(User currentUser, Context ctx)
    {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, PREF_USER_SESSION, 0);
        complexPreferences.putObject(PREF_CURRENT_USER_OBJECT, currentUser);
        complexPreferences.commit();
    }

    public User getCurrentUser(Context ctx)
    {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, PREF_USER_SESSION, 0);
        User currentUser = complexPreferences.getObject(PREF_CURRENT_USER_OBJECT, User.class);
        return currentUser;
    }

    public void clearCurrentUser( Context ctx)
    {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, PREF_USER_SESSION, 0);
        complexPreferences.removeObject(PREF_CURRENT_USER_OBJECT);
        complexPreferences.commit();
    }

    public ValidationManager getValidationManager() {
        return validationManager;
    }
}
