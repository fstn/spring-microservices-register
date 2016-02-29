package com.microservices;

/**
 * Created by SZA on 29/02/2016.
 *
 * Rest helper that help top execute endPoint on children
 */

public abstract class RestRegisterHelper<T> {

    RegisterClient<T> registerClient;

    public RestRegisterHelper(RegisterClient<T> registerClient) {
        this.registerClient = registerClient;
    }

    public abstract T run(T entity);
    public T execute(T entity){
        entity  = (T)registerClient.executeOnChildren(entity,this.getClass());
        entity = run(entity);
        return entity;
    };



}
