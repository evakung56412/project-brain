package com.man_jou.projectbrain.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.form.ApiJsonForm;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class PostTaskJson <T extends ApiJsonForm, E> extends AsyncTask<T, Void, ResponseEntity<E>> {

    private final Class<E> eClass;
    private ApiCallback<E> apiCallback;

    public PostTaskJson(Class<E> eClass, ApiCallback<E> apiCallback) {
        this.eClass = eClass;
        this.apiCallback = apiCallback;
    }

    @Override
    protected ResponseEntity<E> doInBackground(T... ts) {
        final String url = ts[0].getUrl(); // T extends from ApiJsonForm
        JSONObject jsonObject = ts[0].getJsonData();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);

        try {
            RestTemplate restTemplate = new RestTemplate(true);
            return restTemplate.exchange(url, HttpMethod.POST, httpEntity, eClass);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<E> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            apiCallback.postResult(responseEntity);
        } else {
            Log.e("PostTask", "Network Error: " + responseEntity.getStatusCode().toString());
        }
    }
}
