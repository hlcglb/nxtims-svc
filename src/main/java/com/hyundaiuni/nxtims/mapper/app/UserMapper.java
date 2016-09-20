package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;
import java.util.Map;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.domain.app.User;

public interface UserMapper {
    public List<User> getUserListByParam(Map<String, Object> parameter);
    
    public User getUserById(String userId);
    
    public void insertUser(User user);
    
    public void updateUser(User user);
    
    public void deleteUserByUserId(String userId);

    public List<Auth> getAuthByUserId(String userId);

    public List<Resource> getMenuByUserId(String userId);

    public void updateLoginSuccess(String userId);

    public void updateLoginDate(Map<String, Object> parameter);
    
    public void updateAuthFailCnt(String userId);
    
    public int getAuthFailCnt(String userId);
    
    public void updateLockedYn(Map<String, Object> parameter);
    
    public void updateLogoutDate(Map<String, Object> parameter);
}
