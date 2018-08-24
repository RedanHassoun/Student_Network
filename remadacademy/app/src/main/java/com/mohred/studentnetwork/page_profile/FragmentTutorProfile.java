package com.mohred.studentnetwork.page_profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.MessageGetTutorByID;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.UserMessage;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_ABOUT;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_HOW_TO_CONTACT;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_TUTOR_ID;

/**
 * Created by Redan on 1/28/2017.
 */
public class FragmentTutorProfile extends Fragment implements View.OnClickListener
{
    private View view;
    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textUsername;
    private TextView textNumLikes;
    private TextView dateStarted;
    private TextView about;
    private TextView communicationStr;
    private static final String TAG = "tutor_profile";
    private FloatingActionButton buttonEditProfile;
    private String tutorID;

    public static final FragmentTutorProfile newInstance()
    {
        FragmentTutorProfile fragment = new FragmentTutorProfile();
        return  fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_tutor_profile, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        imageView = (ImageView) view.findViewById(R.id.image_profile);
        textUsername = (TextView) view.findViewById(R.id.text_username) ;
        textNumLikes = (TextView) view.findViewById(R.id.text_num_likes) ;
        dateStarted = (TextView) view.findViewById(R.id.text_date_started);
        setAbout((TextView) view.findViewById(R.id.text_about));
        setCommunicationStr((TextView) view.findViewById(R.id.text_communication_str));
        buttonEditProfile= (FloatingActionButton) view.findViewById(R.id.button_edit_profile);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG,"on start");
        Bundle arguments = getArguments();
        tutorID = arguments.getString(ARG_TUTOR_ID);

        if(tutorID!=null)
            initData(tutorID);
        else {
            AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
            Log.d(TAG,"no args");
        }
    }

    private void initData(String tutorID)
    {
        Log.d(TAG,"initiating data... TUTOR id : "+tutorID);
        new GetDataTask(tutorID).execute();
    }

    private void initView(UserMessage tutor)
    {
        try {
            if(tutor.getImageURL() != null && !tutor.getImageURL().equals("")){
                Picasso.with(getActivity())
                        .load(tutor.getImageURL())
                        .placeholder(R.drawable.ic_perm_identity_black_24dp)
                        .error(R.drawable.ic_perm_identity_black_24dp)
                        .into(imageView);
            }

            textUsername.setText(tutor.getUsername());
            textNumLikes.setText(""+tutor.getNumLikes()+" "+getString(R.string.likes));
            String startDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                    .format(new Date(tutor.getDatedStartedToBeTutored().getTime()));
            dateStarted.setText(""+startDate);
            getAbout().setText(tutor.getAboutTutor());
            getCommunicationStr().setText(tutor.getCommunicationStr());

            if(tutorID.equals(DataManager.getInstance().getCurrentUser(getContext()).getId())){
                // If this tutor is the current user - show the edit profile option
                buttonEditProfile.setVisibility(View.VISIBLE);
                buttonEditProfile.setOnClickListener(this);

            }
        }catch (Exception ex){
            ex.printStackTrace();
            AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_edit_profile:
                openEditScreen();
                break;
        }
    }

    private void openEditScreen()
    {
        FragmentEditTutorProfile fragment = FragmentEditTutorProfile.newInstance(this);
        Bundle args = new Bundle();
        args.putString(ARG_TUTOR_ID,tutorID);
        args.putString(ARG_HOW_TO_CONTACT, getCommunicationStr().getText().toString());
        args.putString(ARG_ABOUT, getAbout().getText().toString());
        ((ActivityBase)getActivity()).addFragment(fragment,"edit_tutor_profile",args,true);
    }

    public TextView getAbout() {
        return about;
    }

    public void setAbout(TextView about) {
        this.about = about;
    }

    public TextView getCommunicationStr() {
        return communicationStr;
    }

    public void setCommunicationStr(TextView communicationStr) {
        this.communicationStr = communicationStr;
    }

    private class GetDataTask extends AsyncTask<Void,Void,UserMessage>
    {
        private String tutorID;
        public GetDataTask(String id)
        {
            this.tutorID=id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected UserMessage doInBackground(Void... params)
        {
            try {
                MessageGetTutorByID message = new MessageGetTutorByID(tutorID);
                ConnectionObject respond = message.sendMessage();

                UserMessage tutor = (UserMessage) respond;

                return tutor;
            }catch (Exception ex){
                return null;
            }
        }

        @Override
        protected void onPostExecute(UserMessage userMessage) {
            super.onPostExecute(userMessage);
            progressBar.setVisibility(View.GONE);
            if(userMessage!=null){
                initView(userMessage);
            }else {
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                Log.d(TAG,"recived null");
            }
        }


    }


}
