package pams.batch.saleeventcheck.server.checkpoint.saving;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pams.batch.saleeventcheck.server.checkpoint.SepCheckPoint;
import pams.batch.saleeventcheck.server.checkpoint.SepCheckPointRequest;
import pams.batch.saleeventcheck.server.checkpoint.SepCheckPointResponse;
import pams.checkpoint.CheckPointException;
import pams.checkpoint.CheckPointRequest;
import pams.checkpoint.CheckPointResponse;

import java.io.IOException;

/**
 * 定期存款发生额等检核
 * User: zhanrui
 * Date: 13-8-3
 * Time: 上午7:47
 */
@Component("saving.CurrentDepCheck")
public class CurrentDepCheck extends SepCheckPoint {
    private static final Logger logger = LoggerFactory.getLogger(CurrentDepCheck.class);
    private static final String PROGNAME = "saving.CurrentDepCheck";

    @Override
    protected void service(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {
        //
        resp.setRtnCode("0000");
        System.out.println("CurrentDepCheck");
    }

    @Override
    public void service(CheckPointRequest req, CheckPointResponse resp) throws CheckPointException, IOException {
        service((SepCheckPointRequest)req, (SepCheckPointResponse)resp);
    }
}
