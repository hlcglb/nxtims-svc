package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;
import java.util.Map;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.domain.app.User;

public interface UserMapper {
    public List<User> getUserListByParam(Map<String, Object> parameter);
    
    public User getUserById(String userId);
    
    public User getUserByParam(Map<String, Object> parameter);
    
    public void insertUser(User user);
    
    public void updateUser(User user);
    
    public void updateUserPwd(Map<String, Object> parameter);
    
    public void deleteUserByUserId(String userId);
    
    public void deleteUserAuthByUserId(String userId);
    
    public void deleteUserAccessLogByUserId(String userId);
    
    public void deleteUserPwdChangeLogByUserId(String userId);

    public List<Auth> getAuthByUserId(String userId);
    
    public Auth getUserAuthByPk(Auth auth);
    
    public void insertUserAuth(Auth auth);
    
    public void deleteUserAuthByPk(Auth auth);

    public List<Resource> getMenuByUserId(String userId);

    public void updateLoginSuccess(String userId);

    public void updateLoginDate(Map<String, Object> parameter);
    
    public void updateAuthFailCnt(String userId);
    
    public int getAuthFailCnt(String userId);
    
    public void updateLockedYn(Map<String, Object> parameter);
    
    public void updateLogoutDate(Map<String, Object> parameter);
    
    public void insertUserPwdChangeLog(Map<String, Object> parameter);
    
    public boolean isExistingPasswordOnLog(Map<String, Object> parameter);
}
