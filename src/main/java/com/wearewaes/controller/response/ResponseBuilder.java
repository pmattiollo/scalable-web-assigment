package com.wearewaes.controller.response;

import static com.wearewaes.model.InputType.LEFT;
import static com.wearewaes.model.InputType.RIGHT;

import com.wearewaes.model.InputType;

public class ResponseBuilder {

    private Response response;

    private ResponseBuilder(Response response) {
        this.response = response;
    }

    public static ResponseBuilder oneResponse() {
        Response response = new Response();
        return new ResponseBuilder(response);
    }

    public static ResponseBuilder oneLeftDataSettedUpResponse() {
        return oneDataSettedUpResponse(LEFT);
    }

    public static ResponseBuilder oneRightDataSettedUpResponse() {
        return oneDataSettedUpResponse(RIGHT);
    }

    public static ResponseBuilder oneDiffResponse(String result) {
        Response response = new Response();
        response.setUserMessage(result);
        return new ResponseBuilder(response);
    }

    public ResponseBuilder withUserMessage(String userMessage) {
        response.setUserMessage(userMessage);
        return this;
    }

    public ResponseBuilder withDeveloperMessage(String developerMessage) {
        response.setDeveloperMessage(developerMessage);
        return this;
    }

    public Response get() {
        return response;
    }

    private static ResponseBuilder oneDataSettedUpResponse(InputType type) {
        Response response = new Response();
        response.setUserMessage("[OK] - " + type.name() + " data has been uploaded sucessfully");
        return new ResponseBuilder(response);
    }

}
