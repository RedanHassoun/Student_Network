package com.mohred.studentnetwork.page_tutors;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.common.AppConstants;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.DataGetLoaderTask;
import com.mohred.studentnetwork.connection.DataPostLoaderTask;
import com.mohred.studentnetwork.connection.MessageGetCourses;
import com.mohred.studentnetwork.connection.MessagePOSTTutor;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.Course;
import com.mohred.studentnetwork.model.CourseMessage;
import com.mohred.studentnetwork.model.Status;
import com.mohred.studentnetwork.model.Tutor;
import com.mohred.studentnetwork.model.User;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Redan on 1/28/2017.
 */
public class DialogFragmentBecomeTutor extends DialogFragment implements View.OnClickListener
{
    private static final String TAG = "dialog_become_tutor";
    private View view;
    private ImageButton buttonExit;
    private Button buttonCancel;
    private Button buttonSubmit;
    private EditText editTextAbout;
    private EditText editTextCommStr;
    private ProgressBar progressBar;
    private MultiSpinnerSearch spinnerChooseCourses;
    private static final int TUTORED_COURSES_LOADER_ID = 20;
    private static final int ADD_TUTOR_LOADER = 21;
    private List<KeyPairBoolData> selectedCourses = null;
    private FragmentTutorsList prevFragment;


    public static DialogFragmentBecomeTutor newInstance(FragmentTutorsList prevFragment)
    {
        DialogFragmentBecomeTutor f = new DialogFragmentBecomeTutor();
        f.setPrevFragment(prevFragment);
        return f;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog  dialog = new Dialog(getActivity(), R.style.GrowFromPointTheme );
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_fragment_become_tutor, null);


        /*
            Exit button
         */
        Drawable exitDrawable = getActivity().getResources().getDrawable(R.drawable.ic_action_delete_sign_48px);
        exitDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.light_blue),
                PorterDuff.Mode.MULTIPLY));
        buttonExit = (ImageButton) view.findViewById(R.id.button_exit);
        buttonExit.setImageDrawable(exitDrawable);
        buttonExit.setOnClickListener(this);

        /*
            Edit texts
         */
        editTextAbout = (EditText) view.findViewById(R.id.text_about);
        editTextCommStr = (EditText) view.findViewById(R.id.text_communication_str);

        /*
         * Choose courses button
         */
        spinnerChooseCourses = (MultiSpinnerSearch) view.findViewById(R.id.spinner_choose_courses);
        //spinnerChooseCourses.setOnClickListener(this);

        /*
            Cancel button
         */
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(this);

        /*
            Submit button
         */
        buttonSubmit = (Button) view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);


        /*
            Progress bar
         */
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);


        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(TUTORED_COURSES_LOADER_ID, null, loaderCallbackForTutors);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        return dialog;
    }

    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForTutors =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    String facID = DataManager.getInstance().getCurrentUser(getActivity()).getFaculityID();
                    return new DataGetLoaderTask(getActivity(),new MessageGetCourses(facID)); // TODO - implement index
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    progressBar.setVisibility(GONE);
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                        Log.d(TAG,"NULL");
                        return;
                    }
                    CourseMessage respond = (CourseMessage)data;
                    List<Course> courses = respond.getCourses();

                    List<KeyPairBoolData> coursesKeyPair = coursesToKeyPairBool(courses);

                    initSpinnerForCourses(coursesKeyPair);
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                    progressBar.setVisibility(GONE);
                }
            };

    public FragmentTutorsList getPrevFragment() {
        return prevFragment;
    }

    public void setPrevFragment(FragmentTutorsList prevFragment) {
        this.prevFragment = prevFragment;
    }

    private class AddTutorLoader implements LoaderManager.LoaderCallbacks<ConnectionObject>
    {
        private Tutor tutor;


        public AddTutorLoader(Tutor tutor)
        {
            AddTutorLoader.this.tutor = tutor;
        }

        @Override
        public Loader<ConnectionObject> onCreateLoader(
                int id, Bundle args)
        {
            progressBar.setVisibility(View.VISIBLE);
            return new DataPostLoaderTask(getActivity(),new MessagePOSTTutor(),tutor);
        }
        @Override
        public void onLoadFinished(
                Loader<ConnectionObject> loader, ConnectionObject data)
        {
            progressBar.setVisibility(GONE);
            // Display our data, for instance updating our adapter
            if(data == null){
                AppUtils.showErrorMessage((AppCompatActivity)getActivity(),
                                            getString(R.string.general_error_message));
                Log.d(TAG,"NULL");
                return;
            }
            Status respond = (Status)data;
            Message message = new Message();
            message.obj = respond;
            UIHandler handler = new UIHandler();
            handler.sendMessage(message);

        }
        @Override
        public void onLoaderReset(Loader<ConnectionObject> loader)
        {
            progressBar.setVisibility(GONE);
        }
    }


    private class UIHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Status respond = (Status)msg.obj;
            if(respond.getStatus().equals(AppConstants.ConnectionConstants.STATUS_OK)){
                AppUtils.showToastMessage(getContext(),
                        getString(R.string.tutor_adding_succedd_message));
                User user = DataManager.getInstance().getCurrentUser(getContext());
                user.setTutor(true);
                DataManager.getInstance().setCurrentUser(user,getContext());
                ((ActivityBase)getActivity()).openActivity(ActivityTutors.class);
                getActivity().finish();
                DialogFragmentBecomeTutor.this.dismiss();
            }

            else
                AppUtils.showErrorMessage((AppCompatActivity)getActivity(),
                        getString(R.string.general_error_message));

            Log.d(TAG,"status = "+respond.getStatus());
        }
    }

    private List<KeyPairBoolData> coursesToKeyPairBool(List<Course> courses) {
        List<KeyPairBoolData> toReturn = new ArrayList<>();

        for(Course course : courses)
        {
            KeyPairBoolData currentKeyPair = new KeyPairBoolData();
            currentKeyPair.setId(Long.parseLong(course.getId()));
            currentKeyPair.setName(course.getName());
            currentKeyPair.setSelected(false);

            toReturn.add(currentKeyPair);
        }

        return toReturn;
    }

    private void initSpinnerForCourses(final List<KeyPairBoolData> listArray)
    {
        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch searchSpinner = (MultiSpinnerSearch)view. findViewById(R.id.spinner_choose_courses);
        /***
         * -1 is no by default selection
         * 0 to length will select corresponding values
         */
        searchSpinner.setItems(listArray, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        searchSpinner.setLimit(7, new MultiSpinnerSearch.LimitExceedListener() {
            @Override
            public void onLimitListener(KeyPairBoolData data) {
                Toast.makeText(getApplicationContext(),
                        "Limit exceed ", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.60f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_submit:
                becomeTutor();
                break;
            case R.id.button_cancel:
                this.dismiss();
                break;
            case R.id.button_exit:
                this.dismiss();
                break;

            case R.id.spinner_choose_courses:
                launchChooseCoursesDialog();
                break;
        }
    }

    private void becomeTutor()
    {
        if(validateFields()==true)
        {
            DataManager dataManager = DataManager.getInstance();
            Tutor tutor = new Tutor();
            tutor.setAbout(editTextAbout.getText().toString());
            tutor.setCommunicationStr(editTextCommStr.getText().toString());
            tutor.setId(dataManager.getCurrentUser(getContext()).getId());

            List<Course> listCourses = new ArrayList<>();

            for(KeyPairBoolData currCourse : selectedCourses){
                Course newCourse = new Course();
                newCourse.setId(""+currCourse.getId());
                listCourses.add(newCourse);
            }

            tutor.setCourses(listCourses);

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(ADD_TUTOR_LOADER, null, new AddTutorLoader(tutor));
        }
    }

    private boolean validateFields()
    {
        boolean isValid = true;

        if(editTextAbout.getText().toString().equals("")){
            isValid=false;
            editTextAbout.setError(getString(R.string.you_have_to_write_something_about_yourself));
        }

        if(editTextCommStr.getText().toString().equals("")){
            isValid = false;
            editTextCommStr.setError(getString(R.string.you_have_to_write_communication_desciption));
        }

        selectedCourses = spinnerChooseCourses.getSelectedItems();
        if(selectedCourses == null || selectedCourses.size()==0){
            if(isValid){
                AppUtils.showErrorMessage((AppCompatActivity) getActivity(),
                                            getString(R.string.you_have_to_select_atleast_one_coures));
            }
            isValid = false;
        }

        return isValid;
    }

    private void launchChooseCoursesDialog() {

    }
}
