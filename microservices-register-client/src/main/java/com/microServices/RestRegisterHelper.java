package com.microservices;

import com.microservices.model.App;
import com.microservices.model.EndPoint;
import com.microservices.model.Entity;
import com.microservices.model.StackTraceWSElement;

/**
 * Created by SZA on 29/02/2016.
 *
 * Rest helper that help top execute endPoint on children
 */

public abstract class RestRegisterHelper<T> {

    RegisterClient<T> registerClient;
    private Double currentEndPointPriority = 100.0;
    private boolean currentEndPointDone = false;


    public RestRegisterHelper(RegisterClient<T> registerClient) {
        this.registerClient = registerClient;
    }

    public abstract T run(T entity);

    /**
     * Call EndPoint on client order by priority
     * @param entity
     * @return
     */
    public T execute(T entity){
        EndPoint endPoint = registerClient.getEndPoint(this.getClass());
        for(App client: registerClient.getChildrenApp()){

            if(((Entity)entity).isStopChildren() || ((Entity)entity).isStopAll()){
                ((Entity)entity).setStopChildren(false);
                break;
            }
            if(client.getPriority() >= currentEndPointPriority && !currentEndPointDone){
                entity = executeCurrentEndPoint(entity, endPoint);
            }
            try {
                entity = registerClient.getRegisterUtil().executeOnChild(client, endPoint, entity);
            }catch(Exception e){
                ((Entity)entity).getStackError().add(new StackTraceWSError(client,endPoint,e))  ;
            }
        }
        if(! currentEndPointDone && !((Entity)entity).isStopAll()){
            entity =  executeCurrentEndPoint(entity, endPoint);
        }
        return entity;
    }

    /**
     * Execute current endPoint
     * @param entity
     * @param endPoint
     * @return
     */
    private T executeCurrentEndPoint(T entity, EndPoint endPoint) {
        entity = run(entity);
        ((Entity)entity).getStackTrace().add(new StackTraceWSElement(registerClient.getApp(),endPoint))  ;
        currentEndPointDone = true;
        return entity;
    }

    /**
     * Stop service inheritance
     * @param entity
     * @return
     */
    public T stopChildren(T entity){
        ((Entity)entity).setStopChildren(true);
        return entity;
    }
    /**
     * Stop service inheritance + parent
     * @param entity
     * @return
     */
    public T stopAll(T entity){
        ((Entity)entity).setStopAll(true);

        return entity;
    }

}
