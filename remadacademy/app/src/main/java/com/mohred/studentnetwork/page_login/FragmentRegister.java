package com.mohred.studentnetwork.page_login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_TYPE_REGULAR;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_CHOOSE_ORGANIZATION;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_LOGIN;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_LOGIN_METHOD;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USERNAME;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_EMAIL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_FULL_NAME;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_PASSWORD;

/**
 * Created by Redan on 12/3/2016.
 */
public class FragmentRegister extends Fragment implements View.OnClickListener
{
    private View view;
    private Button buttonRegister;
    private TextView textLogin;
    private EditText fieldUsername;
    private EditText fieldEmail;
    private EditText fieldPassword;
    private EditText fieldPasswordConfirm;
    private EditText fieldFullName;


    public static final FragmentRegister newInstance()
    {
        FragmentRegister fragment = new FragmentRegister();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        buttonRegister = (Button) view.findViewById(R.id.button_submit);
        buttonRegister.setOnClickListener(this);

        fieldUsername = (EditText) view.findViewById(R.id.field_username);
        fieldEmail = (EditText) view.findViewById(R.id.field_email);
        fieldPassword = (EditText) view.findViewById(R.id.field_password);
        fieldPasswordConfirm = (EditText) view.findViewById(R.id.field_password_confirm);

        fieldFullName = (EditText) view.findViewById(R.id.field_full_name);

        textLogin = (TextView) view.findViewById(R.id.text_login);
        textLogin.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_submit:
                if(AppUtils.checkRegisterFields(getActivity(),fieldFullName,fieldUsername,
                                                fieldEmail,fieldPassword,fieldPasswordConfirm))
                {
                    onClickRegister();
                }
                break;
            
            case R.id.text_login:
                onLoginClick();
                break;

        }
    }

    private void onClickRegister()
    {
        Bundle args = new Bundle();
        args.putString(ARG_USER_FULL_NAME, fieldFullName.getText().toString());
        args.putString(ARG_USERNAME,fieldUsername.getText().toString());
        args.putString(ARG_USER_EMAIL,fieldEmail.getText().toString());
        args.putString(ARG_USER_PASSWORD,fieldPassword.getText().toString());
        args.putString(ARG_LOGIN_METHOD,REGISTER_TYPE_REGULAR);

        ((ActivityAuthenticate)getActivity()).replaceFragment(FRAGMENT_CHOOSE_ORGANIZATION,args,true);
    }



    /**
     * This method handles the login user click - turns back to the login fragment
     */
    public void onLoginClick()
    {
        ((ActivityAuthenticate)getActivity()).replaceFragment(FRAGMENT_LOGIN,null,false);
    }


}
