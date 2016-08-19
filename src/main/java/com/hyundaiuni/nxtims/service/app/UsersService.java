package com.hyundaiuni.nxtims.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.User;
import com.hyundaiuni.nxtims.mapper.app.UsersMapper;

@Service
public class UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Transactional(readOnly = true)
    public User getUser(String userId) {
        Assert.notNull(userId, "userId must not be null");

        User user = usersMapper.getUserById(userId);

        if(user != null) {
            List<Auth> authList = usersMapper.getAuthByUserId(userId);

            if(!CollectionUtils.isEmpty(authList)) {
                user.setAuthList(authList);
            }
        }

        return user;
    }
}
