package com.hyundaiuni.nxtims.service.framework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundaiuni.nxtims.domain.framework.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTests {
    @Autowired
    private UsersService usersService;

    @Test
    public void testGet() {
        User user = usersService.getUser("20006X");
            
        if(user != null){
            fail("");
        }

        assertThat(usersService.getUser("200065")).isInstanceOf(com.hyundaiuni.nxtims.domain.framework.User.class);
    }
}
