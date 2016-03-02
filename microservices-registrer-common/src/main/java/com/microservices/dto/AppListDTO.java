package com.microservices.dto;

import com.microservices.model.application.Application;

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
    private List<Application> data;

    public AppListDTO(){

    }

    public AppListDTO(List<Application> data) {
        this.data = data;
    }

    public List<Application> getData() {
        return data;
    }

    public void setData(List<Application> data) {
        this.data = data;
    }
}
