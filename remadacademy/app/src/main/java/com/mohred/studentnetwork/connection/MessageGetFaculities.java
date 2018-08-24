package com.mohred.studentnetwork.connection;

import android.util.Log;

import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.FaculityMessage;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.URLs.URL_GET_FACULITIES;

/**
 * Created by Redan on 1/6/2017.
 */

public class MessageGetFaculities extends GetMessage
{
    public MessageGetFaculities(String orgID)
    {
        ArrayList<String> params = new ArrayList<>();
        params.add(URL_GET_FACULITIES);
        params.add(orgID);

        messageURL = buildMessage(params);
    }

    @Override
    public ConnectionObject sendMessage()
    {
        Log.d("CONNECTION_PROBLEM","Sending get request for faculities : "+messageURL);
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
        ResponseEntity<FaculityMessage> responseEntity = restTemplate.getForEntity(messageURL,FaculityMessage.class);

        // convert the array to a list and return it
        return responseEntity.getBody();
    }
}
