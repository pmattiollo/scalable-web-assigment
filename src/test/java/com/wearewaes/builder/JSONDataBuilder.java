package com.wearewaes.builder;

import com.wearewaes.model.JSONData;

public class JSONDataBuilder {

    private JSONData jsonData;

    public JSONDataBuilder(JSONData jsonData) {
        this.jsonData = jsonData;
    }

    public static JSONDataBuilder oneData() {
        JSONData jsonData = new JSONData();
        jsonData.setDiffId(1L);
        JSONDataBuilder jsonDataBuilder = new JSONDataBuilder(jsonData);
        return  jsonDataBuilder;
    }

    public JSONDataBuilder withId(Long id) {
        jsonData.setId(id);
        return this;
    }

    public JSONDataBuilder withLeft(String left) {
        jsonData.setLeft(left);
        return this;
    }

    public JSONDataBuilder withRight(String right) {
        jsonData.setRight(right);
        return this;
    }

    public JSONData get() {
        return jsonData;
    }
}
