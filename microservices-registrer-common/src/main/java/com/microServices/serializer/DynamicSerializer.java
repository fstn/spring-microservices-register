package com.microservices.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DynamicSerializer<T, K>{

    private Class<T> classTypeInput;
    private Class<K> classTypeOutPut;

    public DynamicSerializer(){

    }

    public DynamicSerializer(Class<T> classTypeInput, Class<K> classTypeOutPut) {
        this.classTypeInput = classTypeInput;
        this.classTypeOutPut = classTypeOutPut;
    }

    /**
     * Deserialize json into object
     * @param jsonToDeserialize the json to deserialize
     * @return the deserialized object
     * @throws IOException exception
     */
    public K deSerialize(String jsonToDeserialize) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonToDeserialize, this.classTypeOutPut);
    }


    /**
     *
     * @param objectToSerialize object to serialize
     * @return the serialize object into string
     * @throws IOException exception
     */
    public String serialize(T objectToSerialize) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectToSerialize);
    }


    /**
     *
     * @param objectToTransform object to transform
     * @return the object transformed
     */
    public K transform(T objectToTransform){
        String json = null;
        try {
            json = this.serialize(objectToTransform);
        } catch (IOException e) {
            e.printStackTrace();
        }

        K invoiceEu = null;
        try {
            invoiceEu = (K) this.deSerialize(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invoiceEu;
    }
}
