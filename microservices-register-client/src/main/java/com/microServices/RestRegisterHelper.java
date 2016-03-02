package com.microservices;

import com.microservices.model.App;
import com.microservices.model.EndPoint;
import com.microservices.model.Entity;
import com.microservices.model.StackTraceWSElement;
import com.microservices.serializer.DynamicSerializer;

/**
 * Created by SZA on 29/02/2016.
 *
 * Rest helper that help top execute endPoint on children
 */

public abstract class RestRegisterHelper<T> {

    RegisterClient<T> registerClient;
    private Double currentEndPointPriority = 100.0;
    private boolean currentEndPointDone = false;
    private DynamicSerializer<Object, T> dynamicSerializer;
    private Class<T> classType;

    public RestRegisterHelper(RegisterClient<T> registerClient, Class<T> classType) {
        this.registerClient = registerClient;
        this.dynamicSerializer =  new DynamicSerializer<>(Object.class,classType);
    }

    public abstract T run(T entity);

    /**
     * Call EndPoint on client order by priority
     * @param entity
     * @return
     */
    public T execute(Object entity){
        T transformedEntity = this.dynamicSerializer.transform(entity);

        EndPoint endPoint = registerClient.getEndPoint(this.getClass());
        for(App client: registerClient.getChildrenApp()){

            if(((Entity)transformedEntity).isStopChildren() || ((Entity)transformedEntity).isStopAll()){
                ((Entity)transformedEntity).setStopChildren(false);
                break;
            }
            if(client.getPriority() >= currentEndPointPriority && !currentEndPointDone){
                transformedEntity = executeCurrentEndPoint(transformedEntity, endPoint);
            }
            try {
                transformedEntity = registerClient.getRegisterUtil().executeOnChild(client, endPoint, transformedEntity);
            }catch(Exception e){
                ((Entity)transformedEntity).getStackError().add(new StackTraceWSError(client,endPoint,e))  ;
            }
        }
        if(! currentEndPointDone && !((Entity)transformedEntity).isStopAll()){
            transformedEntity =  executeCurrentEndPoint(transformedEntity, endPoint);
        }
        return transformedEntity;
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
