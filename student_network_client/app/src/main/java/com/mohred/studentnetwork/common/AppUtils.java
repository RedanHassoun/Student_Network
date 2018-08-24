package com.mohred.studentnetwork.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.dialog.DialogFragmentError;
import com.mohred.studentnetwork.model.KeyValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Redan on 12/3/2016.
 */
public class AppUtils
{
    private AppUtils(){}
    public static String printKeyHash(Activity context)
    {
        PackageInfo packageInfo;
        String key = null;
        try
        {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=","aaa555ssswww  hash = "+ key);
            }
        } catch (PackageManager.NameNotFoundException e1)
        {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    /**
     * method is used for checking valid username  format.
     *
     * @return boolean true for valid false for invalid
     */
    public static boolean isUsernameValid(String username)
    {
        //Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

        //boolean valid = (username != null) && pattern.matcher(username).matches();

        if(username.equals(""))
            return false;
        return true;
    }


    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email)
    {
        boolean isValid = true;

        /*
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }*/
        if(email.equals("")){
            isValid = false;
        }

        return isValid;
    }

    public static boolean isPasswordOk(String password)
    {
        return  password.length()>2;

    }

    public static boolean isPasswordsMatch(String firstPass,String secondPass)
    {
        return firstPass.equals(secondPass);
    }

    public static boolean isFullnameValid(EditText editTextFullname)
    {
       return true;

    }


    public static boolean checkRegisterFields(Context context, EditText fullName, EditText username,
                                       EditText email,EditText password, EditText confirmPassword)
    {
        boolean isValid = true;

        if(! isFullnameValid(fullName))
        {
            isValid = false;
            fullName.setError(context.getResources().getString(R.string.validation_err_full_name));
        }

        if(! isUsernameValid(username.getText().toString()))
        {
            isValid = false;
            username.setError(context.getResources().getString(R.string.validation_err_username));
        }

        if(! isEmailValid(email.getText().toString()))
        {
            isValid = false;
            email.setError(context.getResources().getString(R.string.validation_err_email));
        }

        if(! isPasswordOk(password.getText().toString()))
        {
            isValid = false;
            password.setError(context.getResources().getString(R.string.validation_err_password));
        }

        if(! isPasswordsMatch(password.getText().toString(),confirmPassword.getText().toString()))
        {
            isValid = false;
            confirmPassword.setError(context.getResources().getString(R.string.validation_err_password_not_match));
        }

        return  isValid;
    }

    public static JSONObject keyValueToJSON(List<KeyValue> keyValueList) throws JSONException
    {
        JSONObject object = new JSONObject();
        for(KeyValue current : keyValueList)
            object.put(current.getKey(),current.getValue());

        return  object;
    }

    public static void showToastMessage(Context context,String message)
    {
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }

    public static void forceRTLIfSupported(Activity context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            context.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public static void openActivity(Activity thisActivity, Class<?> toOpen)
    {
        Intent intent = new Intent(thisActivity, toOpen);
        thisActivity.startActivity(intent);
    }

    public static void openActivity(Activity thisActivity, Class<?> toOpen,Bundle arguments)
    {
        Intent intent = new Intent(thisActivity, toOpen);
        intent.putExtras(arguments);
        thisActivity.startActivity(intent);
    }


    public static void showErrorMessage(AppCompatActivity activity, String message)
    {
        DialogFragmentError fragmentError = DialogFragmentError.newInstance(message);
        fragmentError.show(activity.getSupportFragmentManager(), "dialog");
    }

    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static Drawable customizeIcon(Context context, ImageView imageView, int iconID)
    {
        Drawable mDrawable = context.getResources().getDrawable(iconID);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(context
                                        .getResources()
                                        .getColor(R.color.colorPrimary),PorterDuff.Mode.MULTIPLY));

        return mDrawable;
    }

    public static Drawable customizeDrawable(Context context,int iconID)
    {
        Drawable mDrawable = context.getResources().getDrawable(iconID);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(context
                .getResources()
                .getColor(R.color.colorPrimary),PorterDuff.Mode.MULTIPLY));

        return mDrawable;
    }

    public static boolean validateUpdateProfileFields(Context context, EditText textUsername)
    {
        boolean isValid = true;
        if(textUsername.getText().toString().equals("")){
            isValid = false;
            textUsername.setError(context.getString(R.string.user_name_validation));
        }

        return isValid;
    }

    public static boolean validateUpdateTutorProfile(Context context,
                                                     EditText editAbout,
                                                     EditText editHowToContact)
    {
        boolean isValid = true;
        if(editAbout.getText().toString().equals("")){
            isValid = false;
            editAbout.setError(context.getString(R.string.wrong_input));
        }

        if(editHowToContact.getText().toString().equals("")){
            isValid = false;
            editHowToContact.setError(context.getString(R.string.wrong_input));
        }

        return isValid;
    }

    public static boolean isInternetAvailable()
    {
        try
        {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
