package com.microServices.model;

/**
 * Created by stephen on 27/02/2016.
 */
public class Invoice {
    private Double amount;
    private String id;
    private Double TVA;

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

    public Double getTVA() {
        return TVA;
    }

    public void setTVA(Double TVA) {
        this.TVA = TVA;
    }
}
