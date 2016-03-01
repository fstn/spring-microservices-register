package com.microservices.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by SZA on 01/03/2016.
 * Entity that encapsulate register apps result
 */
@XmlRootElement
public class AppListDTO {

    @XmlElement(name = "data")
    private List<App> data;

    public AppListDTO(){

    }

    public AppListDTO(List<App> data) {
        this.data = data;
    }

    public List<App> getData() {
        return data;
    }

    public void setData(List<App> data) {
        this.data = data;
    }
}
