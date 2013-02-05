package pams.service.custlist.importdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pams.repository.dao.SvClsCustinfoMapper;
import pams.repository.dao.custlist.CustlistMapper;
import pams.repository.model.SvClsCustinfo;
import pub.platform.advance.utils.PropertyManager;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-12-17
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ImportService {
    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

    @Autowired
    private SvClsCustinfoMapper custinfoMapper;
    @Autowired
    private CustlistMapper custlistMapper;

    private  static int transaction_commit_num = PropertyManager.getIntProperty("import_data_transaction_num");

    public void importDataFromTxt(String rptDate, String file, String rptType, List<String> msgList) {
        String filename = getTxtPath(rptDate) + file;

        //删除旧数据
        custlistMapper.deleteRecords(rptDate, rptType);

        ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        Format format = (Format)applicationContext.getBean("rpt"+rptType+"Format");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            List<SvClsCustinfo> beans = new ArrayList<>();
            int count = 0;
            while ((line = br.readLine()) != null) {
                SvClsCustinfo bean = (SvClsCustinfo) format.parse(line);
                fillBeanBaseInfo(bean, rptDate, rptType);
                beans.add(bean);
                count++;
                if (count == transaction_commit_num) {
                    logger.info("============begin import " +transaction_commit_num + " lines....");
                    custlistMapper.insertBatch(beans);
                    logger.info("============end import " +transaction_commit_num + " lines....");
                    beans = new ArrayList<>();
                    count = 0;
                }
            }
            custlistMapper.insertBatch(beans);
            msgList.add("导入成功：报表数据文件：" + file);
        } catch (FileNotFoundException e) {
            msgList.add("导入失败：报表数据文件：" + file + " 不存在。");
        } catch (Exception e) {
            msgList.add("导入失败：报表数据文件：" + file + " 读取失败。" + e.getMessage());
            //throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("系统错误", e);
                }
            }
        }
    }

    private void fillBeanBaseInfo(SvClsCustinfo bean, String startdate, String rptType){
        bean.setGuid(UUID.randomUUID().toString());
        bean.setRptDate(startdate);
        bean.setRptType(rptType);
        bean.setImpDate(new Date());
        bean.setOperId("9999"); //TODO
        bean.setOperDate(new Date());
        bean.setDeleteFlag("0");
    }

    private String getTxtPath(String rptdate) {
        return PropertyManager.getProperty("cims") + PropertyManager.getProperty("import_custlist_data_dir")+ rptdate + "/ACRM/";
    }
}
