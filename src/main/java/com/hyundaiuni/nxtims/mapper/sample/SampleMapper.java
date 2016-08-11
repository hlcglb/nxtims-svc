package com.hyundaiuni.nxtims.mapper.sample;

import java.util.Map;

public interface SampleMapper {
    public Map<String, Object> get(String id);

    public void insert(Map<String, Object> map);
}
