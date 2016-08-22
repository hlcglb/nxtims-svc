package com.hyundaiuni.nxtims.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.User;

public class UserTest {
    @Test
    public void testObjectToJson() {
        Exception ex = null;
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            User user = new User();
            user.setUserId("admin");
            user.setUserPwd("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918"); //패스워드 admin sha-256
            user.setUseYn("Y");
            user.setExpiredYn("N");
            user.setPwdExpiredYn("N");
            user.setLockedYn("N");

            Auth auth = new Auth();
            auth.setAuthId("ROLE_ADMIN");

            ArrayList<Auth> authList = new ArrayList<Auth>();
            authList.add(auth);

            user.setAuthList(authList);

            String json = mapper.writeValueAsString(user);
            
            System.out.println(json);
            
            assertEquals(user.isUse(),true);
            assertEquals(user.isNonExpired(),true);
            assertEquals(user.isNonLocked(),true);
            assertEquals(user.isNonPwdExpired(),true);
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
            
            String json = "{\"nonExpired\":true,\"nonLocked\":true,\"nonPwdExpired\":true,\"use\":true,\"USER_ID\":\"admin\",\"USER_NM\":null,\"USER_PWD\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\",\"EXPIRED_YN\":\"N\",\"EXPIRED_YMD\":null,\"LOCKED_YN\":\"N\",\"PWD_EXPIRED_YN\":\"N\",\"PWD_EXPIRED_YMD\":null,\"USE_YN\":\"Y\",\"REG_USER_ID\":null,\"UPD_USER_ID\":null,\"AUTH_LIST\":[{\"AUTH_ID\":\"ROLE_ADMIN\"}]}";
            
            User user = mapper.readValue(json,User.class);
            
            System.out.println(user.toString());
        }
        catch(Exception e) {
            ex = e;
        }
        
        assertEquals(null, ex);
    }    
}
