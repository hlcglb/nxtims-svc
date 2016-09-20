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
        if(StringUtils.isEmpty(resource.getResourceNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_NM must not be null.",
                new String[] {"RESOURCE_NM"});
        }

        if(StringUtils.isEmpty(resource.getResourceType())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_TYPE must not be null.",
                new String[] {"RESOURCE_TYPE"});
        }

        if("02".equals(resource.getResourceType())) {
            if(StringUtils.isEmpty(resource.getResourceUrl())) {
                throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_URL must not be null.",
                    new String[] {"RESOURCE_URL"});
            }
        }

        if(StringUtils.isEmpty(resource.getUseYn())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "USE_YN must not be null.", new String[] {"USE_YN"});
        }

        if(!(resource.getUseYn().equals("Y") || resource.getUseYn().equals("N"))) {
            throw new ServiceException("MSG.INVALID_DATA", "USE_YN is invalid.", new String[] {"USE_YN"});
        }

        if(StringUtils.isNotEmpty(resource.getLinkResourceId())) {
            Resource linkResource = resourceMapper.getResourceById(resource.getLinkResourceId());

            if(linkResource == null) {
                throw new ServiceException("MSG.INVALID_DATA", "LINK_RESOURCE_ID is invalid.",
                    new String[] {"LINK_RESOURCE_ID"});
            }
        }

        String resourceId = resourceMapper.getResourceSequence();

        resource.setResourceId(resourceId);

        resourceMapper.insertResource(resource);

        return getResource(resourceId);
    }

    public void updateResource(String resourceId, Resource resource) {
        Resource tempResource = resourceMapper.getResourceById(resourceId);

        if(tempResource == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", resourceId + " not found.", null);
        }

        if(StringUtils.isEmpty(resource.getResourceId())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_ID must not be null.",
                new String[] {"RESOURCE_ID"});
        }

        if(StringUtils.isEmpty(resource.getResourceNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_NM must not be null.",
                new String[] {"RESOURCE_NM"});
        }

        if(StringUtils.isEmpty(resource.getResourceType())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_TYPE must not be null.",
                new String[] {"RESOURCE_TYPE"});
        }

        if("02".equals(resource.getResourceType())) {
            if(StringUtils.isEmpty(resource.getResourceUrl())) {
                throw new ServiceException("MSG.MUST_NOT_NULL", "RESOURCE_URL must not be null.",
                    new String[] {"RESOURCE_URL"});
            }
        }

        if(StringUtils.isEmpty(resource.getUseYn())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "USE_YN must not be null.", new String[] {"USE_YN"});
        }

        if(!(resource.getUseYn().equals("Y") || resource.getUseYn().equals("N"))) {
            throw new ServiceException("MSG.INVALID_DATA", "USE_YN is invalid.", new String[] {"USE_YN"});
        }

        if(StringUtils.isNotEmpty(resource.getLinkResourceId())) {
            Resource linkResource = resourceMapper.getResourceById(resource.getLinkResourceId());

            if(linkResource == null) {
                throw new ServiceException("MSG.INVALID_DATA", "LINK_RESOURCE_ID is invalid.",
                    new String[] {"LINK_RESOURCE_ID"});
            }
        }

        resourceMapper.updateResource(resource);
    }

    public void deleteResource(String resourceId) {
        Resource resource = resourceMapper.getResourceById(resourceId);

        if(resource == null) {
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
                    updateResource(resource.getResourceId(), resource);
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
}
