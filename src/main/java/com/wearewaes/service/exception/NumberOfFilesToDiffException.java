package com.wearewaes.service.exception;

public class NumberOfFilesToDiffException extends RuntimeException {

    private Long id;
    private boolean hasLeftJson;
    private boolean hasRightJson;

    public NumberOfFilesToDiffException(Long id, boolean hasLeftJson, boolean hasRightJson) {
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
