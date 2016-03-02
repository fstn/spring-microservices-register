package com.microservices.dto;

import com.microservices.model.application.Application;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

/**
 * Created by SZA on 01/03/2016.
 * Entity that encapsulate register apps result
 */
@XmlRootElement
public class AppDTO {

    @XmlElement(name = "data")
    private Application data;

    public AppDTO(){

    }

    public AppDTO(Optional<Application> data) {
        if(data.isPresent()){
            this.data = data.get();
        }
    }

    public AppDTO(Application data) {
        this.data = data;
    }

    public Application getData() {
        return data;
    }

    public void setData(Application data) {
        this.data = data;
    }

}
