package com.wearewaes.builder;

import com.wearewaes.model.JSONData;

/**
 * Utility class responsible for creating {@link JSONData} entities
 */
public class JSONDataBuilder {

    private JSONData jsonData;

    public JSONDataBuilder(JSONData jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * Creates a default JSON Data entity with a default diff ID value
     *
     * @return the {@link JSONDataBuilder} itself
     */
    public static JSONDataBuilder oneData() {
        JSONData jsonData = new JSONData();
        jsonData.setDiffId(1L);
        JSONDataBuilder jsonDataBuilder = new JSONDataBuilder(jsonData);
        return  jsonDataBuilder;
    }

    /**
     * Defines a database ID
     *
     * @param id that represents the database ID
     * @return the {@link JSONDataBuilder} itself
     */
    public JSONDataBuilder withId(Long id) {
        jsonData.setId(id);
        return this;
    }

    /**
     * Defines a left data
     *
     * @param left that represents the data
     * @return the {@link JSONDataBuilder} itself
     */
    public JSONDataBuilder withLeft(String left) {
        jsonData.setLeft(left);
        return this;
    }

    /**
     * Defines a right data
     *
     * @param right that represents the data
     * @return the {@link JSONDataBuilder} itself
     */
    public JSONDataBuilder withRight(String right) {
        jsonData.setRight(right);
        return this;
    }

    /**
     * Get the {@link JSONData}
     *
     * @return the {@link JSONData} created
     */
    public JSONData get() {
        return jsonData;
    }
}
