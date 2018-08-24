package com.mohred.studentnetwork.page_tutors;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.model.UserMessage;
import com.mohred.studentnetwork.page_profile.FragmentShowProfile;
import com.squareup.picasso.Picasso;
import java.sql.Timestamp;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_TUTOR_PROFILE;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_PROGILE_USER_MAIL;

/**
 * Created by Redan on 1/27/2017.
 */
public class AdapterTutoredStudents extends ArrayAdapter<UserMessage>
{
    private int resource;
    private Activity activity;
    private List<UserMessage> listTutors;


    public AdapterTutoredStudents(Activity activity, int resource, List<UserMessage> listTutors)
    {
        super(activity, resource, listTutors);

        this.resource = resource;
        this.activity = activity;
        this.listTutors = listTutors;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view;
        view = activity.getLayoutInflater().inflate(resource, parent, false);
        final UserMessage user = listTutors.get(position);

        Holder holder = new Holder();

        holder.imageStudent = (CircleImageView) view.findViewById(R.id.item_image);
        holder.textName = (TextView) view.findViewById(R.id.text_name);
        holder.textCourseName = (TextView) view.findViewById(R.id.text_course_name);
        holder.textDateStarted = (TextView) view.findViewById(R.id.text_date_started);


        /*
         * init image
         */
        if(user.getImageURL()!=null && !user.getImageURL().equals(""))
        {
            Picasso.with(activity)
                    .load(user.getImageURL())
                    .placeholder(R.drawable.ic_perm_identity_black_24dp)
                    .error(R.drawable.ic_perm_identity_black_24dp)
                    .into( holder.imageStudent);
        }

        /*
         * init details
         */
        holder.textName.setText(user.getUsername());
        holder.textCourseName.setText(user.getTutoredCourseName());
        Timestamp dateStarted = user.getDatedStartedToBeTutored();
        try {
            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(""+dateStarted.getTime()),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            holder.textDateStarted.setText(timeAgo);
        }catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentShowProfile fragment = new FragmentShowProfile();
                Bundle arguments = new Bundle();
                String userEmail = user.getEmail();
                arguments.putString(ARG_PROGILE_USER_MAIL,userEmail);
                fragment.setArguments(arguments);
                ((ActivityBase)activity).addFragment(fragment,FRAGMENT_TUTOR_PROFILE,arguments,true);
            }
        });


        return view;
    }

    private class Holder
    {
        private CircleImageView imageStudent;
        private TextView textName;
        private TextView textCourseName;
        private TextView textDateStarted;
    }
}