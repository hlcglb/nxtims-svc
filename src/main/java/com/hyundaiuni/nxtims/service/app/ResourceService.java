package com.hyundaiuni.nxtims.service.app;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.app.ResourceMapper;

@Service
public class ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Transactional(readOnly = true)
    public List<AuthResource> getResourceAuthAll() {
        return resourceMapper.getResourceAuthAll();
    }

    @Transactional(readOnly = true)
    public List<Resource> getResourceListByParam(Map<String, Object> parameter, int offset, int limit) {
        Assert.notNull(parameter, "parameter must not be null");
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        parameter.put("offset", offset);
        parameter.put("limit", limit);

        return resourceMapper.getResourceListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public Resource getResource(String resourceId) {
        Assert.notNull(resourceId, "resourceId must not be null");

        return resourceMapper.getResourceById(resourceId);
    }

    public Resource insertResource(Resource resource) {
        Assert.notNull(resource, "resource must not be null");

        checkMandatoryResource(resource);

        resource.setResourceId(resourceMapper.getResourceSequence());

        resourceMapper.insertResource(resource);

        return getResource(resource.getResourceId());
    }

    public void updateResource(Resource resource) {
        Assert.notNull(resource, "resource must not be null");

        if(StringUtils.isEmpty(resource.getResourceId())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_ID must not be null.",
                new String[] {"RESOURCE_ID"});
        }

        if(resourceMapper.getResourceById(resource.getResourceId()) == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", resource.getResourceId() + " not found.", null);
        }

        checkMandatoryResource(resource);

        resourceMapper.updateResource(resource);
    }

    public void deleteResource(String resourceId) {
        Assert.notNull(resourceId, "resourceId must not be null");

        if(resourceMapper.getResourceById(resourceId) == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", resourceId + " not found.", null);
        }

        resourceMapper.deleteResourceById(resourceId);
    }

    public void saveResources(List<Resource> resourceList) {
        if(CollectionUtils.isNotEmpty(resourceList)) {
            for(Resource resource : resourceList) {
                if("C".equals(resource.getTransactionType())) {
                    insertResource(resource);
                }
                else if("U".equals(resource.getTransactionType())) {
                    updateResource(resource);
                }
                else if("D".equals(resource.getTransactionType())) {
                    deleteResource(resource.getResourceId());
                }
                else {
                    throw new ServiceException("MSG.TRANSACTION_TYPE_NOT_SUPPORTED",
                        resource.getTransactionType() + " does not supported.",
                        new String[] {resource.getTransactionType()});
                }
            }
        }
    }

    private void checkMandatoryResource(Resource resource) {
        if(StringUtils.isEmpty(resource.getResourceNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_NM must not be null.",
                new String[] {"RESOURCE_NM"});
        }

        if(StringUtils.isEmpty(resource.getResourceType())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_TYPE must not be null.",
                new String[] {"RESOURCE_TYPE"});
        }

        if("02".equals(resource.getResourceType()) && StringUtils.isEmpty(resource.getResourceUrl())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_URL must not be null.",
                new String[] {"RESOURCE_URL"});
        }

        if(StringUtils.isEmpty(resource.getUseYn())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "USE_YN must not be null.", new String[] {"USE_YN"});
        }

        if(!("Y".equals(resource.getUseYn()) || "N".equals(resource.getUseYn()))) {
            throw new ServiceException("MSG.INVALID_DATA", "USE_YN is invalid.", new String[] {"USE_YN"});
        }

        if(StringUtils.isNotEmpty(resource.getLinkResourceId())) {
            if(resourceMapper.getResourceById(resource.getLinkResourceId()) == null) {
                throw new ServiceException("MSG.INVALID_DATA", "LINK_RESOURCE_ID is invalid.",
                    new String[] {"LINK_RESOURCE_ID"});
            }
        }
    }
}
