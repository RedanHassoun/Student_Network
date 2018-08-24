package com.mohred.studentnetwork.page_profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.MessageUpdateTutorProfile;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.Tutor;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_OK;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_ABOUT;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_HOW_TO_CONTACT;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_TUTOR_ID;

/**
 * Created by Redan on 3/10/2017.
 */

public class FragmentEditTutorProfile extends Fragment implements View.OnClickListener
{
    private View view;
    private EditText editAbout;
    private EditText editHowToContact;
    private String tutorId;
    private String textHowToContact;
    private String textAbout;
    private Button buttonApply;
    private ProgressBar progressBar;
    private FragmentTutorProfile prevFragment;

    public static FragmentEditTutorProfile newInstance(FragmentTutorProfile prevFragment) {
        FragmentEditTutorProfile fragment = new FragmentEditTutorProfile();
        fragment.setPrevFragment(prevFragment);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_edit_tutor_profile, container, false);

        editAbout = (EditText) view.findViewById(R.id.edit_text_about);
        editHowToContact = (EditText) view.findViewById(R.id.edit_text_how_to_contact);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        buttonApply = (Button) view.findViewById(R.id.button_submit);
        buttonApply.setOnClickListener(this);

        try {
            Bundle arguments = getArguments();
            tutorId = arguments.getString(ARG_TUTOR_ID);
            textHowToContact = getArguments().getString(ARG_HOW_TO_CONTACT);
            textAbout = getArguments().getString(ARG_ABOUT);

            if((tutorId == null) || (textHowToContact == null) || (textAbout == null))
                throw new IllegalArgumentException();

        }catch (Exception ex){
            ex.printStackTrace();
            AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
        }

        initView(textHowToContact,textAbout);
        return view;
    }

    private void initView(String textHowToContact, String textAbout)
    {
        editHowToContact.setText(textHowToContact);
        editAbout.setText(textAbout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                applyChanges();
                break;
        }
    }

    private void applyChanges()
    {
        if(!AppUtils.validateUpdateTutorProfile(getContext(),editAbout,editHowToContact))
        {
            AppUtils.showErrorMessage((AppCompatActivity) getActivity(),
                                        getString(R.string.please_fill_fields));

            return;
        }

        Tutor tutor = new Tutor();
        tutor.setAbout(editAbout.getText().toString());
        tutor.setCommunicationStr(editHowToContact.getText().toString());
        tutor.setId(tutorId);
        new UpdateTutorProfileTask(tutor).execute();
    }

    public FragmentTutorProfile getPrevFragment() {
        return prevFragment;
    }

    public void setPrevFragment(FragmentTutorProfile prevFragment) {
        this.prevFragment = prevFragment;
    }


    private class UpdateTutorProfileTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private Tutor tutor;
        public UpdateTutorProfileTask(Tutor tutor){
            this.tutor = tutor;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {
            try {
                MessageUpdateTutorProfile message = new MessageUpdateTutorProfile();
                return message.sendMessage(tutor);
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject)
        {
            super.onPostExecute(connectionObject);
            progressBar.setVisibility(View.GONE);

            try {
                com.mohred.studentnetwork.model.Status status =
                                            (com.mohred.studentnetwork.model.Status)connectionObject;

                switch (status.getStatus()){
                    case STATUS_OK:
                        AppUtils.showToastMessage(getContext(),getString(R.string.profile_updated_successfully));
                        try {
                            getFragmentManager()
                                    .beginTransaction()
                                    .detach(prevFragment)
                                    .attach(prevFragment)
                                    .commit();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        break;

                    default:
                        AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                }
            }catch (Exception ex){
                ex.printStackTrace();
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
            }


        }
    }
}
