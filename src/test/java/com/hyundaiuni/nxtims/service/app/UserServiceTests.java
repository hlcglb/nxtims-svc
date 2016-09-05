package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.domain.app.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    private static final Log log = LogFactory.getLog(UserServiceTests.class);
    
    @Autowired
    private UserService userService;

    @Test
    public void testGetUser() {
        User user = userService.getUser("20006X");

        if(user != null) {
            fail("");
        }

        assertThat(userService.getUser("test")).isInstanceOf(User.class);
    }

    @Test
    public void testGetMenuByUserId() {
        List<Resource> menuList = userService.getMenuByUserId("20006X");

        if(!CollectionUtils.isEmpty(menuList)) {
            fail("");
        }

        menuList = userService.getMenuByUserId("test");

        if(CollectionUtils.isEmpty(menuList)) {
            fail("");
        }
    }

    @Test
    public void testOnAuthenticationSuccess() {
        Exception ex = null;
        
        try {
            userService.onAuthenticationSuccess("test", "DB18EBE12C90845710D544C7A15D7072", "localhost");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }
        
        assertEquals(null, ex);
    }
    
    @Test
    public void testOnAuthenticationFailure() {
        Exception ex = null;
        
        try {
            userService.onAuthenticationFailure("test");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }
        
        assertEquals(null, ex);
    }
    
    @Test
    public void testOnLogout() {
        Exception ex = null;
        
        try {
            userService.onLogout("test", "DB18EBE12C90845710D544C7A15D7072");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }
        
        assertEquals(null, ex);
    }    
}
