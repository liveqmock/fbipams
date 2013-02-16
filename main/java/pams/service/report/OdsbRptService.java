package pams.service.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.repository.dao.OdsbLargedepFlowMapper;
import pams.repository.dao.OdsbStTconfirmTradMapper;
import pams.repository.dao.largedepflow.LargeDepFlowMapper;
import pams.repository.dao.report.OdsbRptMapper;
import pams.repository.model.OdsbStTconfirmTrad;
import pams.repository.model.report.BasePagedQryParamBean;

import java.util.List;

/**
 * User: zhanrui
 * Date: 13-1-24
 * Time: ÏÂÎç2:29
 */
@Service
public class OdsbRptService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OdsbLargedepFlowMapper odsbLargedepFlowMapper;
    @Autowired
    private LargeDepFlowMapper largeDepFlowMapper;
    @Autowired
    private OdsbStTconfirmTradMapper odsbStTconfirmTradMapper;
    @Autowired
    private OdsbRptMapper odsbRptMapper;

    public List<OdsbStTconfirmTrad> selectPagedRecords_RptA07V1(BasePagedQryParamBean paramBean){
        return odsbRptMapper.selectRptA07V1Records(paramBean);
    }


    //===========================================

    public OdsbLargedepFlowMapper getOdsbLargedepFlowMapper() {
        return odsbLargedepFlowMapper;
    }

    public void setOdsbLargedepFlowMapper(OdsbLargedepFlowMapper odsbLargedepFlowMapper) {
        this.odsbLargedepFlowMapper = odsbLargedepFlowMapper;
    }

    public LargeDepFlowMapper getLargeDepFlowMapper() {
        return largeDepFlowMapper;
    }

    public void setLargeDepFlowMapper(LargeDepFlowMapper largeDepFlowMapper) {
        this.largeDepFlowMapper = largeDepFlowMapper;
    }

    public OdsbStTconfirmTradMapper getOdsbStTconfirmTradMapper() {
        return odsbStTconfirmTradMapper;
    }

    public void setOdsbStTconfirmTradMapper(OdsbStTconfirmTradMapper odsbStTconfirmTradMapper) {
        this.odsbStTconfirmTradMapper = odsbStTconfirmTradMapper;
    }

    public OdsbRptMapper getOdsbRptMapper() {
        return odsbRptMapper;
    }

    public void setOdsbRptMapper(OdsbRptMapper odsbRptMapper) {
        this.odsbRptMapper = odsbRptMapper;
    }
}
