package com.mohred.studentnetwork.page_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.CheckUserStatusMessage;
import com.mohred.studentnetwork.connection.DataPostLoaderTask;
import com.mohred.studentnetwork.connection.LoginGoogleMessage;
import com.mohred.studentnetwork.connection.LoginMessage;
import com.mohred.studentnetwork.connection.MessageFacebookLogin;
import com.mohred.studentnetwork.dialog.DialogFragmentError;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.managers.ValidationManager;
import com.mohred.studentnetwork.model.Status;
import com.mohred.studentnetwork.model.StatusAuthenticate;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;
import org.json.JSONObject;
import java.util.Arrays;
import mehdi.sakout.fancybuttons.FancyButton;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.LOGIN_OK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.LOGIN_USER_NOT_EXIST;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.LOGIN_WRONG_PASSWORD;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_TYPE_FACEBOOK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REGISTER_TYPE_GOOGLE;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_NOT_EXIST;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_CHOOSE_ORGANIZATION;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_REGISTER;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_LOGIN_METHOD;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USERNAME;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_EMAIL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_FULL_NAME;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_IMAGE_URL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_USER_PASSWORD;

/**
 * Created by Redan on 12/3/2016.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener,
                                                        GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "fragment_login";
    private static final int RC_SIGN_IN = 9001;
    private LoginButton facebookLoginButton;
    private Button loginButton;
    private Button registerButton;
    private FancyButton myFacebookLogin;
    private FancyButton buttonGoogleLoginStyled;
    private SignInButton myGoogleLogin;
    private SignInButton googleLoginButton=null;
    private EditText EditTextpassword;
    private EditText EditTextEmail;
    private View view;
    private DataManager dataManager = DataManager.getInstance();
    private GoogleApiClient mGoogleApiClient=null;
    private User userToLogin;
    private static final int REGULAR_LOGIN_LOADER_ID = 1222;
    private LoaderManager loaderManager;
    private enum USER_TYPE {GOOGLE_USER,FACEBOOK_USER};
    private CallbackManager callbackManager;
    private ProgressBar progressBar;


    public static final FragmentLogin newInstance()
    {
        FragmentLogin fragment = new FragmentLogin();

        return fragment;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        handleLoginError();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        initFacebookLogin();
        initGoogleLogin();

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        EditTextEmail = (EditText) view.findViewById(R.id.edit_text_email);

        EditTextpassword = (EditText) view.findViewById(R.id.edit_text_password);

        loginButton = (Button) view.findViewById(R.id.regular_login_button);
        loginButton.setOnClickListener(this);

        registerButton = (Button) view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

        myGoogleLogin= (SignInButton) view.findViewById(R.id.button_google_my);
        myFacebookLogin = (FancyButton) view.findViewById(R.id.button_facebook_my);

        myFacebookLogin.setOnClickListener(this);
        myGoogleLogin.setOnClickListener(this);
        buttonGoogleLoginStyled = (FancyButton) view.findViewById(R.id.button_google_styled);
        buttonGoogleLoginStyled.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);


        Log.d(TAG,"on activity result : request code = "+requestCode+", result code = "+resultCode+", data = "+data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            //  case R.id.btn_google_signin:
            //     signInWithGoogle();
            //     break;

            case R.id.regular_login_button:
                loginRegular();
                break;

            case R.id.register_button:
                ((ActivityAuthenticate)getActivity()).replaceFragment(FRAGMENT_REGISTER,null,true);
                break;

            case R.id.button_google_styled:
                //myGoogleLogin.performClick();
                signInWithGoogle();
                break;

            case R.id.button_google_my:
                //signInWithGoogle();
                break;

            case R.id.button_facebook_my:
                facebookLoginButton.performClick();
                break;

        }
    }

    private void loginRegular()
    {
        ValidationManager validManager = DataManager.getInstance().getValidationManager();

        if(validManager.checkLoginFields(getActivity(),EditTextEmail,EditTextpassword)==true)
        {
            UserMessage user = new UserMessage();
            user.setEmail(EditTextEmail.getText().toString());
            user.setPassword(EditTextpassword.getText().toString());

            ((ActivityAuthenticate)getActivity()).showProgressBar();

            loaderManager = getLoaderManager();
            loaderManager.restartLoader(REGULAR_LOGIN_LOADER_ID, null, new RegularLoginLoaderCallback(user));
        }
    }

    private class RegularLoginLoaderCallback implements LoaderManager.LoaderCallbacks<ConnectionObject>
    {
        private UserMessage user;
        public RegularLoginLoaderCallback(UserMessage user)
        {
            this.user = user;
        }
        @Override
        public Loader<ConnectionObject> onCreateLoader(
                int id, Bundle args)
        {
            LoginMessage checkUserMessage = new LoginMessage();
            return new DataPostLoaderTask(getActivity(),checkUserMessage,user);
        }
        @Override
        public void onLoadFinished(
                Loader<ConnectionObject> loader, ConnectionObject data)
        {
            progressBar.setVisibility(View.GONE);
            // Display our data, for instance updating our adapter
            if(data == null){
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                Log.d(TAG,"NULL");
                return;
            }
            StatusAuthenticate userStatus = (StatusAuthenticate) data;

            ((ActivityAuthenticate)getActivity()).hideProgressBar();

            UIHandler handler = new UIHandler();
            Message message = new Message();
            message.obj = userStatus;
            handler.sendMessage(message);

        }
        @Override
        public void onLoaderReset(Loader<ConnectionObject> loader)
        {
        }
    }

    private class UIHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            StatusAuthenticate userStatus =(StatusAuthenticate) msg.obj;

            switch (userStatus.getStatus())
            {
                case LOGIN_OK:
                    User user = getUserDetails(userStatus);
                    ((ActivityAuthenticate)getActivity()).openApp(user);
                    break;
                case LOGIN_USER_NOT_EXIST:
                    showMessageToUser(getResources().getString(R.string.invalid_email));
                    break;
                case LOGIN_WRONG_PASSWORD:
                    showMessageToUser(getResources().getString(R.string.invalid_password));
                    break;
            }
        }
    }

    private User getUserDetails(Status status)
    {
        User user = null;

        StatusAuthenticate statusToExtract = (StatusAuthenticate) status;
        user = new User.Builder()
                .setUsername(statusToExtract.getUsername())
                .setPassword(statusToExtract.getPassword())
                .setId(statusToExtract.getId())
                .setEmail(statusToExtract.getMail())
                .setOrganizationId(statusToExtract.getOgranizationId())
                .setIsTutor(statusToExtract.isTutor())
                .setAbout(statusToExtract.getAbout())
                .setOrganizationName(statusToExtract.getOrgName())
                .setFacuiltyID(statusToExtract.getFaculityID())
                .setFacilityName(statusToExtract.getFacilityName())
                .setFullName(statusToExtract.getFullName())
                .setImageURL(statusToExtract.getImageURL())
                .build();
        Log.d(TAG,"RECEVIED STATUS = "+statusToExtract.isTutor());
        Log.d(TAG,"USER DETAILS = "+user.toString());

        return user;
    }

    private void showMessageToUser(String message)
    {
        DialogFragmentError errorDialog = DialogFragmentError.newInstance(message);
        errorDialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    /**
     * This method is invoked when the login process with one of the supported platforms fails
     */
    private void handleLoginError()
    {
        AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
        Log.d(TAG,"ERROR");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(mGoogleApiClient != null)
        {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    /*
        =======[ Google and Facebook ] =========================================================
     */
    private class MyFacebookCallback implements FacebookCallback<LoginResult>
    {
        public MyFacebookCallback()
        {
            Log.d(TAG,"buildind the callback");
        }
        @Override
        public void onSuccess(LoginResult loginResult)
        {
            Log.d(TAG,"on success");
            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback()
                    {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response)
                        {
                            Log.d(TAG,"response : "+ response.toString());

                            try
                            {
                                User user = new User.Builder()
                                        .setLoginPlatformId(object.getString("id"))
                                        .setEmail(object.getString("email"))
                                        .setUsername(object.getString("name"))
                                        .build();

                                handleAouthUser(user,USER_TYPE.FACEBOOK_USER);
                                Log.d(TAG,"User handled ");
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                Log.d(TAG,"ERROR ON FACEBOOK CALLBACK");
                                progressBar.setVisibility(View.GONE);
                                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel()
        {
            // do nothing
            Log.d(TAG,"cancel");
        }

        @Override
        public void onError(FacebookException exception)
        {
            exception.printStackTrace();
            handleLoginError();
        }
    }

    private void signInWithGoogle()
    {
        Intent signInIntent = Auth.GoogleSignInApi
                                    .getSignInIntent(((ActivityAuthenticate)getActivity())
                                    .getmGoogleApiClient());
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleAouthUser(final User user,USER_TYPE userType)
    {
        /*
            Save the user in order to make an HTTP request and handle in when the
            result comes back
         */
        userToLogin = user;

        /*
            Make the HTTP message to check the user status
         */
        //CheckUserStatusMessage statusMSG = new CheckUserStatusMessage();
        //DataManager.getInstance().getHttpExecutionPool().sendPostMessage(statusMSG,this,user.getUserMessage());
        new CheckUserStatusTask(userToLogin.getUserMessage(),userType).execute();
    }

    private class CheckUserStatusTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private UserMessage userMessage;
        private USER_TYPE registerType;

        public CheckUserStatusTask(UserMessage userMessage,USER_TYPE registerType)
        {
            this.userMessage = userMessage;
            this.registerType = registerType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {

            try {
                ConnectionObject respond;
                CheckUserStatusMessage statusMSG = new CheckUserStatusMessage();
                respond = statusMSG.sendMessage(userMessage);
                return respond;
            }catch (Exception ex){
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            progressBar.setVisibility(View.GONE);
            try {
                Log.d(TAG,"status user 1");
                StatusAuthenticate response = (StatusAuthenticate)connectionObject;
                Log.d(TAG,"status user 2 ");
                if(response.getStatus().equals(STATUS_NOT_EXIST))
                {
                    Log.d(TAG,"status user 3");
                        /*
                            Go to choose organization and register regullary
                         */
                    Bundle args = new Bundle();
                    User user = userToLogin;
                    args.putString(ARG_USER_FULL_NAME, user.getFullName());
                    args.putString(ARG_USERNAME,user.getUsername());
                    args.putString(ARG_USER_EMAIL,user.getEmail());
                    args.putString(ARG_USER_PASSWORD,user.getPassword());

                    Log.d(TAG,"image url = "+user.getImageURL());

                    args.putString(ARG_USER_IMAGE_URL,user.getImageURL());

                    if(registerType.equals(USER_TYPE.GOOGLE_USER))
                        args.putString(ARG_LOGIN_METHOD,REGISTER_TYPE_GOOGLE);
                    else
                        args.putString(ARG_LOGIN_METHOD,REGISTER_TYPE_FACEBOOK);

                    ((ActivityAuthenticate)getActivity()).replaceFragment(FRAGMENT_CHOOSE_ORGANIZATION,args,true);
                }
                else {
                   /*
                        Add user in Aouth
                    */
                    Log.d(TAG,"status user 4 ");
                    new LoginAouthTask(userToLogin.getUserMessage(),registerType).execute();
                }
            }catch (Exception ex){
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                ex.printStackTrace();
            }

        }
    }

    private class LoginAouthTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private UserMessage userMessage;
        private USER_TYPE userType;

        public LoginAouthTask(UserMessage userMessage, USER_TYPE userType)
        {
            this.userMessage = userMessage;
            this.userType = userType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {
            try {

                if(userType.equals(USER_TYPE.GOOGLE_USER)){
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
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            progressBar.setVisibility(View.GONE);
            try {
                StatusAuthenticate status = (StatusAuthenticate) connectionObject;

                switch (status.getStatus()){
                    case LOGIN_OK:
                        User user = getUserDetails(status);

                        ((ActivityAuthenticate)getActivity()).openApp(user);
                        break;
                    default:
                        AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                }
            }catch (Exception ex){
                ex.printStackTrace();
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
            }

        }
    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess())
        {
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User.Builder()
                    .setLoginPlatformId(acct.getId())
                    .setEmail(acct.getEmail())
                    .setUsername(acct.getGivenName()+" "+acct.getFamilyName())
                    .build();

            if(acct.getPhotoUrl()!=null)
                user.setImageURL(acct.getPhotoUrl().toString());

            Log.d(TAG,"User image url = "+user.getImageURL());

            handleAouthUser(user,USER_TYPE.GOOGLE_USER);
        }
        else
        {
            int statusCode = result.getStatus().getStatusCode();
            Log.d(TAG,"Status code = "+statusCode);
            handleLoginError();
        }
    }

    private void initFacebookLogin()
    {
        Log.d(TAG,"initiating facebook login");
        facebookLoginButton = (LoginButton) view.findViewById(R.id.login_button);
        facebookLoginButton.setReadPermissions("email");
        facebookLoginButton.setFragment(this);
        facebookLoginButton.setReadPermissions(Arrays.asList(
                                        "public_profile", "email", "user_birthday", "user_friends"));

        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(callbackManager,new MyFacebookCallback());
        LoginManager.getInstance().logOut();
    }

    private void initGoogleLogin()
    {
        try {
            if(googleLoginButton == null){
                googleLoginButton = (SignInButton)view.findViewById(R.id.button_google_my);
                googleLoginButton.setOnClickListener(this);
            }


            if(mGoogleApiClient == null){
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getActivity().getResources().getString(R.string.server_client_id))
                        .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                        .requestEmail()
                        .build();

                mGoogleApiClient = (new GoogleApiClient.Builder(getActivity())
                        .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build());

                ((ActivityAuthenticate)getActivity()).setmGoogleApiClient(mGoogleApiClient);

            /* Customize the button */
                SignInButton signInButton = (SignInButton) view.findViewById(R.id.button_google_my);
                signInButton.setSize(SignInButton.SIZE_STANDARD);
                signInButton.setScopes(gso.getScopeArray());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
