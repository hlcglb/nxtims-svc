package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.domain.app.User;
import com.hyundaiuni.nxtims.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    private static final Log log = LogFactory.getLog(UserServiceTests.class);

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();

            userService.getUserListByParam(parameter, 0, 10);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testInsertUserInvalidPassword() {
        try {
            User user = new User();

            user.setUserId("XXXXXX");
            user.setUserNm("XXXXXX");
            user.setUserPwd("1234qwer");
            user.setEmail("xxxxxx@hyundai-uni.com");            
            user.setUseYn("Y");

            userService.insertUser(user);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("Invalid");
        }
    }

    @Test
    public void testInsertUserInvalidAuthId() {
        try {
            User user = new User();

            user.setUserId("XXXXXX");
            user.setUserNm("XXXXXX");
            user.setUserPwd("12345qwert");
            user.setUseYn("Y");
            user.setEmail("xxxxxx@hyundai-uni.com");

            Auth auth = new Auth();
            auth.setAuthId("TEST");

            List<Auth> authList = new ArrayList<>();
            authList.add(auth);

            user.setAuthList(authList);

            userService.insertUser(user);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("not found");
        }
    }

    @Test
    public void testInsertUser() {
        try {
            User user = new User();

            user.setUserId("XXXXXX");
            user.setUserNm("XXXXXX");
            user.setUserPwd("12345qwert");
            user.setUseYn("Y");
            user.setEmail("xxxxxx@hyundai-uni.com");

            Auth auth = new Auth();
            auth.setAuthId("ANONYMOUS");

            List<Auth> authList = new ArrayList<>();
            authList.add(auth);

            user.setAuthList(authList);

            userService.insertUser(user);

            userService.deleteUser(user.getUserId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("not found");
        }
    }

    @Test
    public void testUpdateUser() {
        try {
            User user = new User();

            user.setUserId("XXXXXX");
            user.setUserNm("XXXXXX");
            user.setUserPwd("12345qwert");
            user.setUseYn("Y");
            user.setEmail("xxxxxx@hyundai-uni.com");

            Auth auth = new Auth();
            auth.setAuthId("ANONYMOUS");

            List<Auth> authList = new ArrayList<>();
            authList.add(auth);

            user.setAuthList(authList);

            userService.insertUser(user);

            user.setUserPwd("qwert12345");

            authList = user.getAuthList();

            if(CollectionUtils.isNotEmpty(authList)) {
                for(Auth tempAuth : authList) {
                    tempAuth.setTransactionType("D");
                }
            }

            Auth auth1 = new Auth();
            auth1.setAuthId("EMPLOYEE");
            auth1.setTransactionType("C");

            authList.add(auth1);

            user.setAuthList(authList);

            userService.updateUser(user);

            userService.deleteUser(user.getUserId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("not found");
        }
    }

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

        if(CollectionUtils.isNotEmpty(menuList)) {
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

    @Test
    public void testReissuePassword() {
        Exception ex = null;

        try {
            String userId = "test";
            String userNm = "테스트";
            String email = "byungsik.pyo@hyundai-uni.com";

            User user = userService.reissuePassword(userId, userNm, email);

            log.info("ReissuePassword is [" + user.getUserPwd() + "]");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
