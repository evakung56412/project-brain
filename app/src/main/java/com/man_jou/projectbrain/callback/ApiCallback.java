package com.man_jou.projectbrain.callback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ApiCallback<T> {
    void postResult(ResponseEntity<T> responseEntity);
    void getResult(ResponseEntity<T> responseEntity);
}
