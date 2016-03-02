package com.microservices.model;

import java.util.Date;

/**
 * Created by SOLO on 02/03/2016.
 */
public class Header extends DynamicFields {
    private Date scanDate;
    private String supplierId;
    private String clientId;


    public Header() {
    }

    public Date getScandate() {
        return scanDate;
    }

    public void setScandate(Date scandate) {
        this.scanDate = scandate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    @Override
    public String toString() {
        return "Header{" +
                "scanDate=" + scanDate +
                ", supplierId='" + supplierId + '\'' +
                ", clientId='" + clientId + '\'' +
                "} " + super.toString();
    }
}
