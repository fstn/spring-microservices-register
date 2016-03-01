package com.microservices.model;

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
    private App data;

    public AppDTO(){

    }

    public AppDTO(Optional<App> data) {
        if(data.isPresent()){
            this.data = data.get();
        }
    }

    public AppDTO(App data) {
        this.data = data;
    }

    public App getData() {
        return data;
    }

    public void setData(App data) {
        this.data = data;
    }

}
