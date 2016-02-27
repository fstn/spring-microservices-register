package com.microServices.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephen on 27/02/2016.
 */
public class Invoice {
    private Double amount;
    private String id;
    private Double tva;
    private Map<String,Object> dynamicField = new HashMap<>();

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public Map<String, Object> getDynamicField() {
        return dynamicField;
    }

    public void setDynamicField(Map<String, Object> dynamicField) {
        this.dynamicField = dynamicField;
    }
}
