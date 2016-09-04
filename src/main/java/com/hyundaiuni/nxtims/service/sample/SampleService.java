package com.hyundaiuni.nxtims.service.sample;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.sample.SampleMapper;

@Service
public class SampleService {
    @Autowired
    private SampleMapper sampleMapper;

    @Transactional(readOnly = true)
    public Map<String, Object> get(String id) {
        Map<String, Object> map = sampleMapper.get(id);
        
        if(MapUtils.isEmpty(map)){
            throw new ServiceException("USER_NOT_FOUND", id + " not found.");
        }
        
        return map;
    }

    @Transactional
    public void insert(Map<String, Object> map) {
        sampleMapper.insert(map);
    }
}
