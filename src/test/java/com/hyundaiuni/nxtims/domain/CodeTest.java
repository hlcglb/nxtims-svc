package com.hyundaiuni.nxtims.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.CodeDetail;
import com.hyundaiuni.nxtims.domain.app.CodeMaster;

public class CodeTest {
    @Test
    public void testObjectToJson() {
        Exception ex = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            CodeMaster codeMaster = new CodeMaster();
            codeMaster.setCodeMstCd("RESOURCE_TYPE");
            codeMaster.setCodeMstNm("자원구분");

            CodeDetail codeDetail1 = new CodeDetail();
            codeDetail1.setCodeMstCd("RESOURCE_TYPE");
            codeDetail1.setCodeDtlCd("01");
            codeDetail1.setCodeDtlNm("SCREEN");

            CodeDetail codeDetail2 = new CodeDetail();
            codeDetail2.setCodeMstCd("RESOURCE_TYPE");
            codeDetail2.setCodeDtlCd("02");
            codeDetail2.setCodeDtlNm("API");

            List<CodeDetail> codeDetaileList = new ArrayList<>();
            codeDetaileList.add(codeDetail1);
            codeDetaileList.add(codeDetail2);

            codeMaster.setCodeDetaileList(codeDetaileList);

            mapper.writeValueAsString(codeMaster);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testJsonToObject() {
        Exception ex = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            String json = "{\"CODE_MST_CD\":\"RESOURCE_TYPE\",\"CODE_MST_NM\":\"자원구분\",\"USER_ID\":null,\"CODE_DETAIL_LIST\":[{\"CODE_MST_CD\":\"RESOURCE_TYPE\",\"CODE_DTL_CD\":\"01\",\"CODE_DTL_NM\":\"SCREEN\",\"MSG_GRP_CD\":null,\"MSG_CD\":null,\"SORT_SEQ\":0,\"USE_YN\":null,\"REF_CD1\":null,\"REF_NM1\":null,\"REF_CD2\":null,\"REF_NM2\":null,\"REF_CD3\":null,\"REF_NM3\":null,\"REF_CD4\":null,\"REF_NM4\":null,\"USER_ID\":null,\"TRANSACTION_TYPE\":null},{\"CODE_MST_CD\":\"RESOURCE_TYPE\",\"CODE_DTL_CD\":\"02\",\"CODE_DTL_NM\":\"API\",\"MSG_GRP_CD\":null,\"MSG_CD\":null,\"SORT_SEQ\":0,\"USE_YN\":null,\"REF_CD1\":null,\"REF_NM1\":null,\"REF_CD2\":null,\"REF_NM2\":null,\"REF_CD3\":null,\"REF_NM3\":null,\"REF_CD4\":null,\"REF_NM4\":null,\"USER_ID\":null,\"TRANSACTION_TYPE\":null}]}";

            mapper.readValue(json, CodeMaster.class);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }
}
