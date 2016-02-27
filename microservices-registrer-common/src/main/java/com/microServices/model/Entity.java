package com.microServices.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 27/02/2016.
 */
@XmlRootElement
public class Entity<T> {

    @XmlElement(name = "data")
    private T data;
    private List<StackTraceWSElement> stackTrace = new ArrayList<>();


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<StackTraceWSElement> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<StackTraceWSElement> stackTrace) {
        this.stackTrace = stackTrace;
    }
}
