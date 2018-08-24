package com.mohred.studentnetwork.page_login;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.model.Faculity;

import java.util.List;

/**
 * Created by Redan on 1/6/2017.
 */

public class FaculitiesSpinnerAdapter extends ArrayAdapter<Faculity>
{
    private int resource;
    private Context context;
    private List<Faculity> facList;
    public FaculitiesSpinnerAdapter(Context context, int resource, List<Faculity> facList)
    {
        super(context, resource, facList);

        this.resource = resource;
        this.context = context;
        this.facList = facList;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        view = ((Activity)context).getLayoutInflater().inflate(resource, parent, false);

        Holder h = new Holder();
        h.name = (TextView) view.findViewById(R.id.text_title);
        h.name.setText(facList.get(position).getName());

        return view;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view = ((Activity)context).getLayoutInflater().inflate(resource, parent, false);

        Holder h = new Holder();
        h.name = (TextView) view.findViewById(R.id.text_title);
        h.name.setText(facList.get(position).getName());

        return view;
    }

    private class Holder
    {
        private TextView name;
    }
}
