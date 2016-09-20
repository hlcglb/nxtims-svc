package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;
import java.util.Map;

import com.hyundaiuni.nxtims.domain.app.CodeDetail;
import com.hyundaiuni.nxtims.domain.app.CodeMaster;

public interface CodeMapper {
    public List<CodeMaster> getCodeMasterListByParam(Map<String, Object> parameter);
    
    public List<CodeDetail> getCodeDetailListByCodeMstCd(String codeMstCd);
    
    public CodeMaster getCodeMasterByCodeMstCd(String codeMstCd);
    
    public void insertCodeMaster(CodeMaster codeMaster);
    
    public void updateCodeMaster(CodeMaster codeMaster);
    
    public void deleteCodeMasterByCodeMstCd(String codeMstCd);
    
    public void deleteCodeDetailByCodeMstCd(String codeMstCd);
    
    public CodeDetail getCodeDetailByPk(Map<String, Object> parameter);
    
    public void insertCodeDetail(CodeDetail codeDetail);
    
    public void updateCodeDetail(CodeDetail codeDetail);
    
    public void deleteCodeDetailByPk(Map<String, Object> parameter);    
}
