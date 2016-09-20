package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;
import java.util.Map;

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.domain.app.Resource;

public interface AuthMapper {
    public List<Auth> getAuthListByParam(Map<String, Object> parameter);

    public Auth getAuthByAuthId(String authId);

    public void insertAuth(Auth auth);

    public void updateAuth(Auth auth);

    public void deleteAuthByAuthId(String authId);
    
    public int getUserRowCountByAuthId(String authId);
    
    public AuthResource getAuthResourceByPk(Map<String, Object> parameter);
    
    public void insertAuthResource(AuthResource authResource);
    
    public void deleteAuthResourceByPk(Map<String, Object> parameter);
    
    public List<AuthResource> getAuthResourceListByAuthId(String authId);
    
    public void deleteAuthResourceByAuthId(String authId);
    
    public List<Resource> getNotExistsAuthResourceListByAuthId(String authId);
}
