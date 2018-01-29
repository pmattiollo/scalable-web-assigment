package com.wearewaes.service.exception;

import com.wearewaes.model.InputType;

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
