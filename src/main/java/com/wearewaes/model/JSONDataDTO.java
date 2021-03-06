package com.wearewaes.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Data Transfer Object that represents an simplified {@link JSONData} for a user request
 */
public class JSONDataDTO {

    @ApiModelProperty(value = "This is the json value (Base64)", required = true)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JSONDataDTO that = (JSONDataDTO) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
