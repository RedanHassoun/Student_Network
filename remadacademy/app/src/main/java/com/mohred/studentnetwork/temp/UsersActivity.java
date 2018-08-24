package com.mohred.studentnetwork.temp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mohred.studentnetwork.R;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Redan on 12/10/2016.
 */
public class UsersActivity extends AppCompatActivity implements View.OnClickListener
{
    private ListView listView;
    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_users);

        initViews();
    }

    private void initViews()
    {
        listView = (ListView) findViewById(R.id.list_users);
        button = (Button) findViewById(R.id.button_refresh);
        button.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.the_progress_bar);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button_refresh:
                new DownloadUsersTask().execute();
                break;
        }
    }


    private class DownloadUsersTask extends AsyncTask<Void, Void, List<TheUser>>
    {
        @Override
        protected List<TheUser> doInBackground(Void... urls)
        {


            // The URL for making the GET request
            final String url = "http://10.0.2.2:8080/Proj5/getTheUsers";

            //http://10.0.2.2:8080/Proj5/getTheUsers
            // Set the Accept header
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);

            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Make the HTTP GET request, marshaling the response from JSON to an array of Events
            // Perform the HTTP GET request
            ResponseEntity<TheUser[]> responseEntity = restTemplate.getForEntity(url,TheUser[].class);

            // convert the array to a list and return it
            return Arrays.asList(responseEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<TheUser> result)
        {
            MyListAdapter adapter = new MyListAdapter(UsersActivity.this,result);
            listView.setAdapter(adapter);


        }
    }

    private class MyListAdapter extends ArrayAdapter<TheUser>
    {
        private List<TheUser> users;
        public MyListAdapter(Activity activity, List<TheUser> users)
        {
            super(activity, 0, users);
            this.users = users;
        }

        @Override
        public int getCount()
        {
            return users.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View view;
            view = getLayoutInflater().inflate(R.layout.item_temp, parent, false);
            ((TextView) view.findViewById(R.id.text_username)).setText(users.get(position).getUsername());
            ((TextView) view.findViewById(R.id.text_password)).setText("Password : "+users.get(position).getPassword());
            return view;
        }
    }
}
