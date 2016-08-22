package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.domain.app.User;

public interface UsersMapper {
    public User getUserById(String userId);
    public List<Auth> getAuthByUserId(String userId);
    public List<Resource> getMenuByUserId(String userId);
}
