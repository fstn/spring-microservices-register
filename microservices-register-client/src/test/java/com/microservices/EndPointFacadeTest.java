package com.microservices;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by stephen on 02/03/2016.
 */
public class EndPointFacadeTest {

    private EndPointFacade endPointFacade;

    @Before
    public void setUp() throws Exception {
        endPointFacade = new EndPointFacade();
    }

    @Test
    public void createEndPoint(){
        try {
            endPointFacade.createEndPoint(this.getClass());
            fail("Must throw an exception because it missing annotation");
        }catch(NullPointerException e){
            assertEquals("msg txt","RestHelper must be use inside a JAX-RS endPoint,need have @Path, @POST,@GET,@PUT or @DELETE annotations",e.getMessage());
        }
    }
}
