package com.hyundaiuni.nxtims.service.framework;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundaiuni.nxtims.domain.framework.Auth;
import com.hyundaiuni.nxtims.mapper.framework.UsersMapper;

@Service
public class UsersService {
    @Autowired
    private UsersMapper usersMapper;
    
    @Transactional(readOnly = true)
    public com.hyundaiuni.nxtims.domain.framework.User getUser(String userId) {
        com.hyundaiuni.nxtims.domain.framework.User user = usersMapper.getUser(userId);
        
        Auth auth = new Auth();
        auth.setAuthId("ROLE_ADMIN");

        List<Auth> authList = new ArrayList<Auth>();
        authList.add(auth);

        user.setAuthList(authList);        

        return user;
    }
}
