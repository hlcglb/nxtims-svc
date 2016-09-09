package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;
import java.util.Map;

import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.domain.app.Resource;

public interface ResourceMapper {
    public List<AuthResource> getResourceAuthAll();
    
    public List<Resource> getResourceListByParam(Map<String, Object> parameter);
    
    public Resource getResourceById(String resourceId);
    
    public String getResourceSequence();
    
    public void insertResource(Resource resource);
    
    public void updateResource(Resource resource);
    
    public void deleteResourceById(String resourceId);
}
