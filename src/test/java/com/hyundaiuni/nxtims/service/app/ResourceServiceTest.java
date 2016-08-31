package com.hyundaiuni.nxtims.service.app;

import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.AuthResource;

public class ResourceServiceTest {
    @Autowired
    private ResourceService resourceService;
    
    public void testGetResourceByType(){
        List<AuthResource> authResourceList = resourceService.getResources();
        
        if(CollectionUtils.isEmpty(authResourceList)){
            fail("");
        }        
    }
}
