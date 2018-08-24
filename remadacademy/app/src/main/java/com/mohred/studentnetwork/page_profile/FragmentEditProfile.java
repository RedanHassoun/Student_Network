package com.mohred.studentnetwork.page_profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.UpdateUserProfileMessage;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;
import com.mohred.studentnetwork.page_login.FragmentLogin;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_NOT_OK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_OK;

/**
 * Created by m7md on 12/31/2016.
 */

public class FragmentEditProfile extends Fragment implements View.OnClickListener
{
    private User currentUser;
    private View view;
    private EditText textMail;
    private EditText textUsername;
    private EditText textAbout;
    private EditText textFullName;
    private EditText textPass;
    private Button buttonSend;
    private CircleImageView imageProfile;
    private ProgressBar progressBar;
    private static final String TAG = "edit_profile";

    public static final FragmentLogin newInstance()
    {
        FragmentLogin fragment = new FragmentLogin();

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                                                     @Nullable Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_profile_edit, container, false);

        new InitUITask().execute();

        return view;
    }

    private void initViews()
    {
        textUsername = (EditText) view.findViewById(R.id.user_profile_name);
        textUsername.setText(currentUser.getUsername());

        buttonSend = (Button) view.findViewById(R.id.button_submit);
        buttonSend.setOnClickListener(this);

        CircleImageView imageProfile = (CircleImageView) view.findViewById(R.id.user_profile_photo);
        imageProfile.setOnClickListener(this);

        if(currentUser.getImageURL() != null && !currentUser.getImageURL().equals("")){
            Picasso.with(getActivity())
                    .load(currentUser.getImageURL())
                    .placeholder(R.drawable.ic_perm_identity_black_24dp)
                    .error(R.drawable.ic_perm_identity_black_24dp)
                    .into(imageProfile);
        }

        imageProfile= (CircleImageView) view.findViewById(R.id.user_profile_photo);
        imageProfile.setOnClickListener(this);

        textAbout = (EditText) view.findViewById(R.id.text_about);
        textAbout.setText(currentUser.getAbout());

        //textMail = (EditText) view.findViewById(R.id.text_mail);
        //textMail.setText(currentUser.getEmail());

        textFullName = (EditText) view.findViewById(R.id.text_fullname);
        textFullName.setText(currentUser.getFullName());


        textPass = (EditText) view.findViewById(R.id.text_password);
        textPass.setText(currentUser.getPassword());

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
    }

    private class InitUITask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            currentUser = DataManager.getInstance().getCurrentUser(getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initViews();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_submit:
                updateUserOnServer();
                break;

            case R.id.user_profile_photo:
                DialogFragmentUploadProfilePhoto fragment =
                                                    DialogFragmentUploadProfilePhoto.newInstance();
                fragment.show(getFragmentManager(),"photo_dialog");
                break;
        }
    }

    private void updateUserOnServer()
    {
        if(! AppUtils.validateUpdateProfileFields(getContext(),textUsername))
            return;

        /*
            Update current user
         */
        currentUser.setFullName(textFullName.getText().toString());
        currentUser.setAbout(textAbout.getText().toString());
        currentUser.setUsername(textUsername.getText().toString());
        currentUser.setPassword(textPass.getText().toString());

        UserMessage userToPost = currentUser.getUserMessage();
        new UploadUserUpdateTask(userToPost).execute();
    }

    private class UploadUserUpdateTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private UserMessage userToPost;
        public UploadUserUpdateTask(UserMessage userToPost)
        {
            this.userToPost = userToPost;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {
            try {
                UpdateUserProfileMessage message  = new UpdateUserProfileMessage();
                ConnectionObject toReturn = message.sendMessage(userToPost);
                return toReturn;
            }catch (Exception ex)
            {
                ex.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            progressBar.setVisibility(View.GONE);
            if(connectionObject == null){
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                return;
            }
            switch (((com.mohred.studentnetwork.model.Status)connectionObject).getStatus())
            {
                case STATUS_OK:
                    User userSession = DataManager.getInstance().getCurrentUser(getContext());
                    userSession.setPassword(userToPost.getPassword());
                    userSession.setFullName(userToPost.getFullName());
                    userSession.setAbout(userToPost.getAbout());
                    userSession.setUsername(userToPost.getUsername());

                    DataManager.getInstance().setCurrentUser(userSession,getContext());
                    AppUtils.showToastMessage(getContext(),getString(R.string.user_updated_successfully));
                    break;

                case STATUS_NOT_OK:
                    AppUtils.showToastMessage(getContext(),getString(R.string.error_ocurred_while_updating_user));
                    break;
            }
        }
    }
}
