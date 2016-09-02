package com.hyundaiuni.nxtims.service.app;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.AuthResource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServiceTest {
    @Autowired
    private ResourceService resourceService;
    
    @Test
    public void testGetResource(){
        List<AuthResource> authResourceList = resourceService.getResources();
        
        if(CollectionUtils.isEmpty(authResourceList)){
            fail("");
        }        
    }
}
