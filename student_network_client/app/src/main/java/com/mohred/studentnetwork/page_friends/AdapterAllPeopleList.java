
package com.mohred.studentnetwork.page_friends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.model.UserMessage;
import com.mohred.studentnetwork.page_profile.FragmentShowProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_OTHER_USER_PROFILE;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_PROGILE_USER_MAIL;


public class AdapterAllPeopleList extends ArrayAdapter<UserMessage>
{
    private Activity activity;
    private List<UserMessage> listUsers;
    private int resource;

    public AdapterAllPeopleList(Activity context, int resource , List<UserMessage> listUsers)
    {
        super(context, resource, listUsers);
        this.listUsers = listUsers;
        this.activity = context;
        this.resource = resource;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup)
    {
        // TODO - handle this inflating problem
        View view = ((Activity)activity).getLayoutInflater().inflate(resource, viewGroup, false);

        TextView username = (TextView) view.findViewById(R.id.text_username);
        username.setText(listUsers.get(i).getUsername());

        CircleImageView imageProfile = (CircleImageView) view.findViewById(R.id.image_profile);
        String imageURL = listUsers.get(i).getImageURL();
        if(imageURL != null && imageURL != ""){
                Picasso.with(activity)
                        .load(listUsers.get(i).getImageURL())
                        .placeholder(R.drawable.ic_perm_identity_black_24dp)
                        .error(R.drawable.ic_perm_identity_black_24dp)
                        .into(imageProfile);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentShowProfile fragment = FragmentShowProfile.newInstance();
                Bundle args = new Bundle();
                args.putString(ARG_PROGILE_USER_MAIL,listUsers.get(i).getEmail());
                fragment.setArguments(args);
                ((ActivityBase)activity).replaceFragment(fragment,FRAGMENT_OTHER_USER_PROFILE,args,true);
            }
        });

        return view;
    }


}