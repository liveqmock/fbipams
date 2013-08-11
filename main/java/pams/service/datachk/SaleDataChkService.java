package pams.service.datachk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pams.datachkserver.chkserver.connector.SepConnector;
import pams.repository.dao.SvCmsCustbaseMapper;
import pams.repository.dao.SvSaleDetailChkMapper;
import pams.repository.dao.SvSaleDetailHisMapper;
import pams.repository.dao.SvSaleDetailMapper;
import pams.repository.dao.saledata.SaleDataChkMapper;
import pams.repository.model.SvCmsCustbase;
import pams.repository.model.SvSaleDetail;
import pams.repository.model.SvSaleDetailChk;
import pams.repository.model.SvSaleDetailHis;
import pams.repository.model.saledata.SaleDataChkVO;
import pams.repository.model.saledata.SaleDataQryParamBean;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.security.OperatorManager;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zhanrui
 * Date: 13-3-4
 * Time: ����2:29
 */
@Service
public class SaleDataChkService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SaleDataChkMapper saleDataChkMapper;
    @Autowired
    private SvSaleDetailMapper svSaleDetailMapper;
    @Autowired
    private SvSaleDetailChkMapper svSaleDetailChkMapper;
    @Autowired
    private SvSaleDetailHisMapper svSaleDetailHisMapper;

    @Autowired
    private SvCmsCustbaseMapper svCmsCustbaseMapper;

    @Autowired
    private SepConnector connector;

    @Autowired
    private ToolsService toolsService;
    @Autowired
    private PlatformService platformService;

    public List<SaleDataChkVO> selectPagedRecords_SaleData(SaleDataQryParamBean paramBean) {
        return saleDataChkMapper.selectSaleDataRecords(paramBean);
    }

    public void processSaleDataCheck(SaleDataChkVO vo) {
        SvCmsCustbase cust = svCmsCustbaseMapper.selectByPrimaryKey(vo.getCustguid());
        if (cust == null) {
            handleOneRecordCheckResult(vo.getGuid(), "1111", "ϵͳ���޴˿ͻ���Ϣ��δ���.");
            insertCheckHistory(vo.getGuid(), "DataCheck", "Ӫ������ݼ��");
            return;
        }

        String custno = cust.getCertNo();
        String certtype = cust.getCertType();
        String certno = cust.getCertNo();

        Map<String, Object> req = new HashMap<>();
        Map<String, Object> resp = new HashMap<>();


        req.put("prdid", vo.getPrdid());
        req.put("subprdid", vo.getSubprdid());
        req.put("tellerid", vo.getTellerid());
        req.put("bankid", vo.getDeptid());
        req.put("txndate", vo.getTxndate());
        req.put("txntime", vo.getTxntime());
        req.put("salesnum1", vo.getSalesnum1());
        req.put("salesnum2", vo.getSalesnum2());
        req.put("salesnum3", vo.getSalesnum3());
        req.put("salesamt1", vo.getSalesamt1());
        req.put("salesamt2", vo.getSalesamt2());
        req.put("salesamt3", vo.getSalesamt3());

        req.put("custno", custno);
        req.put("certtype", certtype);
        req.put("certno", certno);

        connector.process(req, resp);

        String rtnCode = (String) resp.get("rtncode");
        String rtnMsg = (String) resp.get("rtnmsg");

        if (rtnCode.startsWith("1")) { //ϵͳ������
            throw new RuntimeException(rtnMsg);
        } else {
            handleOneRecordCheckResult(vo.getGuid(), rtnCode, rtnMsg);
            insertCheckHistory(vo.getGuid(), "DataCheck", "Ӫ������ݼ��");
        }
    }

    //==========================================

    //��һ����˵ķ��ؽ�����д���
    private void handleOneRecordCheckResult(String key, String rtnCode, String rtnMsg) {
        SvSaleDetail svSaleDetail = svSaleDetailMapper.selectByPrimaryKey(key);
        SvSaleDetailChk svSaleDetailChk = svSaleDetailChkMapper.selectByPrimaryKey(key);
        if (svSaleDetailChk == null) {//��˼�¼���в����ڼ�¼ʱ������һ����¼
            svSaleDetailChk = new SvSaleDetailChk();
            svSaleDetailChk.setGuid(key);

            //! ���״̬����
            String checkFlag = "0";
            checkFlag = processRtnCode(rtnCode);
            svSaleDetailChk.setCheckflag(checkFlag);

            int len = rtnMsg.getBytes().length;
            if (len >= 200) {
                rtnMsg = rtnMsg.substring(1, 100);
            }
            svSaleDetailChk.setChecklog(rtnMsg);

            String operatorId = getOperId();
            svSaleDetailChk.setCheckoperid(operatorId);
            svSaleDetailChk.setChecktimes(1L);
            svSaleDetailChk.setCheckdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            svSaleDetailChk.setChecktime(new SimpleDateFormat("HH:mm:ss").format(new Date()));

            svSaleDetailChk.setCheckorgrecver(svSaleDetail.getRecversion());

            svSaleDetailChk.setArchiveflag("0");
            svSaleDetailChk.setRecversion(1L);

            svSaleDetailChkMapper.insert(svSaleDetailChk);
        } else { //�Ѵ��ڼ�¼ʱ�����¼�¼
            //! ���״̬����
            String checkFlag = "0";
            checkFlag = processRtnCode(rtnCode);
            svSaleDetailChk.setCheckflag(checkFlag);

            int len = rtnMsg.getBytes().length;
            if (len >= 200) {
                rtnMsg = rtnMsg.substring(1, 100);
            }
            svSaleDetailChk.setChecklog(rtnMsg);

            String operatorId = getOperId();
            svSaleDetailChk.setCheckoperid(operatorId);
            svSaleDetailChk.setChecktimes(svSaleDetailChk.getChecktimes() + 1L);
            svSaleDetailChk.setCheckdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            svSaleDetailChk.setChecktime(new SimpleDateFormat("HH:mm:ss").format(new Date()));

            svSaleDetailChk.setCheckorgrecver(svSaleDetail.getRecversion());

            svSaleDetailChk.setArchiveflag("0");
            svSaleDetailChk.setRecversion(svSaleDetailChk.getRecversion() + 1L);

            svSaleDetailChkMapper.updateByPrimaryKeySelective(svSaleDetailChk);
        }
    }

    //�������״̬����
    private String processRtnCode(String rtnCode) {
        String checkFlag;
        if ("0000".equals(rtnCode)) { //���ͨ��
            checkFlag = "2";
        } else if (rtnCode.startsWith("1")) { // ������ �ԡ�1����ͷ�ģ�ϵͳ�������δ���м��
            checkFlag = "0";
        }else{ //�ѽ��м�ˣ�δͨ��
            checkFlag = "1";
        }
        return checkFlag;
    }

    private void insertCheckHistory(String key, String operCode, String operDesc){
        SvSaleDetailHis svSaleDetailHis = new SvSaleDetailHis();
        svSaleDetailHis.setSaledataguid(key);
        svSaleDetailHis.setOpercode(operCode);
        svSaleDetailHis.setOperdesc(operDesc);
        svSaleDetailHis.setOperid(getOperId());
        svSaleDetailHis.setOperdate(new Date());

        svSaleDetailHisMapper.insert(svSaleDetailHis);
    }

    private String getOperId() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("�û�δ��¼��");
        }

        return om.getOperatorId();
    }

    //===========================================

    public SaleDataChkMapper getSaleDataChkMapper() {
        return saleDataChkMapper;
    }

    public void setSaleDataChkMapper(SaleDataChkMapper saleDataChkMapper) {
        this.saleDataChkMapper = saleDataChkMapper;
    }

}
