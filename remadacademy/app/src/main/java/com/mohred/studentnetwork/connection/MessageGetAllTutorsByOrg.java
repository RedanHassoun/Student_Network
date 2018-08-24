package com.mohred.studentnetwork.connection;

import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.MessageListTutors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.URLs.URL_GET_ALL_TUTORS_BY_ORG;

/**
 * Created by Redan on 1/21/2017.
 */

public class MessageGetAllTutorsByOrg extends GetMessage
{
    public MessageGetAllTutorsByOrg(String orgID)
    {
        ArrayList<String> params = new ArrayList<>();
        params.add(URL_GET_ALL_TUTORS_BY_ORG);
        params.add(orgID);
        params.add("0"); // TODO

        messageURL = buildMessage(params);
    }
    @Override
    public ConnectionObject sendMessage()
    {
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
        ResponseEntity<MessageListTutors> responseEntity = restTemplate.getForEntity(messageURL,MessageListTutors.class);

        // convert the array to a list and return it
        return responseEntity.getBody();
    }
}
