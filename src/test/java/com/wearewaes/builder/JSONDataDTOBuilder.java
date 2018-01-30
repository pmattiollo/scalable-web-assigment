package com.wearewaes.builder;

import com.wearewaes.model.JSONData;
import com.wearewaes.model.JSONDataDTO;

public class JSONDataDTOBuilder {

    private JSONDataDTO jsonDataDTO;

    private JSONDataDTOBuilder(JSONDataDTO jsonDataDTO) {
        this.jsonDataDTO = jsonDataDTO;
    }

    public static JSONDataDTOBuilder oneData() {
        JSONDataDTO jsonDataDTO = new JSONDataDTO();
        jsonDataDTO.setValue("UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv");
        JSONDataDTOBuilder jsonDataDTOBuilder = new JSONDataDTOBuilder(jsonDataDTO);
        return jsonDataDTOBuilder;
    }

    public JSONDataDTOBuilder withoutValue() {
        jsonDataDTO.setValue(null);
        return this;
    }

    public JSONDataDTOBuilder withValue(String value) {
        jsonDataDTO.setValue(value);
        return this;
    }

    public JSONDataDTO get() {
        return jsonDataDTO;
    }

}
