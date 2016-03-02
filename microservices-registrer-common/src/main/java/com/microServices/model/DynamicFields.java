package com.microservices.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Dynamic Fields : all unkown properties
 */
public class DynamicFields {

    private Map<String, Object> dynamicFields;


    public DynamicFields() {
        this.dynamicFields = new LinkedHashMap<>();
    }

    public DynamicFields(Map<String, Object> dynamicFields) {
        this.dynamicFields = dynamicFields;
    }

    public void setDynamicFields(Map<String, Object> dynamicFields) {
        this.dynamicFields = dynamicFields;
    }

    /**
     * Get dynamic fields
     * @return the dynamic fields
     */
    @JsonAnyGetter
    public Map<String, Object> getDynamicFields() {
        return dynamicFields;
    }

    /**
     * Add unknown bean field into Map of "dynamic fields"
     * @param name the name of the "dynamic field"
     * @param value the value of the "dynamic field"
     */
    @JsonAnySetter
    public void addDynamicField(String name, Object value ) {
        this.dynamicFields.put(name, value);
    }

    @Override
    public String toString() {
        return "DynamicFields{" +
                "dynamicFields=" + dynamicFields +
                '}';
    }
}
