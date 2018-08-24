package com.mohred.studentnetwork.page_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.DataGetLoaderTask;
import com.mohred.studentnetwork.connection.DataPostLoaderTask;
import com.mohred.studentnetwork.connection.GetOrganizationsMessage;
import com.mohred.studentnetwork.connection.LoginGoogleMessage;
import com.mohred.studentnetwork.connection.LoginMessage;
import com.mohred.studentnetwork.connection.MessageFacebookLogin;
import com.mohred.studentnetwork.connection.MessageGetFaculities;
import com.mohred.studentnetwork.connection.RegisterUserMessage;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.interfaces.HTTPObserver;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.managers.ValidationManager;
import com.mohred.studentnetwork.model.Faculity;
import com.mohred.studentnetwork.model.FaculityMessage;
import com.mohred.studentnetwork.model.Organization;
import com.mohred.studentnetwork.model.OrganizationsMessage;
import com.mohred.studentnetwork.model.RegisterStatus;
import com.mohred.studentnetwork.model.Status;
import com.mohred.studentnetwork.model.StatusAouthLogin;
import com.mohred.studentnetwork.model.StatusAuthenticate;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;

import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.LOGIN_OK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_STATIS_OK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_STATUS_EXIST;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_TYPE_FACEBOOK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_TYPE_GOOGLE;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_TYPE_REGULAR;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_UNKNOWN_ERROR;
import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.ID_NULL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_LOGIN_METHOD;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USERNAME;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_DOMAIN_ID;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_EMAIL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_FULL_NAME;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_IMAGE_URL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_PASSWORD;

/**
 * Created by Redan on 12/19/2016.
 */
public class FragmentChooseOrganizationAndFaculty extends Fragment implements HTTPObserver,View.OnClickListener
{
    private static final String TAG = "choose_org";
    private View view;
    private Spinner spinnerOrgs;
    private Spinner spinnerFaculities;
    private LinearLayout faculityChoiceLayout;
    private Button buttonRegister;
    private List<Organization> organizationsList;
    private List<Faculity> faculitiesList = null;
    private Organization selectedOrganization=null;
    private Faculity selectedFaculity = null;
    private static final int ORGS_LOADER_ID = 0;
    private int FACULITIES_LOADER_ID = 200;
    private static final int REGISTER_USER_LOADER_ID = 1000;
    private LoaderManager loaderManager;
    private DataManager dataManager = DataManager.getInstance();
    public static final FragmentChooseOrganizationAndFaculty newInstance()
    {
        FragmentChooseOrganizationAndFaculty frag = new FragmentChooseOrganizationAndFaculty();

        return  frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_choose_orginization, container, false);

        faculityChoiceLayout = (LinearLayout) view.findViewById(R.id.layout_choice_faculity);

        buttonRegister = (Button) view.findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(this);

        loaderManager = getLoaderManager();
        loaderManager.restartLoader(ORGS_LOADER_ID, null, loaderCallbackForOrgs);

        return view;
    }


    private class LoaderCallbackForFaculities implements LoaderManager.LoaderCallbacks<ConnectionObject>
    {
        @Override
        public Loader<ConnectionObject> onCreateLoader(
                int id, Bundle args)
        {
            ((ActivityAuthenticate)getActivity()).showProgressBar();
            Log.d("CONNECTION_PROBLEM","creating loader");
            return new DataGetLoaderTask(getActivity(),new MessageGetFaculities(selectedOrganization.getId()));
        }
        @Override
        public void onLoadFinished(
                Loader<ConnectionObject> loader, ConnectionObject data)
        {
            ((ActivityAuthenticate)getActivity()).hideProgressBar();
            // Display our data, for instance updating our adapter
            if(data == null){
                Log.d("CONNECTION_PROBLEM","got null from server");
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                Log.d(TAG,"NULL");
                ((ActivityAuthenticate)getActivity()).hideProgressBar();
                return;
            }

            Log.d(TAG,"connectoion object org message");
            FaculityMessage faculityMessage = (FaculityMessage) data;
            faculitiesList = faculityMessage.getFacList();

            addDefualtSelection();

            Log.d(TAG,"size = "+faculitiesList.size());
            spinnerFaculities = (Spinner) view.findViewById(R.id.spinner_faculities);
            FaculitiesSpinnerAdapter adapter = new FaculitiesSpinnerAdapter(getActivity(),
                    R.layout.item_spinner_faculity,
                    faculitiesList);
            spinnerFaculities.setAdapter(adapter);
            spinnerFaculities.setOnItemSelectedListener(new FaculitiesOnItemSelectedListener());
            ((ActivityAuthenticate)getActivity()).hideProgressBar();
            faculityChoiceLayout.setVisibility(View.VISIBLE);
        }

        private void addDefualtSelection() {
            boolean isExist = false;
            for(int i=0;i<faculitiesList.size();i++){
                if(faculitiesList.get(i).getId().equals(ID_NULL)){
                    isExist = true;
                }
            }

            if(! isExist){
                Faculity emptyFaculity = new Faculity();
                emptyFaculity.setId(ID_NULL);
                emptyFaculity.setName(getString(R.string.choose_a_faculty));
                faculitiesList.add(0,emptyFaculity);
            }
        }

        @Override
        public void onLoaderReset(Loader<ConnectionObject> loader)
        {
        }
    }

    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForOrgs =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    Log.d("CONNECTION_PROBLEM","creating loader for organizations");
                    ((ActivityAuthenticate)getActivity()).showProgressBar();
                    return new DataGetLoaderTask(getActivity(),new GetOrganizationsMessage());
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    ((ActivityAuthenticate)getActivity()).hideProgressBar();
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                        Log.d(TAG,"NULL");
                        Log.d("CONNECTION_PROBLEM","got null from server");
                        return;
                    }

                    Log.d(TAG,"connectoion object org message");
                    OrganizationsMessage orgsMessage = (OrganizationsMessage) data;
                    spinnerOrgs = (Spinner) view.findViewById(R.id.spinner_organizations);
                    spinnerOrgs.setOnItemSelectedListener(new OrgsOnItemSelectedListener());
                    organizationsList = orgsMessage.getOrgs();
                    Organization emptyOrg = new Organization();
                    emptyOrg.setName(getString(R.string.choose_an_organization));
                    emptyOrg.setId(ID_NULL);
                    organizationsList.add(0,emptyOrg);
                    selectedOrganization = organizationsList.get(0);
                    OrganizationsAdapter adapter = new OrganizationsAdapter(getActivity(),
                            R.layout.item_organization,
                            organizationsList);
                    spinnerOrgs.setAdapter(adapter);
                    ((ActivityAuthenticate)getActivity()).hideProgressBar();
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                }
            };

    private class RegisterUserLoaderCallback implements LoaderManager.LoaderCallbacks<ConnectionObject>
    {
        private UserMessage userToPost;
        public RegisterUserLoaderCallback(UserMessage userToPost)
        {
            this.userToPost = userToPost;
        }

        @Override
        public Loader<ConnectionObject> onCreateLoader(int id, Bundle args) {
            ((ActivityAuthenticate)getActivity()).showProgressBar();
            return new DataPostLoaderTask(getActivity(),new RegisterUserMessage(),userToPost);
        }

        @Override
        public void onLoadFinished(Loader<ConnectionObject> loader, ConnectionObject data) {
            ((ActivityAuthenticate)getActivity()).hideProgressBar();
            StatusAuthenticate s = (StatusAuthenticate )data;
            RegisterUIHandler uiHandler = new RegisterUIHandler(data);
            uiHandler.sendEmptyMessage(0);
        }

        @Override
        public void onLoaderReset(Loader<ConnectionObject> loader) {

        }
    }

    private class RegisterUIHandler extends Handler
    {
        private ConnectionObject data;

        public RegisterUIHandler(ConnectionObject data)
        {
            this.data = data;
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG,"connection object -> register user response");
            try {
                StatusAuthenticate s = (StatusAuthenticate) data;
                Log.d(TAG,"status = "+s.getStatus());

                switch(s.getStatus())
                {
                    case REGISTER_STATUS_EXIST:
                        AppUtils.showErrorMessage((AppCompatActivity) getActivity(),
                                getString(R.string.cant_register_already_exist));
                        break;
                    case REGISTER_UNKNOWN_ERROR:
                        AppUtils.showErrorMessage((AppCompatActivity) getActivity(),
                                getString(R.string.cant_register_server_error));
                        break;
                    case REGISTER_STATIS_OK:
                        if(s.getRegisterType().equals(REGISTER_TYPE_REGULAR))
                        {
                            User user = getUserDetails(s);

                            Log.d(TAG,"User id = "+user.getId());
                            loginRegular(user);
                            //((ActivityAuthenticate)getActivity()).openApp(user);
                        }else if(s.getRegisterType().equals(REGISTER_TYPE_GOOGLE)){
                            Log.d(TAG,"google login");
                            new LoginAouthTask(getUserFromArguments().getUserMessage(),
                                                                    REGISTER_TYPE_GOOGLE).execute();
                        }else if(s.getRegisterType().equals(REGISTER_TYPE_FACEBOOK)){
                            new LoginAouthTask(getUserFromArguments().getUserMessage(),
                                                                   REGISTER_TYPE_FACEBOOK).execute();
                        }
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
            }

        }
    }

    private class LoginAouthTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private UserMessage userMessage;
        private String userType;

        public LoginAouthTask(UserMessage userMessage, String userType)
        {
            this.userMessage = userMessage;
            this.userType = userType;
        }
        @Override
        protected ConnectionObject doInBackground(Void... params)
        {
            try {

                if(userType.equals(REGISTER_TYPE_GOOGLE)){
                    LoginGoogleMessage loginGoogleMessage = new LoginGoogleMessage();
                    ConnectionObject respond;
                    return loginGoogleMessage.sendMessage(userMessage);
                }else {
                    MessageFacebookLogin loginGoogleMessage = new MessageFacebookLogin();
                    ConnectionObject respond;
                    return loginGoogleMessage.sendMessage(userMessage);
                }

            }catch (Exception ex)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject)
        {
            super.onPostExecute(connectionObject);

            try {
                StatusAuthenticate status = (StatusAuthenticate) connectionObject;

                switch (status.getStatus()){
                    case LOGIN_OK:
                        User user = new User.Builder()
                                .setId(status.getId())
                                .setOrganizationName(status.getOrgName())
                                .setUsername(status.getUsername())
                                .setEmail(status.getMail())
                                .setFullName(status.getFullName())
                                .setImageURL(status.getImageURL())
                                .setLoginPlatformId(status.getPlatformId())
                                .build();
                        ((ActivityAuthenticate)getActivity()).openApp(user);
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
            }

        }
    }

    private final int MESSAGE_ON_LOAD_FINISHED = 1;
    private class UIHandler extends Handler
    {
        private ConnectionObject data;
        public UIHandler(ConnectionObject data)
        {
            this.data = data;
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MESSAGE_ON_LOAD_FINISHED){
                if(data instanceof StatusAouthLogin)
                {
                    StatusAouthLogin status = (StatusAouthLogin) data;

                    switch (status.getStatus()){
                        case LOGIN_OK:
                            User user = getUserDetails(status);
                            ((ActivityAuthenticate)getActivity()).openApp(user);
                            break;
                    }

                }
            }
        }
    }

    private User getUserDetails(Status status)
    {
        User user = null;

        if(status instanceof StatusAouthLogin)
        {
            StatusAouthLogin statusToExtract = (StatusAouthLogin) status;
            user = new User.Builder()
                    .setUsername(statusToExtract.getUsername())
                    .setPassword(statusToExtract.getPassword())
                    .setId(statusToExtract.getId())
                    .setEmail(statusToExtract.getMail())
                    .setOrganizationId(statusToExtract.getOgranizationId())
                    .setIsTutor(statusToExtract.isTutor())
                    .setOrganizationName(statusToExtract.getOrgName())
                    .setFacuiltyID(statusToExtract.getFaculityID())
                    .setFacilityName(statusToExtract.getFacilityName())
                    .setFullName(statusToExtract.getFullName())
                    .setImageURL(statusToExtract.getImageURL())
                    .build();
        } else if (status instanceof StatusAuthenticate)
        {
            StatusAuthenticate statusToExtract = (StatusAuthenticate) status;
            user = new User.Builder()
                    .setUsername(statusToExtract.getUsername())
                    .setPassword(statusToExtract.getPassword())
                    .setId(statusToExtract.getId())
                    .setEmail(statusToExtract.getMail())
                    .setOrganizationId(statusToExtract.getOgranizationId())
                    .setIsTutor(statusToExtract.isTutor())
                    .setOrganizationName(statusToExtract.getOrgName())
                    .setFacuiltyID(statusToExtract.getFaculityID())
                    .setFacilityName(statusToExtract.getFacilityName())
                    .setFullName(statusToExtract.getFullName())
                    .setImageURL(statusToExtract.getImageURL())
                    .build();
        } else if(status instanceof RegisterStatus){
            RegisterStatus statusToExtract = (RegisterStatus) status;
            user = new User.Builder()
                    .setUsername(statusToExtract.getUsername())
                    .setPassword(statusToExtract.getPassword())
                    .setId(statusToExtract.getId())
                    .setEmail(statusToExtract.getMail())

                    .setOrganizationName(statusToExtract.getOrgName())
                    .setFacuiltyID(statusToExtract.getFaculityID())

                    .setFullName(statusToExtract.getFullName())
                    .setImageURL(statusToExtract.getImageURL())
                    .build();
        }


        return user;
    }

    @Override
    public void update(final ConnectionObject connectionObject)
    {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(connectionObject == null)
                {
                    // TODO - handle error case
                    Log.d(TAG,"connection object is null");
                    return;
                }

                if(connectionObject instanceof OrganizationsMessage)
                {
                    Log.d(TAG,"connectoion object org message");
                    OrganizationsMessage orgsMessage = (OrganizationsMessage) connectionObject;
                    spinnerOrgs = (Spinner) view.findViewById(R.id.spinner_organizations);
                    organizationsList = orgsMessage.getOrgs();
                    selectedOrganization = organizationsList.get(0);
                    OrganizationsAdapter adapter = new OrganizationsAdapter(getActivity(),
                            R.layout.item_organization,
                            organizationsList);
                    spinnerOrgs.setAdapter(adapter);
                    ((ActivityAuthenticate)getActivity()).hideProgressBar();
                }
                if(connectionObject instanceof RegisterStatus)
                {
                    Log.d(TAG,"connection object -> register user response");
                    RegisterStatus s = (RegisterStatus) connectionObject;
                    Log.d(TAG,"status = "+s.getStatus());

                    switch(s.getStatus())
                    {
                        case REGISTER_STATUS_EXIST:
                            AppUtils.showErrorMessage((AppCompatActivity) getActivity(),
                                    getString(R.string.cant_register_already_exist));
                            break;
                        case REGISTER_UNKNOWN_ERROR:
                            AppUtils.showErrorMessage((AppCompatActivity) getActivity(),
                                    getString(R.string.cant_register_server_error));
                            break;
                        case REGISTER_STATIS_OK:
                            if(s.getRegisterType().equals(REGISTER_TYPE_REGULAR))
                            {
                                User user = new User.Builder()
                                        .setPassword(s.getPassword())
                                        .setId(s.getId())
                                        .setOrganizationName(s.getOrgName())
                                        .setUsername(s.getUsername())
                                        .setEmail(s.getEmail())
                                        .setFullName(s.getFullName())
                                        .setImageURL(s.getImageURL())

                                        .build();

                                Log.d(TAG,"User id = "+user.getId());

                                // ((ActivityAuthenticate)getActivity()).openApp(user);
                            }else if(s.getRegisterType().equals(REGISTER_TYPE_GOOGLE)){
                                LoginGoogleMessage loginGoogleMessage = new LoginGoogleMessage();
                                DataManager.getInstance().getHttpExecutionPool().sendPostMessage(loginGoogleMessage,
                                        FragmentChooseOrganizationAndFaculty.this,
                                        getUserFromArguments().getUserMessage());
                            }
                            break;
                    }
                }
                if (connectionObject instanceof StatusAuthenticate)
                {
                    StatusAuthenticate statusToExtract = (StatusAuthenticate) connectionObject;
                    User user = new User.Builder()
                            .setUsername(statusToExtract.getUsername())
                            .setPassword(statusToExtract.getPassword())
                            .setId(statusToExtract.getId())
                            .setEmail(statusToExtract.getMail())
                            .setOrganizationId(statusToExtract.getOgranizationId())
                            .setIsTutor(statusToExtract.isTutor())
                            .setOrganizationName(statusToExtract.getOrgName())
                            .setFacuiltyID(statusToExtract.getFaculityID())
                            .setFacilityName(statusToExtract.getFacilityName())
                            .setFullName(statusToExtract.getFullName())
                            .setImageURL(statusToExtract.getImageURL())
                            .build();

                    Log.d(TAG,"USER DETAILS = "+user.toString());
                    ((ActivityAuthenticate)getActivity()).openApp(user);

                }
            }
        });

    }
    private void loginRegular(User NEWuser)
    {
        ValidationManager validManager = DataManager.getInstance().getValidationManager();


        {
            UserMessage user = new UserMessage();
            user.setEmail(NEWuser.getEmail().toString());
            user.setPassword(NEWuser.getPassword().toString());

            ((ActivityAuthenticate)getActivity()).showProgressBar();

            LoginMessage checkUserMessage = new LoginMessage();
            dataManager.getHttpExecutionPool().sendPostMessage(checkUserMessage,this,user);
        }


    }
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_register:
                registerUser();
                break;
        }
    }

    private User getUserFromArguments()
    {
        /*
            Get user details
         */
        String fullName = getArguments().getString(ARG_USER_FULL_NAME, "");
        String username = getArguments().getString(ARG_USERNAME, "");
        String email = getArguments().getString(ARG_USER_EMAIL, "");
        String password = getArguments().getString(ARG_USER_PASSWORD, "");
        String loginMethod = getArguments().getString(ARG_LOGIN_METHOD, "");
        String imageURL = getArguments().getString(ARG_USER_IMAGE_URL,"");
        String domainId = getArguments().getString(ARG_USER_DOMAIN_ID,"");

        Log.d(TAG,"Login method from arguments : "+loginMethod);
        Log.d(TAG,"image URL from arguments : "+imageURL);

        /*
            Build user
         */
        User user = new User.Builder()
                .setEmail(email)
                .setUsername(username)
                .setFullName(fullName)
                .setPassword(password)
                .setOrganizationId(selectedOrganization.getId())
                .setLoginMethod(loginMethod)
                .setImageURL(imageURL)
                .setLoginPlatformId(domainId)
                .build();

        return user;
    }

    private void registerUser()
    {
        if((selectedFaculity == null) || (selectedOrganization == null))
        {
            AppUtils.showToastMessage(getContext(),getString(R.string.must_specify_facility_organization));
            return;
        }
        Log.d(TAG,"registering user");

        /*
            Send register POST message
         */
        UserMessage userToPost = getUserFromArguments().getUserMessage();
        userToPost.setFacilityId(selectedFaculity.getId());
        userToPost.setOgranizationId(selectedOrganization.getId());

        Log.d(TAG,"User method"+userToPost.getLoginMethod());
        /*
        RegisterUserMessage registerUserMessage = new RegisterUserMessage();
        DataManager.getInstance().getHttpExecutionPool()
                                 .sendPostMessage(registerUserMessage,this,userToPost);*/

        loaderManager.restartLoader(REGISTER_USER_LOADER_ID, null, new RegisterUserLoaderCallback(userToPost));
    }

    private class OrgsOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            if( !  organizationsList.get(i).getId().equals(ID_NULL)) {
                selectedOrganization = organizationsList.get(i);
                Log.d(TAG,"initing loadier for faculities...");
                loaderManager.restartLoader(++FACULITIES_LOADER_ID, null, new LoaderCallbackForFaculities());
            }else {
                selectedOrganization = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            selectedOrganization = null;
        }
    }

    private class FaculitiesOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            if( !  faculitiesList.get(i).getId().equals(ID_NULL)) {
                selectedFaculity = faculitiesList.get(i);
                Log.d(TAG,"initing loadier for faculities...");
            }else {
                selectedFaculity = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            selectedFaculity = null;
        }
    }
}
