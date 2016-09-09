package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServiceTest {
    private static final Log log = LogFactory.getLog(ResourceServiceTest.class);

    @Autowired
    private ResourceService resourceService;

    @Test
    public void testGetResource() {
        List<AuthResource> authResourceList = resourceService.getResourceAuthAll();

        if(CollectionUtils.isEmpty(authResourceList)) {
            fail("");
        }
    }

    @Test
    public void testGetResourceListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("resourceNm", "SAMPLE");

            resourceService.getResourceListByParam(parameter, 0, 10);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetResourceById() {
        Exception ex = null;

        try {
            resourceService.getResourceById("000000");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testInsertResource() {
        Exception ex = null;

        try {
            Resource resource = new Resource();

            resource.setResourceNm("TEST");

            resourceService.insertResource(resource);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("not be null");
        }

        try {
            Resource resource = new Resource();

            resource.setResourceNm("TEST");
            resource.setResourceType("03");
            resource.setResourceUrl("---");
            resource.setUseYn("Y");

            resource = resourceService.insertResource(resource);

            resourceService.deleteResourceById(resource.getResourceId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testUpdateResource() {
        Exception ex = null;

        try {
            Resource resource = new Resource();

            resource.setResourceNm("TEST");
            resource.setResourceType("03");
            resource.setResourceUrl("---");
            resource.setUseYn("Y");

            resource = resourceService.insertResource(resource);

            resource.setResourceNm("TEST1");

            resourceService.updateResource(resource.getResourceId(), resource);

            resourceService.deleteResourceById(resource.getResourceId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
