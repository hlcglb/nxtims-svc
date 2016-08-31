package com.hyundaiuni.nxtims.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.domain.app.User;
import com.hyundaiuni.nxtims.mapper.app.UserMapper;

@Service
public class UserService {
    private static final int LIMIT_AUTH_FAIL_CNT = 5;

    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public User getUser(String userId) {
        Assert.notNull(userId, "userId must not be null");

        User user = userMapper.getUserById(userId);

        if(user != null) {
            List<Auth> authList = userMapper.getAuthByUserId(userId);

            if(!CollectionUtils.isEmpty(authList)) {
                user.setAuthList(authList);
            }
        }

        return user;
    }

    @Transactional(readOnly = true)
    public List<Resource> getMenuByUserId(String userId) {
        Assert.notNull(userId, "userId must not be null");

        List<Resource> menuList = userMapper.getMenuByUserId(userId);

        return menuList;
    }

    @Transactional
    public void onAuthenticationSuccess(String userId, String sessionId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(sessionId, "sessionId must not be null");

        userMapper.updateLastLoginDate(userId);

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("userId", userId);
        parameter.put("sessionId", sessionId);

        userMapper.updateLoginDate(parameter);
    }

    @Transactional
    public void onAuthenticationFailure(String userId) {
        Assert.notNull(userId, "userId must not be null");

        userMapper.updateAuthFailCnt(userId);

        int authFailCnt = userMapper.getAuthFailCnt(userId);

        if(LIMIT_AUTH_FAIL_CNT == authFailCnt) {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("userId", userId);
            parameter.put("lockedYn", "Y");

            userMapper.updateLockedYn(parameter);
        }
    }
    
    @Transactional
    public void onLogout(String userId, String sessionId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(sessionId, "sessionId must not be null");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("userId", userId);
        parameter.put("sessionId", sessionId);

        userMapper.updateLogoutDate(parameter);
    }    
}
