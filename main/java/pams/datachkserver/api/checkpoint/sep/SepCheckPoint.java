package pams.datachkserver.api.checkpoint.sep;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.CheckPointRequest;
import pams.datachkserver.api.checkpoint.CheckPointResponse;
import pams.datachkserver.api.checkpoint.GenericCheckPoint;
import skyline.service.PlatformService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * ��˽Ӫ�����˻�����.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ����1:11
 */
public abstract class SepCheckPoint extends GenericCheckPoint {
    private static final Logger logger = LoggerFactory.getLogger(SepCheckPoint.class);

    @Resource
    protected JdbcTemplate jdbcTemplate;
    @Resource
    protected PlatformService platformService;


    public SepCheckPoint(){
        //
    }

    protected void doCheck(SepCheckPointRequest req, SepCheckPointResponse resp)
            throws CheckPointException, IOException {
        System.out.println("111");
    }

    protected void service(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {
        doCheck(req, resp);
    }

    @Override
    public void service(CheckPointRequest req, CheckPointResponse resp) throws CheckPointException, IOException {
        SepCheckPointRequest request;
        SepCheckPointResponse response;
        try {
            request = (SepCheckPointRequest) req;
            response = (SepCheckPointResponse) resp;
        } catch (ClassCastException e) {
            throw new CheckPointException("non-SEP request or response");
        }
        service(request, response);
    }

    //======================
    /**
     * Dcc�ͻ���ֱ�Ӹ���֤����Ϣ����
     * ���֤�� �ڿͻ��ź��׷�ӡ�0��������׷�ӡ�#0��
     * @return custno
     */
    protected String generateDccCustnoByCertInfo(String certtype, String certno) {
        Map<String, String> certTypeMap = platformService.selectEnuItemValueToExpandValueMap("CERTTYPE");
        String ccbsCertType = certTypeMap.get(certtype);
        if (StringUtils.isEmpty(ccbsCertType)) {
            logger.error("֤�����Ͷ�Ӧʧ�ܣ�" + certtype );
            return null;
        }

        String custno = ccbsCertType + certno;
        if (ccbsCertType.equals("A")) { //���֤
            if (certno.length() < 18) { //һ�����֤
                custno = custno + "#0";
            } else {
                custno = custno + "0";
            }
        } else {
            custno = custno + "#0";
        }
        return custno;
    }
}
