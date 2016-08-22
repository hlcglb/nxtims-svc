package com.hyundaiuni.nxtims.service.app;

import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.AuthResource;

public class ResourcesServiceTest {
    @Autowired
    private ResourcesService resourcesService;
    
    public void testGetResourceByType(){
        List<AuthResource> authResourceList = resourcesService.getResourceByType("02");
        
        if(CollectionUtils.isEmpty(authResourceList)){
            fail("");
        }        
    }
}
