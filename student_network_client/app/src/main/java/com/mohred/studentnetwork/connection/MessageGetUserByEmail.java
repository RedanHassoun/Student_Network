package com.mohred.studentnetwork.connection;

import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.UserMessage;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.URLs.URL_GET_USER_BY_MAIL;

/**
 * Created by Redan on 1/20/2017.
 */
public class MessageGetUserByEmail extends PostMessage
{
    public MessageGetUserByEmail()
    {
        ArrayList<String> params=new ArrayList<>();
        params.add(URL_GET_USER_BY_MAIL);
        messageURL = buildMessage(params);
    }


    @Override
    public ConnectionObject sendMessage(ConnectionObject toPost)
    {
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
        UserMessage userMessage = restTemplate.postForObject(messageURL, toPost, UserMessage.class);
        return userMessage;
    }
}
