package com.mohred.studentnetwork.page_tutors;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.connection.MessageUpdateTutorRequest;
import com.mohred.studentnetwork.dialog.DialogFragmentError;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.TutorRequest;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REQUEST_ACCEPTED;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.REQUEST_DECLINED;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_NOT_OK;

/**
 * Created by Redan on 2/3/2017.
 */

public class AdapterTutorRequests extends ArrayAdapter<TutorRequest>
{
    private int resource;
    private FragmentTutorRequests fragment;
    private List<TutorRequest> listRequests;
    private static final String TAG = "adapter_tutor_req";


    public AdapterTutorRequests(FragmentTutorRequests fragment, int resource, List<TutorRequest> listRequests)
    {
        super(fragment.getActivity(), resource, listRequests);

        this.resource = resource;
        this.fragment = fragment;
        this.listRequests = listRequests;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view;
        view = fragment.getActivity().getLayoutInflater().inflate(resource, parent, false);

        final Holder holder = new Holder();

        holder.buttonAccept = (Button) view.findViewById(R.id.button_accept);
        holder.buttonDecline = (Button) view.findViewById(R.id.button_decline);
        holder.imageProfile = (CircleImageView) view.findViewById(R.id.image_student);
        holder.textContent = (TextView) view.findViewById(R.id.text_content);
        holder.textDate = (TextView) view.findViewById(R.id.text_date);
        holder.textAlreadySent = (TextView) view.findViewById(R.id.text_already_sent);

        TutorRequest currentRequest = listRequests.get(position);

        /*
         * Set the content
         */
        holder.textContent.setText(currentRequest.getStudentName()+" "+
                                   fragment.getString(R.string.requested_tutoring_on)+" "+
                                   currentRequest.getCourseName());

        /*
         * Set the date
         */
        long timeStamp  = currentRequest.getTime().getTime();
        Date date = new Date(timeStamp);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String theDateText = df.format(date);
        holder.textDate.setText(theDateText);

        /*
         * Set image URL
         */
        try {
            Picasso.with(fragment.getContext())
                    .load(currentRequest.getUserImageURL())
                    .placeholder(R.drawable.ic_perm_identity_black_24dp)
                    .error(R.drawable.ic_perm_identity_black_24dp)
                    .into(holder.imageProfile);
        }catch (Exception ex){
            ex.printStackTrace();
            // SKIP
        }

        /*
         * Set listeners for the button
         */
        holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdateTutorRequst(REQUEST_ACCEPTED,position,holder);
            }
        });

        holder.buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdateTutorRequst(REQUEST_DECLINED,position,holder);
            }
        });

        /*
         * Set buttons or text
         */
        updateRequestStatusUI(holder,currentRequest.getStatus());

        return view;
    }

    private void updateRequestStatusUI(Holder holder,int status)
    {
        if(status == REQUEST_ACCEPTED){
            holder.buttonAccept.setVisibility(View.GONE);
            holder.buttonDecline.setVisibility(View.GONE);
            holder.textAlreadySent.setVisibility(View.VISIBLE);
            holder.textAlreadySent.setText(fragment.getActivity().getString(R.string.you_accepted_tutor_request));
        }
        if(status == REQUEST_DECLINED){
            holder.buttonAccept.setVisibility(View.GONE);
            holder.buttonDecline.setVisibility(View.GONE);
            holder.textAlreadySent.setVisibility(View.VISIBLE);
            holder.textAlreadySent.setText(fragment.getActivity().getString(R.string.you_declined_tutor_request));
        }
    }

    private void sendUpdateTutorRequst(int requestStatus,int requestPositionInArray,Holder uiHolder)
    {
        TutorRequest tutorRequest = listRequests.get(requestPositionInArray);
        tutorRequest.setStatus(requestStatus);
        Log.d(TAG,"initing loader");
        fragment.setProgressBarVisible(true);
        new UpdateTutorRequestTask(tutorRequest,uiHolder).execute();
    }

    private class UpdateTutorRequestTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        private TutorRequest tutorRequest;
        private Holder uiHolder;

        public UpdateTutorRequestTask(TutorRequest tutorRequest,Holder uiHolder)
        {
            this.tutorRequest = tutorRequest;
            this.uiHolder = uiHolder;
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {
            try {
                MessageUpdateTutorRequest message = new MessageUpdateTutorRequest();
                ConnectionObject data = message.sendMessage(tutorRequest);
                return data;
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            fragment.setProgressBarVisible(false);

            // Display our data, for instance updating our adapter
            if(connectionObject == null){
                DialogFragmentError dialogError = DialogFragmentError.newInstance(
                        fragment.getString(R.string.error_connecting_to_server));
                dialogError.show(fragment.getActivity().getSupportFragmentManager(),"error_dialog");
                Log.d(TAG,"NULL");
            }else {
                com.mohred.studentnetwork.model.Status status =
                                            (com.mohred.studentnetwork.model.Status)connectionObject;
                if(status.getStatus() != STATUS_NOT_OK){
                    updateRequestStatusUI(uiHolder,tutorRequest.getStatus());
                }else {
                    DialogFragmentError dialogError = DialogFragmentError.newInstance(
                            fragment.getString(R.string.general_error_message));
                    dialogError.show(fragment.getActivity().getSupportFragmentManager(),"error_dialog");
                }
            }

        }
    }

    private class Holder
    {
        private CircleImageView imageProfile;
        private TextView textContent;
        private TextView textDate;
        private Button buttonAccept;
        private Button buttonDecline;
        private TextView textAlreadySent;
    }
}
