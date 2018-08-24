package com.mohred.studentnetwork.connection;

import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.OrgFriendsList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import static com.mohred.studentnetwork.common.AppConstants.URLs.URL_GET_ALL_PEOPLE_FOR_ORG;

/**
 * Created by m7md on 1/14/2017.
 */

public class GetOrgFriends extends PostMessage
{
    public GetOrgFriends() {
        ArrayList<String> params = new ArrayList<>();

        params.add(URL_GET_ALL_PEOPLE_FOR_ORG);
        messageURL = buildMessage(params);
    }

    @Override
    public ConnectionObject sendMessage(ConnectionObject toPost)
    {
        HttpHeaders requestHeaders = new HttpHeaders();
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(acceptableMediaTypes);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        OrgFriendsList orgFriendsList = restTemplate.postForObject(messageURL, toPost, OrgFriendsList.class);
        return orgFriendsList;
    }
}
