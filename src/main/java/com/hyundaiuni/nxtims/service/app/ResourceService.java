package com.hyundaiuni.nxtims.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.mapper.app.ResourceMapper;

@Service
public class ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    
    @Transactional(readOnly = true)
    public List<AuthResource> getResources() {
        List<AuthResource> authResourceList = resourceMapper.getResources();

        return authResourceList;
    }    
}
