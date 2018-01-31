package com.wearewaes.controller.response;

/**
 * Class that represents an standardized message for user requests containing an user and developer message
 */
public class Response {

    private String userMessage;
    private String developerMessage;

    public Response() {
    }

    /**
     * Response constructor that
     *
     * @param userMessage that represents the user message
     * @param developerMessage that represents the developer message containing more details
     */
    public Response(String userMessage, String developerMessage) {
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
