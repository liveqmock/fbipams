package pams.datachkserver.chkapps.saleevent.checkpoint.saving;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pams.datachkserver.api.checkpoint.CheckPointException;
import pams.datachkserver.api.checkpoint.sep.SepCheckPoint;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointRequest;
import pams.datachkserver.api.checkpoint.sep.SepCheckPointResponse;

import java.io.IOException;

/**
 * 定期存款发生额等检核
 * User: zhanrui
 * Date: 13-8-3
 * Time: 上午7:47
 */
@Component("saving.FixedDepCheck")
public class FixedDepCheck extends SepCheckPoint {
    private static final Logger logger = LoggerFactory.getLogger(FixedDepCheck.class);
    private static final String PROGNAME = "saving.FixedDepCheck";

    @Override
    public void doCheck(SepCheckPointRequest req, SepCheckPointResponse resp) throws CheckPointException, IOException {
        //
        System.out.println("FixedDepCheck");
    }
}
