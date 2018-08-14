package com.cft.mamontov.imageprocessor.data.network;

import com.cft.mamontov.imageprocessor.data.models.ErrorResponse;
import com.google.gson.Gson;

import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Response;

public class ErrorHandler {

    private Gson mGson;

    @Inject
    public ErrorHandler(Gson gson) {
        this.mGson = gson;
    }

    public ErrorResponse parseError(Response<?> response) {
        try {
            return mGson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } finally {
            Objects.requireNonNull(response.errorBody()).close();
        }
    }
}