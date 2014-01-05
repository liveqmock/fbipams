package pams.service.custlist.importdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pams.repository.dao.ClsRptdataMapper;
import pams.repository.dao.custlist.CustlistMapper;
import pams.repository.model.ClsRptdata;
import pams.service.common.dataimport.Format;
import pub.platform.advance.utils.PropertyManager;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * �¿ͻ������Ʊ������ݵ���
 * User: zhanrui
 * Date: 20140105
 */
@Service
public class ClsDataImportService {
    private static final Logger logger = LoggerFactory.getLogger(ClsDataImportService.class);

    @Autowired
    private ClsRptdataMapper custinfoMapper;
    @Autowired
    private CustlistMapper custlistMapper;

    private  static int transaction_commit_num = PropertyManager.getIntProperty("import_data_transaction_num");

    public void importDataFromTxt(String rptDate, String file, String rptType, List<String> msgList) {
        String filename = getTxtPath(rptDate) + file;

        //ɾ��������
        custlistMapper.deleteRecords(rptDate, rptType);

        ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        Format format = (Format)applicationContext.getBean("rpt"+rptType+"Format");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            List<ClsRptdata> beans = new ArrayList<>();
            int count = 0;
            while ((line = br.readLine()) != null) {
                ClsRptdata bean = (ClsRptdata) format.parse(line);
                fillBeanBaseInfo(bean, rptDate, rptType);
                beans.add(bean);
                count++;
                if (count % transaction_commit_num == 0) {
                    logger.info("============begin import rpt" + rptType + ", commit num= " +transaction_commit_num + ", current line=" + count);
                    custlistMapper.insertBatch(beans);
                    logger.info("============end import " +transaction_commit_num + " lines....");
                    beans = new ArrayList<>();
                    //count = 0;
                }

                //TODO
                if (count > 10) {
                    break;
                }


            }
            if (!beans.isEmpty()) {
                custlistMapper.insertBatch(beans);
            }
            msgList.add("����ɹ������������ļ���" + file);
        } catch (FileNotFoundException e) {
            logger.info("����ʧ�ܣ����������ļ���" + file + " �����ڡ�", e);
            msgList.add("����ʧ�ܣ����������ļ���" + file + " �����ڡ�");
        } catch (Exception e) {
            logger.info("����ʧ�ܣ����������ļ���" + file + " ��ȡʧ�ܡ�", e);
            msgList.add("����ʧ�ܣ����������ļ���" + file + " ��ȡʧ�ܡ�" + e.getMessage());
            //throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("ϵͳ����", e);
                }
            }
        }
    }

    private void fillBeanBaseInfo(ClsRptdata bean, String startdate, String rptType){
//        bean.setGuid(UUID.randomUUID().toString());
        bean.setRptDate(startdate);
        bean.setRptType(rptType);
//        bean.setImpDate(new Date());
//        bean.setOperId("9999"); //TODO
//        bean.setOperDate(new Date());
//        bean.setDeleteFlag("0");
    }

    private String getTxtPath(String rptdate) {
        return PropertyManager.getProperty("cims") + PropertyManager.getProperty("import_custlist_data_dir")+ rptdate + "/ACRM/";
    }
}