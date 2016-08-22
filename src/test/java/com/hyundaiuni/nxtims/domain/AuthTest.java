package com.hyundaiuni.nxtims.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.Auth;

public class AuthTest {
    @Test
    public void testObjectToJson() {
        Exception ex = null;
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            Auth auth = new Auth();
            auth.setAuthId("ROLE_ADMIN");

            String json = mapper.writeValueAsString(auth);
            
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
            
            String json = "{\"AUTH_ID\":\"ROLE_ADMIN\"}";
            
            Auth auth = mapper.readValue(json,Auth.class);
            
            System.out.println(auth.toString());
        }
        catch(Exception e) {
            ex = e;
        }
        
        assertEquals(null, ex);
    }    
}
