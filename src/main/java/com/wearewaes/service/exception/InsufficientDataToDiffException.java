package com.wearewaes.service.exception;

/**
 * Exception that is thrown when the user try get the diff files result but one of them is missing
 */
public class InsufficientDataToDiffException extends RuntimeException {

    private Long id;
    private boolean hasLeftJson;
    private boolean hasRightJson;

    public InsufficientDataToDiffException(Long id, boolean hasLeftJson, boolean hasRightJson) {
        super();
        this.id = id;
        this.hasLeftJson = hasLeftJson;
        this.hasRightJson = hasRightJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isHasLeftJson() {
        return hasLeftJson;
    }

    public void setHasLeftJson(boolean hasLeftJson) {
        this.hasLeftJson = hasLeftJson;
    }

    public boolean isHasRightJson() {
        return hasRightJson;
    }

    public void setHasRightJson(boolean hasRightJson) {
        this.hasRightJson = hasRightJson;
    }
}
