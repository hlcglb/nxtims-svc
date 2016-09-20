package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundaiuni.nxtims.domain.app.CodeDetail;
import com.hyundaiuni.nxtims.domain.app.CodeMaster;
import com.hyundaiuni.nxtims.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeServiceTest {
    private static final Log log = LogFactory.getLog(CodeServiceTest.class);

    @Autowired
    private CodeService codeService;

    @Test
    public void testGetCodeMasterListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();

            codeService.getCodeMasterListByParam(parameter, 0, 10);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetCodeDetailListByCodeMstCd() {
        Exception ex = null;

        try {
            codeService.getCodeDetailListByCodeMstCd("RESOURCE_TYPE");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetCodeMasterByCodeMstCd() {
        Exception ex = null;

        try {
            codeService.getCodeMasterByCodeMstCd("RESOURCE_TYPE");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);

        try {
            codeService.getCodeMasterByCodeMstCd("XXXXXX");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("not found");
        }
    }

    @Test
    public void testInsertCodeMaster() {
        Exception ex = null;

        try {
            CodeMaster codeMaster = new CodeMaster();
            codeMaster.setCodeMstCd("TEST");
            codeMaster.setCodeMstNm("TEST");

            CodeDetail codeDetail1 = new CodeDetail();
            codeDetail1.setCodeMstCd(codeMaster.getCodeMstCd());
            codeDetail1.setCodeDtlCd("01");
            codeDetail1.setCodeDtlNm("01");

            CodeDetail codeDetail2 = new CodeDetail();
            codeDetail2.setCodeMstCd(codeMaster.getCodeMstCd());
            codeDetail2.setCodeDtlCd("02");
            codeDetail2.setCodeDtlNm("02");

            List<CodeDetail> codeDetaileList = new ArrayList<>();
            codeDetaileList.add(codeDetail1);
            codeDetaileList.add(codeDetail2);

            codeMaster.setCodeDetaileList(codeDetaileList);

            CodeMaster tempCodeMaster = codeService.insertCodeMaster(codeMaster);

            codeService.deleteCodeMaster(tempCodeMaster.getCodeMstCd());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testUpdateAuth() {
        Exception ex = null;

        try {
            CodeMaster codeMaster = new CodeMaster();
            codeMaster.setCodeMstCd("TEST");
            codeMaster.setCodeMstNm("TEST");

            CodeDetail codeDetail1 = new CodeDetail();
            codeDetail1.setCodeMstCd(codeMaster.getCodeMstCd());
            codeDetail1.setCodeDtlCd("01");
            codeDetail1.setCodeDtlNm("01");

            CodeDetail codeDetail2 = new CodeDetail();
            codeDetail2.setCodeMstCd(codeMaster.getCodeMstCd());
            codeDetail2.setCodeDtlCd("02");
            codeDetail2.setCodeDtlNm("02");

            List<CodeDetail> codeDetaileList = new ArrayList<>();
            codeDetaileList.add(codeDetail1);
            codeDetaileList.add(codeDetail2);

            codeMaster.setCodeDetaileList(codeDetaileList);

            CodeMaster tempCodeMaster = codeService.insertCodeMaster(codeMaster);

            codeDetaileList = tempCodeMaster.getCodeDetaileList();

            if(CollectionUtils.isNotEmpty(codeDetaileList)) {
                for(CodeDetail codeDetaile : codeDetaileList) {
                    codeDetaile.setTransactionType("D");
                }
            }

            codeService.updateCodeMaster(tempCodeMaster.getCodeMstCd(), tempCodeMaster);
            codeService.deleteCodeMaster(tempCodeMaster.getCodeMstCd());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
