package com.mohred.studentnetwork.page_profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.MessageGetUserByEmail;
import com.mohred.studentnetwork.connection.MessageLikeTutor;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.TutorLikeEntry;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;
import com.mohred.studentnetwork.page_fullscreen_image.ActivityFullScreenImage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_NOT_OK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_OK;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_TUTOR_PROFILE;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_IMAGE_URL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_IS_PROFILE;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_PROGILE_USER_MAIL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_TUTOR_ID;
import static com.mohred.studentnetwork.page_profile.ActivityProfile.FRAGMENT_EDIT_PROFILE;

/**
 * Created by Redan on 12/23/2016.
 */
public class FragmentShowProfile extends Fragment  implements View.OnClickListener
{
    private View view;
    private ImageView imgEdit;
    private TextView textFaculty;
    private TextView textPass;
    private TextView textOrg;
    private TextView textFullName;
    private TextView textMail;
    private TextView textAbout;
    private FloatingActionMenu menuFloatingMe;
    private FloatingActionMenu menuFloatingOther;
    private FloatingActionButton buttnOpenMyTutor;
    private FloatingActionButton buttonRequestTutoring;
    private FloatingActionButton buttonOpenOtherTutor;
    private FloatingActionButton buttonLikeTutor;
    private ProgressBar progressBar;
    private UserMessage currentUser;
    private String myMail = DataManager.getInstance().getCurrentUser(getContext()).getEmail();

    public static final FragmentShowProfile newInstance()
    {
        FragmentShowProfile fragment = new FragmentShowProfile();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_profile, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        Bundle arguments = getArguments();
        String userEmail = arguments.getString(ARG_PROGILE_USER_MAIL);
        if(userEmail!=null)
        {
            new GetUserDataTask(userEmail).execute();
        }else{
            AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
        }

        return view;
    }

    private class UIHandler extends Handler
    {
        @Override
        public void handleMessage(Message inputMessage)
        {
            UserMessage currentUser = (UserMessage) inputMessage.obj;

            initViews(currentUser);
        }
    }


    private void initViews(UserMessage currentUser)
    {
        FragmentShowProfile.this.currentUser = currentUser;
        TextView textProfileName = (TextView) view.findViewById(R.id.user_profile_name);
        textProfileName.setText(currentUser.getUsername());

        CircleImageView imageProfile = (CircleImageView) view.findViewById(R.id.user_profile_photo);
        final UserMessage user = currentUser;
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityFullScreenImage.class);
                Bundle b = new Bundle();
                b.putString(ARG_IMAGE_URL, user.getImageURL());
                b.putBoolean(ARG_IS_PROFILE,true);
                intent.putExtras(b);
                getActivity().startActivity(intent);
            }
        });

        if(currentUser.getImageURL() != null && !currentUser.getImageURL().equals("")){
            Picasso.with(getActivity())
                    .load(currentUser.getImageURL())
                    .placeholder(R.drawable.ic_perm_identity_black_24dp)
                    .error(R.drawable.ic_perm_identity_black_24dp)
                    .into(imageProfile);
        }

        textAbout = (TextView) view.findViewById(R.id.text_about);
        textAbout.setText(currentUser.getAbout());

        textMail = (TextView) view.findViewById(R.id.text_mail);
        if(currentUser.getEmail()!=null)
            textMail.setText(currentUser.getEmail());

        textFullName = (TextView) view.findViewById(R.id.text_fullname);
        if((currentUser.getFullName() !=null) && (!currentUser.getFullName().equals("")))
            textFullName.setText(currentUser.getFullName());

        textOrg = (TextView) view.findViewById(R.id.text_org);
        textOrg.setText(currentUser.getOrganizationName());

        textPass = (TextView) view.findViewById(R.id.text_password);
        if(currentUser.getEmail().equals(myMail))
        {
            if((currentUser.getPassword() !=null) && (!currentUser.getPassword().equals("")))
                textPass.setText(currentUser.getPassword());
            else {
                textPass.setInputType(InputType.TYPE_CLASS_TEXT);
                textPass.setText(getString(R.string.password_not_set));
            }
        }else {
            textPass.setVisibility(GONE);
            view.findViewById(R.id.title_password).setVisibility(GONE);
        }

        buttnOpenMyTutor = (FloatingActionButton) view.findViewById(R.id.menu_item_open_profile);
        buttonRequestTutoring = (FloatingActionButton) view.findViewById(R.id.menu_item_request_tutoring);
        buttonRequestTutoring.setOnClickListener(FragmentShowProfile.this);
        buttonOpenOtherTutor = (FloatingActionButton)view.findViewById(R.id.menu_item_open_profile_other);
        buttonOpenOtherTutor.setOnClickListener(FragmentShowProfile.this);
        buttonLikeTutor = (FloatingActionButton) view.findViewById(R.id.menu_item_like_tutor);
        buttonLikeTutor.setOnClickListener(FragmentShowProfile.this);

        textFaculty = (TextView) view.findViewById(R.id.text_faculty);
        textFaculty.setText(currentUser.getFacilityName());


        imgEdit=(ImageView) view.findViewById((R.id.img_Edit));

        if(currentUser.getEmail().equals(myMail))
        {
            imgEdit.setOnClickListener(FragmentShowProfile.this);
        }else {
            imgEdit.setVisibility(GONE);
        }

        handleTutorUI(currentUser);
    }

    private void handleTutorUI(UserMessage currentUser)
    {
        menuFloatingMe = (FloatingActionMenu) view.findViewById(R.id.menu_tutor_me);
        menuFloatingOther = (FloatingActionMenu) view.findViewById(R.id.menu_tutor_other);

        if(currentUser.getEmail().equals(myMail))
        {
            if(currentUser.isTutor())
            {
                menuFloatingMe.setVisibility(VISIBLE);
                menuFloatingOther.setVisibility(GONE);
                buttnOpenMyTutor.setOnClickListener(FragmentShowProfile.this);
            }else {
                menuFloatingMe.setVisibility(GONE);
                menuFloatingOther.setVisibility(GONE);
            }
        }else
        {
            if(currentUser.isTutor())
            {
                menuFloatingMe.setVisibility(View.GONE);
                menuFloatingOther.setVisibility(VISIBLE);
            }else {
                menuFloatingMe.setVisibility(View.GONE);
                menuFloatingOther.setVisibility(GONE);
            }
        }
    }




    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.img_Edit:
                if(getActivity() instanceof ActivityProfile)
                    ((ActivityProfile)getActivity()).replaceFragment(FRAGMENT_EDIT_PROFILE,
                                                                     null,
                                                                     true);
                else
                {
                    Fragment fragment = new FragmentEditProfile();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(FRAGMENT_EDIT_PROFILE);
                    transaction.commit();
                }
                break;

            case R.id.menu_item_open_profile:
                Bundle args = new Bundle();
                String tutorID = DataManager.getInstance().getCurrentUser(getContext()).getId();
                args.putString(ARG_TUTOR_ID,tutorID);
                FragmentTutorProfile fragment = FragmentTutorProfile.newInstance();
                fragment.setArguments(args);
                ((ActivityBase)getActivity()).replaceFragment(fragment,FRAGMENT_TUTOR_PROFILE,args,true);
                break;
            case R.id.menu_item_request_tutoring:
                DialogFragmentRequestTutoring dialogFragment = DialogFragmentRequestTutoring.newInstance();
                Bundle arguments = new Bundle();
                arguments.putString(ARG_TUTOR_ID,currentUser.getUserId());
                dialogFragment.setArguments(arguments);
                dialogFragment.show(getFragmentManager(),"dialog_request");
                break;


            case R.id.menu_item_open_profile_other:
                Bundle args2 = new Bundle();
                String tutorId = currentUser.getUserId();
                args2.putString(ARG_TUTOR_ID,tutorId);
                FragmentTutorProfile fragment1 = FragmentTutorProfile.newInstance();
                fragment1.setArguments(args2);
                ((ActivityBase)getActivity()).replaceFragment(fragment1,FRAGMENT_TUTOR_PROFILE,args2,true);
                break;

            case R.id.menu_item_like_tutor:
                likeTutor(currentUser.getUserId());
                break;
        }
    }

    private void likeTutor(String tutorId)
    {
        new LikeTutorTask(tutorId).execute();
    }

    private class LikeTutorTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private String tutorId;
        public LikeTutorTask(String tutorId)
        {
            this.tutorId = tutorId;
        }
        @Override
        protected ConnectionObject doInBackground(Void... params) {

            try{
                MessageLikeTutor message = new MessageLikeTutor();
                TutorLikeEntry tutorLike = new TutorLikeEntry();
                tutorLike.setTutorId(tutorId);
                tutorLike.setUserId(DataManager.getInstance().getCurrentUser(getContext()).getId());

                return message.sendMessage(tutorLike);
            }catch (Exception ex)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);

            try {
                com.mohred.studentnetwork.model.Status status =
                                            (com.mohred.studentnetwork.model.Status) connectionObject;

                switch (status.getStatus()){
                    case STATUS_OK:
                        AppUtils.showToastMessage(getContext(),getString(R.string.liked_tutor_success));
                        break;

                    case STATUS_NOT_OK:
                        AppUtils.showToastMessage(getContext(),getString(R.string.already_liked_tutor));
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
            }
        }
    }

    private class GetUserDataTask extends AsyncTask<Void,Void,UserMessage>
    {
        private String userEmail;
        public GetUserDataTask(String userEmail)
        {
            this.userEmail = userEmail;
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(VISIBLE);
        }

        @Override
        protected UserMessage doInBackground(Void... params)
        {
            try {
                User user = DataManager.getInstance().getCurrentUser(getContext());
                if(user.getEmail().equals(userEmail))
                {
                    return user.getUserMessage();
                }else {
                    MessageGetUserByEmail message = new MessageGetUserByEmail();
                    UserMessage userToPost= new UserMessage();
                    userToPost.setEmail(userEmail);
                    UserMessage userToShow = (UserMessage) message.sendMessage(userToPost);
                    return userToShow;
                }
            }catch (Exception ex)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(UserMessage userToShow)
        {
            super.onPostExecute(userToShow);

            progressBar.setVisibility(View.GONE);

            if(userToShow!=null){
                Message messageToHandle = new Message();
                messageToHandle.obj = userToShow;
                //new UIHandler().handleMessage(messageToHandle);

                initViews(userToShow);
            }
            else {
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
            }
        }
    }


}

