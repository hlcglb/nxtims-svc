package com.hyundaiuni.nxtims.mapper.framework;

import java.util.List;

import com.hyundaiuni.nxtims.domain.framework.Auth;
import com.hyundaiuni.nxtims.domain.framework.User;

public interface UsersMapper {
    public User getUserById(String userId);
    public List<Auth> getAuthByUserId(String userId);
}
