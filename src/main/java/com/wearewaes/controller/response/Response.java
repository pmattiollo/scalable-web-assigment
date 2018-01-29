package com.wearewaes.controller.response;

import org.springframework.stereotype.Component;

/**
 * Class that represents default messages to user requests
 */
public class Response {

    private String userMessage;
    private String developerMessage;

    public Response() {}

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
