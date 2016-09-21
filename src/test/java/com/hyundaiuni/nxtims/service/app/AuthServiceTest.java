package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {
    private static final Log log = LogFactory.getLog(AuthServiceTest.class);
    
    @Autowired
    private AuthService authService;
    
    @Test
    public void testGetAuthListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();

            authService.getAuthListByParam(parameter, 0, 10);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
    
    @Test
    public void testGetAuthResourceListByAuthId(){
        Exception ex = null;
        
        try {
            authService.getAuthResourceListByAuthId("ADMIN");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);        
    }
    
    @Test
    public void testGetAuthByAuthId(){
        Exception ex = null;
        
        try {
            authService.getAuth("ADMIN");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
        
        try {
            authService.getAuth("TEST");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("not found");
        }        
    }
    
    @Test 
    public void testGetNotExistsAuthResourceListByAuthId(){
        Exception ex = null;
        
        try {
            authService.getNotExistsAuthResourceListByAuthId("ADMIN");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
    
    @Test
    public void testInsertAuth(){
        Exception ex = null;

        try {
            Auth auth = new Auth();

            auth.setAuthId("TEST");
            auth.setAuthNm("TEST");
            auth.setSessionUserId("200065");
            
            AuthResource authResource1 = new AuthResource();
            
            authResource1.setAuthId(auth.getAuthId());
            authResource1.setResourceId("000000");

            AuthResource authResource2 = new AuthResource();
            
            authResource2.setAuthId(auth.getAuthId());
            authResource2.setResourceId("000001");

            List<AuthResource> authResourceList = new ArrayList<>();
            authResourceList.add(authResource1);
            authResourceList.add(authResource2);

            auth.setAuthResourceList(authResourceList);

            Auth tempAuth = authService.insertAuth(auth);
            
            authService.deleteAuth(tempAuth.getAuthId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);        
    }
    
    @Test
    public void testInsertAuthDupError() {
        try {
            Auth auth = new Auth();

            auth.setAuthId("ADMIN");
            auth.setAuthNm("어드민");
            auth.setSessionUserId("200065");

            authService.insertAuth(auth);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("duplicated");
        }
    }
    
    @Test
    public void testInsertAuthResourceDupError() {
        try {
            Auth auth = new Auth();

            auth.setAuthId("TEST");
            auth.setAuthNm("TEST");
            auth.setSessionUserId("200065");
            
            AuthResource authResource1 = new AuthResource();
            
            authResource1.setAuthId(auth.getAuthId());
            authResource1.setResourceId("000000");

            AuthResource authResource2 = new AuthResource();
            
            authResource2.setAuthId(auth.getAuthId());
            authResource2.setResourceId("000000");

            List<AuthResource> authResourceList = new ArrayList<>();
            authResourceList.add(authResource1);
            authResourceList.add(authResource2);

            auth.setAuthResourceList(authResourceList);

            authService.insertAuth(auth);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("duplicated");
        }
    }
    
    @Test
    public void testUpdateAuth(){
        Exception ex = null;

        try {
            Auth auth = new Auth();

            auth.setAuthId("TEST");
            auth.setAuthNm("TEST");
            auth.setSessionUserId("200065");
            
            AuthResource authResource1 = new AuthResource();
            
            authResource1.setAuthId(auth.getAuthId());
            authResource1.setResourceId("000000");

            AuthResource authResource2 = new AuthResource();
            
            authResource2.setAuthId(auth.getAuthId());
            authResource2.setResourceId("000001");

            List<AuthResource> authResourceList = new ArrayList<>();
            authResourceList.add(authResource1);
            authResourceList.add(authResource2);

            auth.setAuthResourceList(authResourceList);

            Auth tempAuth = authService.insertAuth(auth);
            
            authResourceList = tempAuth.getAuthResourceList();
            
            if(CollectionUtils.isNotEmpty(authResourceList)) {
                for(AuthResource authResource : authResourceList) {
                    authResource.setTransactionType("D");
                }
            }            
            
            authService.updateAuth(tempAuth);
            authService.deleteAuth(tempAuth.getAuthId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);            
    }
}
