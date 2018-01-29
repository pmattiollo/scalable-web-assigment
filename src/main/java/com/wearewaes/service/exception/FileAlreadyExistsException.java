package com.wearewaes.service.exception;

import com.wearewaes.model.InputType;

public class FileAlreadyExistsException extends RuntimeException {

    private Long id;
    private InputType type;

    public FileAlreadyExistsException(Long id, InputType type) {
        super();
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }
}
