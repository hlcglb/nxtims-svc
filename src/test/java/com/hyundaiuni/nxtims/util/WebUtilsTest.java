package com.hyundaiuni.nxtims.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class WebUtilsTest {
    private static final Log log = LogFactory.getLog(WebUtilsTest.class);
    
    @Test
    public void testMapToRequestParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("msgGrpCd", "MSG");
            parameter.put("msgCd", "NO_DATA_FOUND");

            String requestString = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");

            assertEquals("msgCd%3DNO_DATA_FOUND%2CmsgGrpCd%3DMSG", requestString);
            
            parameter = new HashMap<>();
            parameter.put("msgCd", "NO_DATA_FOUND^TRANSACTION_TYPE_NOT_SUPPORTED");
            
            requestString = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");
            
            assertEquals("msgCd%3DNO_DATA_FOUND%5ETRANSACTION_TYPE_NOT_SUPPORTED", requestString);

            log.info(requestString);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }
    
    @Test
    public void testRequestParamtoMap() {
        Exception ex = null;

        try {
            String query = "msgCd%3DNO_DATA_FOUND%2CmsgGrpCd%3DMSG";
            
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("msgGrpCd", "MSG");
            parameter.put("msgCd", "NO_DATA_FOUND");

            Map<String, Object> result = WebUtils.requestParamtoMap(query, ',', '=', "UTF-8");

            assertEquals(parameter, result);
            
            query = "msgCd%3DNO_DATA_FOUND%5ETRANSACTION_TYPE_NOT_SUPPORTED";
            
            parameter = new HashMap<>();
            parameter.put("msgCd", "NO_DATA_FOUND^TRANSACTION_TYPE_NOT_SUPPORTED");
            
            result = WebUtils.requestParamtoMap(query, ',', '=', "UTF-8");
            
            log.info(result.toString());
            
            assertEquals(parameter, result);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }    
}
