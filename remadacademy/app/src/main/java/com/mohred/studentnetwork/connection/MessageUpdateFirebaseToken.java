package com.mohred.studentnetwork.connection;

import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.Status;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.URLs.URL_UPDATE_FIREBASE_TOKEN;

/**
 * Created by Redan on 2/17/2017.
 */
public class MessageUpdateFirebaseToken extends PostMessage
{
    public MessageUpdateFirebaseToken()
    {
        ArrayList<String> params = new ArrayList<>();
        params.add(URL_UPDATE_FIREBASE_TOKEN);
        messageURL = buildMessage(params);
    }
    @Override
    public ConnectionObject sendMessage(ConnectionObject toPost) {
        HttpHeaders requestHeaders = new HttpHeaders();
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(acceptableMediaTypes);

        // Populate the headers in an HttpEntity object to use for the request
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Add the Jackson and String message converters
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
        Status status = restTemplate.postForObject(messageURL, toPost, Status.class);
        return status;
    }
}
