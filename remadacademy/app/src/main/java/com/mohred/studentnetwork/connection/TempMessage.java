package com.mohred.studentnetwork.connection;

import android.util.Log;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Redan on 12/29/2016.
 */
public class TempMessage extends PostMessage
{
    private String path;
    private String TAG = "temp";

    public TempMessage(String path)
    {
        this.path = path;
        ArrayList<String> params = new ArrayList<>();
        params.add("uploadPhoto");
        params.add("1");
        messageURL = buildMessage(params);
        Log.d(TAG,"POST URL = "+messageURL);
    }

    @Override
    public ConnectionObject sendMessage(ConnectionObject toPost)
    {
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setCharset(Charset.forName("UTF8"));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add( formHttpMessageConverter );
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(path)); /* path - is the path of the
                                                            image i want to upload */
        map.add("username","user1");

        HttpHeaders imageHeaders = new HttpHeaders();
        imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<>(map, imageHeaders);
        restTemplate.exchange(messageURL, HttpMethod.POST, imageEntity, Boolean.class);

        return null;
    }


}
