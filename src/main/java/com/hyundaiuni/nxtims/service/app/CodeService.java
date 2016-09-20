package com.hyundaiuni.nxtims.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hyundaiuni.nxtims.domain.app.CodeDetail;
import com.hyundaiuni.nxtims.domain.app.CodeMaster;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.app.CodeMapper;

@Service
public class CodeService {
    @Autowired
    private CodeMapper codeMapper;
    
    @Transactional(readOnly = true)
    public List<CodeDetail> getCodeDetailAll() {
        return codeMapper.getCodeDetailAll();
    }    

    @Transactional(readOnly = true)
    public List<CodeMaster> getCodeMasterListByParam(Map<String, Object> parameter, int offset, int limit) {
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        parameter.put("offset", offset);
        parameter.put("limit", limit);

        return codeMapper.getCodeMasterListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public List<CodeDetail> getCodeDetailListByCodeMstCd(String codeMstCd) {
        Assert.notNull(codeMstCd, "codeMstCd must not be null");

        return codeMapper.getCodeDetailListByCodeMstCd(codeMstCd);
    }

    @Transactional(readOnly = true)
    public CodeMaster getCode(String codeMstCd) {
        Assert.notNull(codeMstCd, "codeMstCd must not be null");

        CodeMaster codeMaster = codeMapper.getCodeMasterByCodeMstCd(codeMstCd);

        if(codeMaster == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", codeMstCd + " not found.", null);
        }

        List<CodeDetail> codeDetailList = codeMapper.getCodeDetailListByCodeMstCd(codeMstCd);

        codeMaster.setCodeDetaileList(codeDetailList);

        return codeMaster;
    }

    @Transactional
    public CodeMaster insertCode(CodeMaster codeMaster) {
        Assert.notNull(codeMaster, "codeMaster must not be null");

        if(StringUtils.isEmpty(codeMaster.getCodeMstCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_MST_CD must not be null.",
                new String[] {"CODE_MST_CD"});
        }

        if(StringUtils.isEmpty(codeMaster.getCodeMstNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_MST_NM must not be null.",
                new String[] {"CODE_MST_NM"});
        }

        CodeMaster tempCodeMaster = codeMapper.getCodeMasterByCodeMstCd(codeMaster.getCodeMstCd());

        if(tempCodeMaster != null) {
            throw new ServiceException("MSG.DUPLICATED", tempCodeMaster.toString() + " was duplicated.", null);
        }

        codeMapper.insertCodeMaster(codeMaster);

        List<CodeDetail> codeDetailList = codeMaster.getCodeDetaileList();

        if(CollectionUtils.isNotEmpty(codeDetailList)) {
            for(CodeDetail codeDetail : codeDetailList) {
                codeDetail.setCodeMstCd(codeMaster.getCodeMstCd());

                if(StringUtils.isEmpty(codeDetail.getCodeDtlCd())) {
                    throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_CD must not be null.",
                        new String[] {"CODE_DTL_CD"});
                }

                if(StringUtils.isEmpty(codeDetail.getCodeDtlNm())) {
                    throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_NM must not be null.",
                        new String[] {"CODE_DTL_NM"});
                }

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("codeMstCd", codeDetail.getCodeMstCd());
                parameter.put("codeDtlCd", codeDetail.getCodeDtlCd());

                CodeDetail tempCodeDetail = codeMapper.getCodeDetailByPk(parameter);

                if(tempCodeDetail != null) {
                    throw new ServiceException("MSG.DUPLICATED", tempCodeDetail.toString() + " was duplicated.", null);
                }

                codeMapper.insertCodeDetail(codeDetail);
            }
        }

        return getCode(codeMaster.getCodeMstCd());
    }

    @Transactional
    public void updateCode(String codeMstCd, CodeMaster codeMaster) {
        Assert.notNull(codeMstCd, "codeMstCd must not be null");
        Assert.notNull(codeMaster, "codeMaster must not be null");

        CodeMaster tempCodeMaster = codeMapper.getCodeMasterByCodeMstCd(codeMstCd);

        if(tempCodeMaster == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", codeMstCd + " not found.", null);
        }

        if(StringUtils.isEmpty(codeMaster.getCodeMstNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_MST_NM must not be null.",
                new String[] {"CODE_MST_NM"});
        }

        codeMapper.updateCodeMaster(codeMaster);

        List<CodeDetail> codeDetailList = codeMaster.getCodeDetaileList();

        if(CollectionUtils.isNotEmpty(codeDetailList)) {
            for(CodeDetail codeDetail : codeDetailList) {
                String codeDetailTransactionType = codeDetail.getTransactionType();

                codeDetail.setCodeMstCd(codeMaster.getCodeMstCd());

                if("C".equals(codeDetailTransactionType) || "U".equals(codeDetailTransactionType)) {
                    if(StringUtils.isEmpty(codeDetail.getCodeDtlCd())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_CD must not be null.",
                            new String[] {"CODE_DTL_CD"});
                    }

                    if(StringUtils.isEmpty(codeDetail.getCodeDtlNm())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_NM must not be null.",
                            new String[] {"CODE_DTL_NM"});
                    }
                }
                else if("D".equals(codeDetailTransactionType)) {
                    if(StringUtils.isEmpty(codeDetail.getCodeDtlCd())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_CD must not be null.",
                            new String[] {"CODE_DTL_CD"});
                    }
                }

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("codeMstCd", codeDetail.getCodeMstCd());
                parameter.put("codeDtlCd", codeDetail.getCodeDtlCd());

                if("C".equals(codeDetailTransactionType)) {
                    CodeDetail tempCodeDetail = codeMapper.getCodeDetailByPk(parameter);

                    if(tempCodeDetail != null) {
                        throw new ServiceException("MSG.DUPLICATED", tempCodeDetail.toString() + " was duplicated.",
                            null);
                    }
                }
                else if("U".equals(codeDetailTransactionType) || "D".equals(codeDetailTransactionType)) {
                    CodeDetail tempCodeDetail = codeMapper.getCodeDetailByPk(parameter);

                    if(tempCodeDetail == null) {
                        throw new ServiceException("MSG.NO_DATA_FOUND", parameter.toString() + " not found.", null);
                    }
                }

                if("C".equals(codeDetailTransactionType)) {
                    codeMapper.insertCodeDetail(codeDetail);
                }
                else if("U".equals(codeDetailTransactionType)) {
                    codeMapper.updateCodeDetail(codeDetail);
                }
                else if("D".equals(codeDetailTransactionType)) {
                    codeMapper.deleteCodeDetailByPk(parameter);
                }
                else {
                    throw new ServiceException("MSG.TRANSACTION_TYPE_NOT_SUPPORTED",
                        codeDetailTransactionType + " was not supported.", null);
                }
            }
        }
    }

    @Transactional
    public void deleteCode(String codeMstCd) {
        Assert.notNull(codeMstCd, "codeMstCd must not be null");

        CodeMaster codeMaster = codeMapper.getCodeMasterByCodeMstCd(codeMstCd);

        if(codeMaster == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", codeMaster + " not found.", null);
        }

        codeMapper.deleteCodeDetailByCodeMstCd(codeMstCd);
        codeMapper.deleteCodeMasterByCodeMstCd(codeMstCd);
    }
}
