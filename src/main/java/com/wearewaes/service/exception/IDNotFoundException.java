package com.wearewaes.service.exception;

import com.wearewaes.model.InputType;

/**
 * Exception that is thrown when the user try get the diff files result of an ID that does'n exist
 */
public class IDNotFoundException extends RuntimeException {

    private Long id;

    public IDNotFoundException(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
