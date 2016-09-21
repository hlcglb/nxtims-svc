package com.hyundaiuni.nxtims.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.domain.app.User;
import com.hyundaiuni.nxtims.exception.MessageDigestException;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.app.UserMapper;
import com.hyundaiuni.nxtims.util.MessageDigestUtils;
import com.hyundaiuni.nxtims.util.PasswordUtils;

@Service
public class UserService {
    private static final int LIMIT_AUTH_FAIL_CNT = 5;
    private static final String ALGORITHM = "SHA-256";
    private static final String CHARSET_NAME = "UTF-8";
    private static final int EXISTING_PASSWORD_LIMIT = 3;

    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<User> getUserListByParam(Map<String, Object> parameter, int offset, int limit) {
        Assert.notNull(parameter, "parameter must not be null");
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        parameter.put("offset", offset);
        parameter.put("limit", limit);

        return userMapper.getUserListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public User getUser(String userId) {
        Assert.notNull(userId, "userId must not be null");

        User user = userMapper.getUserById(userId);

        if(user != null) {
            List<Auth> authList = userMapper.getAuthByUserId(userId);

            if(CollectionUtils.isNotEmpty(authList)) {
                user.setAuthList(authList);
            }
        }

        return user;
    }

    public User insertUser(User user) {
        Assert.notNull(user, "user must not be null");

        checkMandatoryUser(user);
        
        if(!PasswordUtils.patternCheck(user.getUserPwd())) {
            throw new ServiceException("MSG.INVALID_PASSWORD", "Invalid Password.", null);
        }

        if(userMapper.getUserById(user.getUserId()) != null) {
            throw new ServiceException("MSG.DUPLICATED", user.toString() + " was duplicated.", null);
        }

        userMapper.insertUser(user);

        List<Auth> authList = user.getAuthList();

        if(CollectionUtils.isNotEmpty(authList)) {
            for(Auth auth : authList) {
                auth.setUserId(user.getUserId());

                if(StringUtils.isEmpty(auth.getAuthId())) {
                    throw new ServiceException("MSG.MUST_NOT_NULL", "AUTH_ID must not be null.",
                        new String[] {"AUTH_ID"});
                }

                if(userMapper.getUserAuthByPk(auth) != null) {
                    throw new ServiceException("MSG.DUPLICATED", auth.toString() + " was duplicated.", null);
                }

                userMapper.insertUserAuth(auth);
            }
        }

        return getUser(user.getUserId());
    }

    public void updateUser(User user) {
        Assert.notNull(user, "user must not be null");

        checkMandatoryUser(user);

        User tempUser = userMapper.getUserById(user.getUserId());

        if(tempUser == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", user.getUserId() + " not found.", null);
        }

        userMapper.updateUser(user);

        if(!tempUser.getUserPwd().equals(user.getUserPwd())) {
            if(!PasswordUtils.patternCheck(user.getUserPwd())) {
                throw new ServiceException("MSG.INVALID_PASSWORD", "Invalid Password.", null);
            }

            String password;

            try {
                password = MessageDigestUtils.getMessageDigest(user.getUserPwd(), ALGORITHM, CHARSET_NAME);
            }
            catch(MessageDigestException e) {
                throw new ServiceException("MSG.INVALID_PASSWORD", "Invalid Password.", null, e);
            }

            Map<String, Object> parameter = new HashMap<>();
            parameter.put("userPwd", password);
            parameter.put("sessionUserId", user.getUserPwd());
            parameter.put("userId", user.getUserId());
            parameter.put("limit", EXISTING_PASSWORD_LIMIT);

            if(userMapper.isExistingPasswordOnLog(parameter)) {
                throw new ServiceException("MSG.EXISTING_PASSWORD_ON_LOG", "Invalid Password.",
                    new String[] {Integer.toString(EXISTING_PASSWORD_LIMIT)});
            }

            userMapper.updateUserPwd(parameter);
            userMapper.insertUserPwdChangeLog(parameter);
        }

        List<Auth> authList = user.getAuthList();

        if(CollectionUtils.isNotEmpty(authList)) {
            for(Auth auth : authList) {
                auth.setUserId(user.getUserId());

                String authTransactionType = auth.getTransactionType();

                if(StringUtils.isEmpty(auth.getAuthId())) {
                    throw new ServiceException("MSG.MUST_NOT_NULL", "AUTH_ID must not be null.",
                        new String[] {"AUTH_ID"});
                }

                if("C".equals(authTransactionType)) {
                    Auth tempAuth = userMapper.getUserAuthByPk(auth);

                    if(tempAuth != null) {
                        throw new ServiceException("MSG.DUPLICATED", tempAuth.toString() + " was duplicated.", null);
                    }

                    userMapper.insertUserAuth(auth);
                }
                else if("D".equals(authTransactionType)) {
                    userMapper.deleteUserAuthByPk(auth);
                }
                else {
                    throw new ServiceException("MSG.TRANSACTION_TYPE_NOT_SUPPORTED",
                        authTransactionType + " was not supported.", null);
                }
            }
        }
    }

    public void deleteUser(String userId) {
        Assert.notNull(userId, "userId must not be null");

        User user = userMapper.getUserById(userId);

        if(user == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", user + " not found.", null);
        }

        userMapper.deleteUserAuthByUserId(userId);
        userMapper.deleteUserAccessLogByUserId(userId);
        userMapper.deleteUserPwdChangeLogByUserId(userId);
        userMapper.deleteUserByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Resource> getMenuByUserId(String userId) {
        Assert.notNull(userId, "userId must not be null");

        List<Resource> menuList = userMapper.getMenuByUserId(userId);

        return menuList;
    }

    @Transactional
    public void onAuthenticationSuccess(String userId, String sessionId, String accessIp) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(sessionId, "sessionId must not be null");
        Assert.notNull(accessIp, "accessIp must not be null");

        userMapper.updateLoginSuccess(userId);

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("userId", userId);
        parameter.put("sessionId", sessionId);
        parameter.put("accessIp", accessIp);

        userMapper.updateLoginDate(parameter);
    }

    @Transactional
    public void onAuthenticationFailure(String userId) {
        Assert.notNull(userId, "userId must not be null");

        User user = userMapper.getUserById(userId);

        if(user != null) {
            userMapper.updateAuthFailCnt(userId);

            int authFailCnt = userMapper.getAuthFailCnt(userId);

            if(LIMIT_AUTH_FAIL_CNT == authFailCnt) {
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("userId", userId);
                parameter.put("lockedYn", "Y");

                userMapper.updateLockedYn(parameter);
            }
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

    private void checkMandatoryUser(User user) {
        if(StringUtils.isEmpty(user.getUserId())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "USER_ID must not be null.", new String[] {"USER_ID"});
        }

        if(StringUtils.isEmpty(user.getUserNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "USER_NM must not be null.", new String[] {"USER_NM"});
        }

        if(StringUtils.isEmpty(user.getUserPwd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "USER_PWD must not be null.", new String[] {"USER_PWD"});
        }

        if(!(user.getUseYn().equals("Y") || user.getUseYn().equals("N"))) {
            throw new ServiceException("MSG.INVALID_DATA", "USE_YN is invalid.", new String[] {"USE_YN"});
        }
    }
}
