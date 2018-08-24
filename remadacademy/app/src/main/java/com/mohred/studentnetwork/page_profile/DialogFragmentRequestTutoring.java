package com.mohred.studentnetwork.page_profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.MessageGetTutoredCourses;
import com.mohred.studentnetwork.connection.MessagePOSTTutorRequest;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.Course;
import com.mohred.studentnetwork.model.CourseMessage;
import com.mohred.studentnetwork.model.TutorRequest;

import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_ALREADY_PENDING;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_OK;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_TUTOR_ID;

/**
 * Created by Redan on 2/4/2017.
 */
public class DialogFragmentRequestTutoring extends DialogFragment implements View.OnClickListener
{
    private ImageButton imageButton;
    private Spinner spinnerCourses;
    private static final String TAG = "request_tutoring";
    private Button buttonSubmit;
    private Button buttonCancel;
    private ImageButton buttonExit;
    private Course selectedCourse;
    private ProgressBar progressBar;

    public static DialogFragmentRequestTutoring newInstance()
    {
        DialogFragmentRequestTutoring f = new DialogFragmentRequestTutoring();

        return f;
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume()
    {
        super.onResume();
    }


    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog  dialog = new Dialog(getActivity(), R.style.GrowFromPointTheme );
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_request_tutor, null);

        buttonSubmit = (Button) view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(this);
        spinnerCourses = (Spinner) view.findViewById(R.id.spinner_courses);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        buttonExit = (ImageButton) view.findViewById(R.id.button_exit);
        buttonExit.setOnClickListener(this);
        Drawable exitDrawable = AppUtils.customizeDrawable(getContext(),
                                                            R.drawable.ic_action_delete_sign_48px);
        buttonExit.setImageDrawable(exitDrawable);

        Log.d(TAG,"getting arguments");
        Bundle args = getArguments();
        if(args !=null){
            Log.d(TAG,"arguments not null");
            String tutorID = args.getString(ARG_TUTOR_ID);
            Log.d(TAG,"tutor id : "+tutorID);
            new GetCoursesTask().execute(tutorID);
        }else {
            Log.d(TAG,"NO ARGUMENTS");
            AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
        }


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return dialog;
    }

    @Override
    public void	onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
    }

    @Override
    public void	onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
    }

    @Override
    public void	onStart()
    {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.60f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                makeTutorRequest();
                break;
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_exit:
                dismiss();
                break;
        }
    }

    private void makeTutorRequest()
    {
        selectedCourse = (Course) spinnerCourses.getSelectedItem();
        if(selectedCourse.getId().equals("-1")){
            AppUtils.showToastMessage(getContext(),getString(R.string.you_must_select_course));
            return;
        }else {
            new RequestTutoringTask().execute();
        }
    }

    private class RequestTutoringTask extends AsyncTask<Void,Void,ConnectionObject>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {

            try {
                MessagePOSTTutorRequest message = new MessagePOSTTutorRequest();

                TutorRequest tutorRequest = new TutorRequest();
                tutorRequest.setCourseId(Integer.parseInt(selectedCourse.getId()));
                String userId = DataManager.getInstance().getCurrentUser(getContext()).getId();
                tutorRequest.setStudentId(Integer.parseInt(userId));
                Bundle args = getArguments();
                String tutorID = args.getString(ARG_TUTOR_ID);
                tutorRequest.setTutorId(Integer.parseInt(tutorID));

                return message.sendMessage(tutorRequest);
            }catch (Exception ex)
            {
                ex.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            progressBar.setVisibility(View.VISIBLE);
            if(connectionObject != null ){
                com.mohred.studentnetwork.model.Status status =
                                (com.mohred.studentnetwork.model.Status) connectionObject;

                switch (status.getStatus()){
                    case STATUS_OK:
                        AppUtils.showToastMessage(getContext(),getString(R.string.requested_tutoring_successfully));
                        break;

                    case STATUS_ALREADY_PENDING:
                        AppUtils.showToastMessage(getContext(),getString(R.string.already_sent_tutor_request));
                        break;
                    default:
                        AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                }
            }else {
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                Log.d(TAG,"ERROR on respond !!! ");
            }

            dismiss();
        }
    }

    private class GetCoursesTask extends AsyncTask<String,Void,ConnectionObject>
    {

        @Override
        protected ConnectionObject doInBackground(String... params)
        {

            try {
                String tutorID = params[0];
                MessageGetTutoredCourses tutoredCourses = new MessageGetTutoredCourses(tutorID,0); // TODO - implement index

                return tutoredCourses.sendMessage();
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject)
        {
            super.onPostExecute(connectionObject);
            if(connectionObject !=null) {
                CourseMessage respond = (CourseMessage) connectionObject;
                List<Course> courseList = respond.getCourses();
                Course defultSelection = new Course();
                defultSelection.setId("-1");
                defultSelection.setName(getString(R.string.select_course));
                courseList.add(0,defultSelection);
                TutorCoursesSpinnerAdapter adapter =
                        new TutorCoursesSpinnerAdapter(getContext(),
                                                       R.layout.item_list_course,
                                                       respond.getCourses());
                spinnerCourses.setAdapter(adapter);
            }else {
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
            }

        }
    }
}
