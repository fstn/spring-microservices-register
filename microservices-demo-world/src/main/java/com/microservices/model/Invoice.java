package com.microservices.model;

/**
 * Created by stephen on 27/02/2016.
 */
public class Invoice extends DynamicFields{
    private Double amount;
    private String id;
    private Double tva;
    private String www;

    public Invoice(){

    }

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

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "amount=" + amount +
                ", id='" + id + '\'' +
                ", tva=" + tva +
                ", www='" + www + '\'' +
                "} " + super.toString();
    }
}
