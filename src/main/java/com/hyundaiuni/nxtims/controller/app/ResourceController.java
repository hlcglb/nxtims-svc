package com.hyundaiuni.nxtims.controller.app;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.service.app.ResourceService;
import com.hyundaiuni.nxtims.util.WebUtils;

@RestController
@RequestMapping("/api/v1/app/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getResources() {
        return new ResponseEntity<>(resourceService.getResourceAuthAll(), HttpStatus.OK);
    }

    @RequestMapping(params = "inquiry=getResourceListByParam", method = RequestMethod.GET)
    public ResponseEntity<?> getMessageListByParam(@RequestParam("q") String query, @RequestParam("offset") int offset,
        @RequestParam("limit") int limit) {
        Assert.notNull(query, "query must not be null");
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        try {
            Map<String, Object> parameter = WebUtils.requestParamtoMap(query, ',', '=', "UTF-8");

            return new ResponseEntity<>(resourceService.getResourceListByParam(parameter, offset, limit),
                HttpStatus.OK);
        }
        catch(UnsupportedEncodingException e) {
            throw new ServiceException("ENCODE_NOT_SUPPORTED", e.getMessage(), null, e);
        }
    }

    @RequestMapping(value = "/{resourceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getResourceById(@PathVariable("resourceId") String resourceId) {
        Assert.notNull(resourceId, "resourceId must not be null");

        return new ResponseEntity<>(resourceService.getResourceById(resourceId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insertResource(@RequestBody Resource resource) {
        return new ResponseEntity<>(resourceService.insertResource(resource), HttpStatus.OK);
    }

    @RequestMapping(value = "/{resourceId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateResource(@PathVariable("resourceId") String resourceId,
        @RequestBody Resource resource) {
        resourceService.updateResource(resourceId, resource);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{resourceId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteResource(@PathVariable("resourceId") String resourceId) {
        resourceService.deleteResourceById(resourceId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
