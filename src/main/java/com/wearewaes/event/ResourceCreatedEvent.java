package com.wearewaes.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/**
 * Event used when a new resource is created
 */
public class ResourceCreatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Long code;

    /**
     * Constructor that builds an resource creation
     *
     * @param source that represents the object that triggered the event
     * @param response that represents the requisition response
     * @param code that represents the resource identifier
     */
    public ResourceCreatedEvent(Object source, HttpServletResponse response, Long code) {
        super(source);
        this.response = response;
        this.code = code;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
