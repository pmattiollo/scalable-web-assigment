package com.wearewaes.builder;

import com.wearewaes.model.JSONData;
import com.wearewaes.model.JSONDataDTO;

/**
 * Utility class responsible for creating {@link JSONDataDTO} entities
 */
public class JSONDataDTOBuilder {

    private JSONDataDTO jsonDataDTO;

    private JSONDataDTOBuilder(JSONDataDTO jsonDataDTO) {
        this.jsonDataDTO = jsonDataDTO;
    }

    /**
     * Creates a default JSON Data simplified object (Data Transfer Object) with a default value
     *
     * @return the {@link JSONDataDTOBuilder} itself
     */
    public static JSONDataDTOBuilder oneData() {
        JSONDataDTO jsonDataDTO = new JSONDataDTO();
        jsonDataDTO.setValue("UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv");
        JSONDataDTOBuilder jsonDataDTOBuilder = new JSONDataDTOBuilder(jsonDataDTO);
        return jsonDataDTOBuilder;
    }

    /**
     * Defines an empty value
     *
     * @return the {@link JSONDataDTOBuilder} itself
     */
    public JSONDataDTOBuilder withoutValue() {
        jsonDataDTO.setValue(null);
        return this;
    }

    /**
     * Defines a value
     *
     * @param value that represents the value to be set
     * @return the {@link JSONDataDTOBuilder} itself
     */
    public JSONDataDTOBuilder withValue(String value) {
        jsonDataDTO.setValue(value);
        return this;
    }

    /**
     * Get the {@link JSONDataDTO}
     *
     * @return the {@link JSONDataDTO} created
     */
    public JSONDataDTO get() {
        return jsonDataDTO;
    }

}
