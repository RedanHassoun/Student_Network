package com.mohred.studentnetwork.managers;

import android.content.Context;
import android.widget.EditText;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;

/**
 * Created by Redan on 12/16/2016.
 */

public class ValidationManager
{
    public boolean checkLoginFields(Context context, EditText email, EditText password)
    {
        boolean canLogin = true;
        if(AppUtils.isEmailValid(email.getText().toString())==false)
        {
            canLogin = false;
            email.setError(context.getResources().getString(R.string.validation_err_email));
        }
        if(AppUtils.isPasswordOk(password.getText().toString())==false)
        {
            canLogin = false;
            password.setError(context.getResources().getString(R.string.validation_err_password));
        }

        return  canLogin;
    }

}
