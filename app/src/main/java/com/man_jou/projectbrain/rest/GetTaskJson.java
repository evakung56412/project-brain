package com.man_jou.projectbrain.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.fragment.UpdateProfileFragment;
import com.man_jou.projectbrain.model.Brain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class GetTaskJson<T> extends AsyncTask<String, Void, ResponseEntity<T>> {

    private final Class<T> tClass;
    private ApiCallback<T> callback;

    public GetTaskJson(Class<T> tClass, ApiCallback<T> callback) {
        this.tClass = tClass;
        this.callback = callback;
    }

    @Override
    protected ResponseEntity<T> doInBackground(String... uri) {

        final String url = uri[0];

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        try {
            RestTemplate restTemplate = new RestTemplate(true);
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, tClass);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<T> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            callback.getResult(responseEntity);
        } else {
            Log.e("GetTaskJson", "Network Error: " + responseEntity.getStatusCode().toString());
        }
    }
}
