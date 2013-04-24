package pams.service.effectcust;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pams.repository.dao.SvEclCustinfoMapper;
import pams.repository.dao.effectcust.EffectCustlistMapper;
import pams.repository.model.SvEclCustinfo;
import pams.service.common.dataimport.Format;
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
 * 有效客户拓展提升目标清单.
 * User: zhanrui
 * Date: 13-4-23
 * Time: 下午3:50
 */
@Service
public class EclImportService {
    private static final Logger logger = LoggerFactory.getLogger(EclImportService.class);

    @Autowired
    private SvEclCustinfoMapper custinfoMapper;
    @Autowired
    private EffectCustlistMapper custlistMapper;

    private static int transaction_commit_num = PropertyManager.getIntProperty("import_data_transaction_num");

    public void importDataFromTxt(String rptDate, String file, String rptType, List<String> msgList) {
        String filename = getTxtPath(rptDate) + file;

        //删除旧数据
        custlistMapper.deleteRecords(rptType);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        //Format format = (Format)applicationContext.getBean("rpt"+rptType+"Format");
        //zr 所有报表的格式相同，所以选用统一的格式定义 1001
        Format format = (Format) applicationContext.getBean("rpt" + "1001" + "Format");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            List<SvEclCustinfo> beans = new ArrayList<>();
            int count = 0;
            while ((line = br.readLine()) != null) {
                SvEclCustinfo bean = (SvEclCustinfo) format.parse(line);
                if (bean == null) {
                    logger.debug("导入错误：字段个数不符合定义。 原始数据：" + line);
                    msgList.add("报表数据文件：" + file + " 导入过程中出现错误：字段个数不符合定义。 原始数据：" + line);
                    continue;
                }
                fillBeanBaseInfo(bean, rptDate, rptType);
                beans.add(bean);
                count++;
                if (count == transaction_commit_num) {
                    long start = System.currentTimeMillis();
                    //logger.info("===begin import " + transaction_commit_num + " lines....");
                    custlistMapper.insertBatch(beans);
                    long end = System.currentTimeMillis();
                    logger.info("===import " + transaction_commit_num + " lines.... Time:" + (end-start) + " RPT:" + rptType);
                    beans = new ArrayList<>();
                    count = 0;
                }
            }
            custlistMapper.insertBatch(beans);
            msgList.add("导入成功：报表数据文件：" + file);
        } catch (FileNotFoundException e) {
            logger.info("导入失败：报表数据文件：" + file + " 不存在。", e);
            msgList.add("导入失败：报表数据文件：" + file + " 不存在。");
        } catch (Exception e) {
            logger.info("导入失败：报表数据文件：" + file + " 读取失败。", e);
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

    private void fillBeanBaseInfo(SvEclCustinfo bean, String startdate, String rptType) {
        bean.setGuid(UUID.randomUUID().toString());
        bean.setRptDate(startdate);
        bean.setRptType(rptType);
        bean.setImpDate(new Date());
        bean.setOperId("9999"); //TODO
        bean.setOperDate(new Date());
        bean.setDeleteFlag("0");
    }

    private String getTxtPath(String rptdate) {
        return PropertyManager.getProperty("cims") + PropertyManager.getProperty("import_effectcust_data_dir");
    }
}
