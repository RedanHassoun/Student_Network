package com.mohred.studentnetwork.connection;

import android.util.Log;

import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.OrganizationsMessage;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.URLs.BASE_URL;
import static com.mohred.studentnetwork.common.AppConstants.URLs.GET_ALL_ORGANIZATIONS;

/**
 * Created by Redan on 12/19/2016.
 */

public class GetOrganizationsMessage extends GetMessage
{

    public  GetOrganizationsMessage()
    {
        messageURL = BASE_URL + "/" +GET_ALL_ORGANIZATIONS;
    }

    @Override
    public ConnectionObject sendMessage()
    {
        try
        {
            Log.d("CONNECTION_PROBLEM","Get organizations message URL : "+messageURL);
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
            ResponseEntity<OrganizationsMessage> responseEntity = restTemplate.getForEntity(messageURL,OrganizationsMessage.class);

            // convert the array to a list and return it
            return responseEntity.getBody();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
