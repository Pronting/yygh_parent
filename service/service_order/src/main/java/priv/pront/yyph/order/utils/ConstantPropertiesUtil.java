package priv.pront.yyph.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 19:57
 */
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.partner}")
    private String partner;

    @Value("${weixin.partnerkey}")
    private String partnerkey;

    public static String APPID;
    public static String PARTNER;
    public static String PARTNERKEY;
    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appid;
        PARTNER = partner;
        PARTNERKEY = partnerkey;
    }

}
