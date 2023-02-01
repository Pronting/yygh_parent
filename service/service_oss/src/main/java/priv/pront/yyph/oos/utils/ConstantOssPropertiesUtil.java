package priv.pront.yyph.oos.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 获取aliyun的相关信息
 * @Author: pront
 * @Time:2023-02-01 17:24
 */
@Component
public class ConstantOssPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.regionId}")
    private String regionId;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.secret}")
    private String secret;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    public static String REGION_ID;
    public static String ACCESS_KEY_ID;
    public static String SECRET;
    public static String END_POINT;
    public static String BUCKET;

    @Override
    public void afterPropertiesSet() throws Exception {
        REGION_ID = regionId;
        ACCESS_KEY_ID = accessKeyId;
        SECRET = secret;
        END_POINT = endpoint;
        BUCKET = bucket;
    }
}
