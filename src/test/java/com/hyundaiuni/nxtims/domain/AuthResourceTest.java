package com.hyundaiuni.nxtims.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.AuthResource;

public class AuthResourceTest {
    @Test
    public void testObjectToJson() {
        Exception ex = null;
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            AuthResource authResource = new AuthResource();
            
            authResource.setAuthId("ROLE_ADMIN");
            authResource.setResourceUrl("/api/v1/app/users/*");
            authResource.setHttpMethod("GET");

            String json = mapper.writeValueAsString(authResource);
            
            System.out.println(json);
        }
        catch(Exception e) {
            ex = e;
        }
        
        assertEquals(null, ex);
    }
    
    @Test
    public void testJsonToObject() {
        Exception ex = null;
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            String json = "{\"AUTH_ID\":\"ROLE_ADMIN\",\"AUTH_NM\":null,\"RESOURCE_LEVEL\":null,\"RESOURCE_ID\":null,\"RESOURCE_NM\":null,\"RESOURCE_URL\":\"/api/v1/app/users/*\",\"RESOURCE_TYPE\":null,\"HTTP_METHOD\":\"GET\"}";
            
            AuthResource authResource = mapper.readValue(json,AuthResource.class);
            
            System.out.println(authResource.toString());
        }
        catch(Exception e) {
            ex = e;
        }
        
        assertEquals(null, ex);
    }    
}
