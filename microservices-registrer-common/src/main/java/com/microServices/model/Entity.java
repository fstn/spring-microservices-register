package com.microservices.model;


import com.microservices.StackTraceWSError;

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
    private boolean stopAll;
    private boolean stopChildren;
    private List<StackTraceWSElement> stackTrace = new ArrayList<>();
    private List<StackTraceWSError> stackError = new ArrayList<>();


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

    public boolean isStopAll() {
        return stopAll;
    }

    public void setStopAll(boolean stopAll) {
        this.stopAll = stopAll;
    }

    public boolean isStopChildren() {
        return stopChildren;
    }

    public void setStopChildren(boolean stopChildren) {
        this.stopChildren = stopChildren;
    }

    public List<StackTraceWSError> getStackError() {
        return stackError;
    }

    public void setStackError(List<StackTraceWSError> stackError) {
        this.stackError = stackError;
    }
}
