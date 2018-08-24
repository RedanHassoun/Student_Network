package com.mohred.studentnetwork.connection;

import android.util.Log;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.CourseAbout;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.URLs.URL_GET_COURSE_ABOUT;

/**
 * Created by Redan on 1/14/2017.
 */

public class MessageGetCourseAbout extends GetMessage
{
    private String courseId;
    private static String TAG = "course_about";

    public MessageGetCourseAbout(String courseId)
    {
        ArrayList<String> params = new ArrayList<>();
        params.add(URL_GET_COURSE_ABOUT);
        params.add(courseId);

        messageURL = buildMessage(params);
    }


    @Override
    public ConnectionObject sendMessage()
    {
        Log.d(TAG,"Sending message: "+messageURL);
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
        ResponseEntity<CourseAbout> responseEntity = restTemplate.getForEntity(messageURL,CourseAbout.class);

        // convert the array to a list and return it
        return responseEntity.getBody();
    }

}
