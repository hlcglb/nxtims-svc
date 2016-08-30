package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

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
public class UsersServiceTests {
    @Autowired
    private UsersService usersService;

    @Test
    public void testGetUser() {
        User user = usersService.getUser("20006X");

        if(user != null) {
            fail("");
        }

        assertThat(usersService.getUser("test")).isInstanceOf(User.class);
    }

    @Test
    public void testGetMenuByUserId() {
        List<Resource> menuList = usersService.getMenuByUserId("20006X");

        if(!CollectionUtils.isEmpty(menuList)) {
            fail("");
        }

        menuList = usersService.getMenuByUserId("test");

        if(CollectionUtils.isEmpty(menuList)) {
            fail("");
        }
    }

    @Test
    public void testOnAuthenticationSuccess() {
        try {
            usersService.onAuthenticationSuccess("test", "DB18EBE12C90845710D544C7A15D7072");
        }
        catch(Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testOnAuthenticationFailure() {
        try {
            usersService.onAuthenticationFailure("test");
        }
        catch(Exception e) {
            fail(e.getMessage());
        }
    }    
}
