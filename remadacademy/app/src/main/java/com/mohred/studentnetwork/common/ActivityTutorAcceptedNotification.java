package com.mohred.studentnetwork.common;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.connection.MessageGetTutorByID;
import com.mohred.studentnetwork.model.UserMessage;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.ACCEPTED_YES;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.JSON_KEY_COURSE_NAME;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.JSON_KEY_IS_ACCEPTED;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.JSON_KEY_TUTOR_ID;

/**
 * Created by Redan on 2/18/2017.
 */

public class ActivityTutorAcceptedNotification extends AppCompatActivity
{
    private TextView textMessage;
    private TextView textCommunicationStr;
    private TextView textContactDetailsTitle;
    private ProgressBar progressBar;
    private String tutorID;
    private String courseName;
    private String isAccepted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_accepted_notification);

        Bundle args = getIntent().getExtras();
        if(args != null) {
            tutorID = args.getString(JSON_KEY_TUTOR_ID);
            courseName = args.getString(JSON_KEY_COURSE_NAME);
            isAccepted = args.getString(JSON_KEY_IS_ACCEPTED);
        }else {
            AppUtils.showToastMessage(getBaseContext(),getString(R.string.general_error_message));
        }

        textMessage = (TextView) findViewById(R.id.message);
        textCommunicationStr = (TextView) findViewById(R.id.comm_str);
        textContactDetailsTitle = (TextView) findViewById(R.id.text_contact_details);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        new InitContentTask().execute();
    }

    private class InitContentTask extends AsyncTask<Void,Void,UserMessage>
    {
        @Override
        protected UserMessage doInBackground(Void... params) {
            UserMessage respond = null;
            try {
                MessageGetTutorByID message = new MessageGetTutorByID(tutorID);
                respond = (UserMessage) message.sendMessage();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            return respond;
        }


        @Override
        protected void onPostExecute(UserMessage userMessage) {
            super.onPostExecute(userMessage);
            progressBar.setVisibility(View.GONE);
            try {
                if (isAccepted.equals(ACCEPTED_YES)) {
                    String message = userMessage.getUsername()+" "+
                                            getString(R.string.accepted_your_tutor_request_on)+
                                            " "+courseName;

                    textMessage.setText(message);

                    textCommunicationStr.setText(userMessage.getCommunicationStr());
                }else {
                    String message = userMessage.getUsername()+" "+
                            getString(R.string.rejected_your_tutor_request_on)+
                            " "+courseName;

                    textMessage.setText(message);

                    textContactDetailsTitle.setVisibility(View.GONE);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }


}
