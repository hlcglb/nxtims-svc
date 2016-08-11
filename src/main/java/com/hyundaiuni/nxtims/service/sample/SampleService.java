package com.hyundaiuni.nxtims.service.sample;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundaiuni.nxtims.mapper.sample.SampleMapper;

@Service
public class SampleService {
    @Autowired
    private SampleMapper sampleMapper;

    @Transactional(readOnly = true)
    public Map<String, Object> get(String id) {
        return sampleMapper.get(id);
    }

    @Transactional
    public void insert(Map<String, Object> map) {
        sampleMapper.insert(map);
    }
}
