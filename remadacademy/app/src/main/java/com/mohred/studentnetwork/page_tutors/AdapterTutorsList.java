package com.mohred.studentnetwork.page_tutors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.model.UserMessage;
import com.mohred.studentnetwork.page_profile.FragmentShowProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_TUTOR_PROFILE;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_PROGILE_USER_MAIL;

/**
 * Created by Redan on 1/21/2017.
 */
public class AdapterTutorsList extends ArrayAdapter<UserMessage>
{
    private int resource;
    private AppCompatActivity activity;
    private List<UserMessage> listTutors;


    public AdapterTutorsList(AppCompatActivity activity, int resource, List<UserMessage> listTutors)
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

        final UserMessage tutor = listTutors.get(position);
        Holder holder = new Holder();

        holder.imageProfile = (ImageView)view.findViewById(R.id.image_profile);
        holder.username = (TextView) view.findViewById(R.id.text_username);

        try{
            Picasso.with(getContext())
                    .load(tutor.getImageURL())
                    .placeholder(R.drawable.ic_perm_identity_black_24dp)
                    .error(R.drawable.ic_perm_identity_black_24dp)
                    .into(holder.imageProfile);
        }catch (Exception ex){
            // SKIP
        }

        holder.username.setText(tutor.getUsername());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentShowProfile fragment = new FragmentShowProfile();
                Bundle arguments = new Bundle();
                String userEmail = tutor.getEmail();
                arguments.putString(ARG_PROGILE_USER_MAIL,userEmail);
                fragment.setArguments(arguments);
                ((ActivityBase)activity).addFragment(fragment,FRAGMENT_TUTOR_PROFILE,arguments,true);
            }
        });

        return view;
    }

    private class Holder
    {
        private ImageView imageProfile;
        private TextView username;
    }
}
