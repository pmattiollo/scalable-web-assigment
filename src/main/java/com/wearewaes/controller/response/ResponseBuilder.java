package com.wearewaes.controller.response;

import static com.wearewaes.model.InputType.LEFT;
import static com.wearewaes.model.InputType.RIGHT;

import com.wearewaes.model.InputType;

/**
 * Utility class responsible for creating Responses
 */
public class ResponseBuilder {

    private Response response;

    private ResponseBuilder(Response response) {
        this.response = response;
    }

    /**
     * Creates a default response with no content
     *
     * @return the {@link ResponseBuilder} itself
     */
    public static ResponseBuilder oneResponse() {
        Response response = new Response();
        return new ResponseBuilder(response);
    }

    /**
     * Creates a default response with an user message informing the left upload success
     *
     * @return the {@link ResponseBuilder} itself
     */
    public static ResponseBuilder oneLeftDataSettedUpResponse() {
        return oneDataSettedUpResponse(LEFT);
    }

    /**
     * Creates a default response with an user message informing the right upload success
     *
     * @return the {@link ResponseBuilder} itself
     */
    public static ResponseBuilder oneRightDataSettedUpResponse() {
        return oneDataSettedUpResponse(RIGHT);
    }

    /**
     * Creates a default response with an user message informing the diff result
     *
     * @param result that represents the diff result
     * @return the {@link ResponseBuilder} itself
     */
    public static ResponseBuilder oneDiffResponse(String result) {
        Response response = new Response();
        response.setUserMessage(result);
        return new ResponseBuilder(response);
    }

    /**
     * Defines an user message
     *
     * @param userMessage that represents the user message
     * @return the {@link ResponseBuilder} itself
     */
    public ResponseBuilder withUserMessage(String userMessage) {
        response.setUserMessage(userMessage);
        return this;
    }

    /**
     * Defines an developer message
     *
     * @param developerMessage that represents the developer message
     * @return the {@link ResponseBuilder} itself
     */
    public ResponseBuilder withDeveloperMessage(String developerMessage) {
        response.setDeveloperMessage(developerMessage);
        return this;
    }

    /**
     * Get the {@link Response}
     *
     * @return the {@link Response} created
     */
    public Response get() {
        return response;
    }

    /**
     * Creates a default response with an user message informing upload success
     *
     * @return the {@link ResponseBuilder} itself
     */
    private static ResponseBuilder oneDataSettedUpResponse(InputType type) {
        Response response = new Response();
        response.setUserMessage("[OK] - " + type.name() + " data has been uploaded sucessfully");
        return new ResponseBuilder(response);
    }

}
