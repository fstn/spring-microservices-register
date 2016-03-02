package com.microservices.model;

/**
 * Created by stephen on 27/02/2016.
 */
public class FrInvoice extends DynamicFields {
    private Double amount;
    private String id;
    private Double tvaFr;
    private Header header;

    public FrInvoice(){

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

    public Double getTvaFr() {
        return tvaFr;
    }

    public void setTvaFr(Double tvaFr) {
        this.tvaFr = tvaFr;
    }

    public Header getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "FrInvoice{" +
                "amount=" + amount +
                ", id='" + id + '\'' +
                ", tvaFr=" + tvaFr +
                ", header=" + header +
                "} " + super.toString();
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
