package com.mohred.studentnetwork.page_login;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.model.Organization;

import java.util.List;
import java.util.Locale;

import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.ID_NULL;

/**
 * Created by Redan on 12/19/2016.
 */

public class OrganizationsAdapter extends ArrayAdapter<Organization>
{
    private int resource;
    private Context context;
    private List<Organization> orgList;
    private static final String TAG = "organiztions_adapter";

    public OrganizationsAdapter(Context context, int resource, List<Organization> orgList)
    {
        super(context, resource, orgList);

        this.resource = resource;
        this.context = context;
        this.orgList = orgList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        view = ((Activity)context).getLayoutInflater().inflate(resource, parent, false);

        Holder h = new Holder();
        h.name = (TextView) view.findViewById(R.id.text_title);
        h.name.setText(orgList.get(position).getName());

        if(!orgList.get(position).getId().equals(ID_NULL)){
            h.type =  (TextView) view.findViewById(R.id.text_type);
            h.location =  (TextView) view.findViewById(R.id.text_location);

            h.type.setText(context.getString(R.string.type)+": "+orgList.get(position).getType());
            String locationAddress = getLocationByLongLat(orgList.get(position).getLongitude(),
                                                          orgList.get(position).getLatitude());
            h.location.setText(locationAddress);
        }

        return view;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view = ((Activity)context).getLayoutInflater().inflate(resource, parent, false);

        Holder h = new Holder();
        h.name = (TextView) view.findViewById(R.id.text_title);

        h.name.setText(orgList.get(position).getName());

        if(!orgList.get(position).getId().equals(ID_NULL)){

            h.type =  (TextView) view.findViewById(R.id.text_type);
            h.location =  (TextView) view.findViewById(R.id.text_location);

            h.type.setText(context.getString(R.string.type)+": "+orgList.get(position).getType());
            new GetOrganizationLocationTask(h.location,orgList.get(position).getLongitude(),
                                                    orgList.get(position).getLatitude()).execute();
        }

        return view;
    }

    private class GetOrganizationLocationTask extends AsyncTask<Void,Void,String>
    {
        private TextView textView;
        private double longitude;
        private double latitude;

        public GetOrganizationLocationTask(TextView textView,double longitude,double latitude)
        {
            this.textView = textView;
            this.longitude = longitude;
            this.latitude = latitude;
        }
        @Override
        protected String doInBackground(Void... params) {
            String locationAddress = getLocationByLongLat(longitude,latitude);
            return locationAddress;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                textView.setText(s);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private String getLocationByLongLat(double longitude, double latitude)
    {
        Log.d(TAG,"lat = "+latitude+", long = "+longitude);
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.ENGLISH);

            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            return addresses.get(0).getLocality();
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

    private class Holder
    {
        private TextView name;
        private TextView type;
        private TextView location;
    }
}
