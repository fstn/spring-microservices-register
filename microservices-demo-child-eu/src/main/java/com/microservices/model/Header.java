package com.microservices.model;

import java.util.Date;

/**
 * Created by SOLO on 02/03/2016.
 */
public class Header extends DynamicFields {
    private String euId;
    private String euCovention;


    public Header() {
    }

    public String getEuId() {
        return euId;
    }

    public void setEuId(String euId) {
        this.euId = euId;
    }

    public String getEuCovention() {
        return euCovention;
    }

    public void setEuCovention(String euCovention) {
        this.euCovention = euCovention;
    }

    @Override
    public String toString() {
        return "Header{" +
                "euId='" + euId + '\'' +
                ", euCovention='" + euCovention + '\'' +
                "} " + super.toString();
    }
}
