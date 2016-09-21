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
        Assert.notNull(parameter, "parameter must not be null");
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

        codeMaster.setCodeDetaileList(codeMapper.getCodeDetailListByCodeMstCd(codeMstCd));

        return codeMaster;
    }

    @Transactional
    public CodeMaster insertCode(CodeMaster codeMaster) {
        Assert.notNull(codeMaster, "codeMaster must not be null");

        checkMandatoryCodeMaster(codeMaster);

        if(codeMapper.getCodeMasterByCodeMstCd(codeMaster.getCodeMstCd()) != null) {
            throw new ServiceException("MSG.DUPLICATED", codeMaster.toString() + " was duplicated.", null);
        }

        codeMapper.insertCodeMaster(codeMaster);

        List<CodeDetail> codeDetailList = codeMaster.getCodeDetaileList();

        if(CollectionUtils.isNotEmpty(codeDetailList)) {
            for(CodeDetail codeDetail : codeDetailList) {
                codeDetail.setCodeMstCd(codeMaster.getCodeMstCd());

                checkMandatoryCodeDetail(codeDetail);

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("codeMstCd", codeDetail.getCodeMstCd());
                parameter.put("codeDtlCd", codeDetail.getCodeDtlCd());

                if(codeMapper.getCodeDetailByPk(parameter) != null) {
                    throw new ServiceException("MSG.DUPLICATED", parameter.toString() + " was duplicated.", null);
                }

                codeMapper.insertCodeDetail(codeDetail);
            }
        }

        return getCode(codeMaster.getCodeMstCd());
    }

    @Transactional
    public void updateCode(CodeMaster codeMaster) {
        Assert.notNull(codeMaster, "codeMaster must not be null");

        checkMandatoryCodeMaster(codeMaster);

        if(codeMapper.getCodeMasterByCodeMstCd(codeMaster.getCodeMstCd()) == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", codeMaster.getCodeMstCd() + " not found.", null);
        }

        codeMapper.updateCodeMaster(codeMaster);

        List<CodeDetail> codeDetailList = codeMaster.getCodeDetaileList();

        if(CollectionUtils.isNotEmpty(codeDetailList)) {
            for(CodeDetail codeDetail : codeDetailList) {
                String codeDetailTransactionType = codeDetail.getTransactionType();

                codeDetail.setCodeMstCd(codeMaster.getCodeMstCd());

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("codeMstCd", codeDetail.getCodeMstCd());
                parameter.put("codeDtlCd", codeDetail.getCodeDtlCd());

                if("C".equals(codeDetailTransactionType)) {
                    checkMandatoryCodeDetail(codeDetail);

                    if(codeMapper.getCodeDetailByPk(parameter) != null) {
                        throw new ServiceException("MSG.DUPLICATED", parameter.toString() + " was duplicated.", null);
                    }

                    codeMapper.insertCodeDetail(codeDetail);
                }
                else if("U".equals(codeDetailTransactionType)) {
                    checkMandatoryCodeDetail(codeDetail);

                    if(codeMapper.getCodeDetailByPk(parameter) == null) {
                        throw new ServiceException("MSG.NO_DATA_FOUND", parameter.toString() + " not found.", null);
                    }

                    codeMapper.updateCodeDetail(codeDetail);
                }
                else if("D".equals(codeDetailTransactionType)) {
                    if(StringUtils.isEmpty(codeDetail.getCodeDtlCd())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_CD must not be null.",
                            new String[] {"CODE_DTL_CD"});
                    }

                    if(codeMapper.getCodeDetailByPk(parameter) == null) {
                        throw new ServiceException("MSG.NO_DATA_FOUND", parameter.toString() + " not found.", null);
                    }

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

        if(codeMapper.getCodeMasterByCodeMstCd(codeMstCd) == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", codeMstCd + " not found.", null);
        }

        codeMapper.deleteCodeDetailByCodeMstCd(codeMstCd);
        codeMapper.deleteCodeMasterByCodeMstCd(codeMstCd);
    }

    private void checkMandatoryCodeMaster(CodeMaster codeMaster) {
        if(StringUtils.isEmpty(codeMaster.getCodeMstCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_MST_CD must not be null.",
                new String[] {"CODE_MST_CD"});
        }

        if(StringUtils.isEmpty(codeMaster.getCodeMstNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_MST_NM must not be null.",
                new String[] {"CODE_MST_NM"});
        }
    }

    private void checkMandatoryCodeDetail(CodeDetail codeDetail) {
        if(StringUtils.isEmpty(codeDetail.getCodeDtlCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_CD must not be null.",
                new String[] {"CODE_DTL_CD"});
        }

        if(StringUtils.isEmpty(codeDetail.getCodeDtlNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CODE_DTL_NM must not be null.",
                new String[] {"CODE_DTL_NM"});
        }
    }
}
