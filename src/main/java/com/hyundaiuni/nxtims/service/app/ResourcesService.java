package com.hyundaiuni.nxtims.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.mapper.app.ResourcesMapper;

@Service
public class ResourcesService {
    @Autowired
    private ResourcesMapper resourcesMapper;
    
    @Transactional(readOnly = true)
    public List<AuthResource> getResources() {
        List<AuthResource> authResourceList = resourcesMapper.getResources();

        return authResourceList;
    }    
}
