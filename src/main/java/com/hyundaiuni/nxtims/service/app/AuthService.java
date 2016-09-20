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
import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.app.AuthMapper;

@Service
public class AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Transactional(readOnly = true)
    public List<Auth> getAuthListByParam(Map<String, Object> parameter, int offset, int limit) {
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        parameter.put("offset", offset);
        parameter.put("limit", limit);

        return authMapper.getAuthListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public List<AuthResource> getAuthResourceListByAuthId(String authId) {
        Assert.notNull(authId, "authId must not be null");

        return authMapper.getAuthResourceListByAuthId(authId);
    }

    @Transactional(readOnly = true)
    public Auth getAuthByAuthId(String authId) {
        Assert.notNull(authId, "authId must not be null");

        Auth auth = authMapper.getAuthByAuthId(authId);

        if(auth == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", authId + " not found.", null);
        }

        List<AuthResource> authResourceList = authMapper.getAuthResourceListByAuthId(authId);

        auth.setAuthResourceList(authResourceList);

        return auth;
    }

    @Transactional(readOnly = true)
    public List<Resource> getNotExistsAuthResourceListByAuthId(String authId) {
        Assert.notNull(authId, "authId must not be null");

        return authMapper.getNotExistsAuthResourceListByAuthId(authId);
    }

    @Transactional
    public Auth insertAuth(Auth auth) {
        Assert.notNull(auth, "auth must not be null");

        if(StringUtils.isEmpty(auth.getAuthId())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "AUTH_ID must not be null.", new String[] {"AUTH_ID"});
        }

        if(StringUtils.isEmpty(auth.getAuthNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "AUTH_NM must not be null.", new String[] {"AUTH_NM"});
        }

        Auth tempAuth = authMapper.getAuthByAuthId(auth.getAuthId());

        if(tempAuth != null) {
            throw new ServiceException("MSG.DUPLICATED", auth.toString() + " was duplicated.", null);
        }

        authMapper.insertAuth(auth);

        List<AuthResource> authResourceList = auth.getAuthResourceList();

        if(CollectionUtils.isNotEmpty(authResourceList)) {
            for(AuthResource authResource : authResourceList) {
                authResource.setAuthId(auth.getAuthId());

                if(StringUtils.isEmpty(authResource.getResourceId())) {
                    throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_ID must not be null.",
                        new String[] {"RESOURCE_ID"});
                }

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("authId", authResource.getAuthId());
                parameter.put("resourceId", authResource.getResourceId());

                AuthResource tempAuthResource = authMapper.getAuthResourceByPk(parameter);

                if(tempAuthResource != null) {
                    throw new ServiceException("MSG.DUPLICATED", tempAuthResource.toString() + " was duplicated.",
                        null);
                }

                authMapper.insertAuthResource(authResource);
            }
        }

        return getAuthByAuthId(auth.getAuthId());
    }

    @Transactional
    public void updateAuth(String authId, Auth auth) {
        Assert.notNull(authId, "authId must not be null");
        Assert.notNull(auth, "auth must not be null");

        Auth tempAuth = authMapper.getAuthByAuthId(authId);

        if(tempAuth == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", authId + " not found.", null);
        }

        if(StringUtils.isEmpty(auth.getAuthNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "AUTH_NM must not be null.", new String[] {"AUTH_NM"});
        }

        authMapper.updateAuth(tempAuth);

        List<AuthResource> authResourceList = auth.getAuthResourceList();

        if(CollectionUtils.isNotEmpty(authResourceList)) {
            for(AuthResource authResource : authResourceList) {
                String authResourceTransactionType = authResource.getTransactionType();

                if("C".equals(authResourceTransactionType) || "D".equals(authResourceTransactionType)) {
                    if(StringUtils.isEmpty(authResource.getResourceId())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_ID must not be null.",
                            new String[] {"RESOURCE_ID"});
                    }
                }

                authResource.setAuthId(auth.getAuthId());

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("authId", authResource.getAuthId());
                parameter.put("resourceId", authResource.getResourceId());

                if("C".equals(authResourceTransactionType)) {
                    AuthResource tempAuthResource = authMapper.getAuthResourceByPk(parameter);

                    if(tempAuthResource != null) {
                        throw new ServiceException("MSG.DUPLICATED", tempAuthResource.toString() + " was duplicated.",
                            null);
                    }
                }
                else if("D".equals(authResourceTransactionType)) {
                    AuthResource tempAuthResource = authMapper.getAuthResourceByPk(parameter);

                    if(tempAuthResource == null) {
                        throw new ServiceException("MSG.NO_DATA_FOUND", parameter.toString() + " not found.", null);
                    }
                }

                if("C".equals(authResourceTransactionType)) {
                    authMapper.insertAuthResource(authResource);
                }
                else if("D".equals(authResourceTransactionType)) {
                    authMapper.deleteAuthResourceByPk(parameter);
                }
                else {
                    throw new ServiceException("MSG.TRANSACTION_TYPE_NOT_SUPPORTED",
                        authResourceTransactionType + " was not supported.", null);
                }
            }
        }
    }

    @Transactional
    public void deleteAuthByAuthId(String authId) {
        Assert.notNull(authId, "authId must not be null");

        Auth auth = authMapper.getAuthByAuthId(authId);

        if(auth == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", authId + " not found.", null);
        }

        int userRowCount = authMapper.getUserRowCountByAuthId(authId);

        if(userRowCount > 0) {
            throw new ServiceException("MSG.DELETE_FIRST", "Delete user records of auth first",
                new String[] {"user records of auth"});
        }

        authMapper.deleteAuthResourceByAuthId(authId);
        authMapper.deleteAuthByAuthId(authId);
    }
}
