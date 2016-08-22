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
            
        if(user != null){
            fail("");
        }

        assertThat(usersService.getUser("200065")).isInstanceOf(User.class);
    }
    
    @Test
    public void testGetMenuByUserId() {
        List<Resource> menuList = usersService.getMenuByUserId("20006X");
            
        if(!CollectionUtils.isEmpty(menuList)){
            fail("");
        }

        menuList = usersService.getMenuByUserId("200065");
        
        if(CollectionUtils.isEmpty(menuList)){
            fail("");
        }
    }    
}
