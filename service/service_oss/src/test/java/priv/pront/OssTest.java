package priv.pront;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;

/**
 * @Description: 测试oss创建bucket
 * @Author: pront
 * @Time:2023-02-01 16:58
 */

public class OssTest {

    public static void main(String[] args) throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tNMzvmLjLaUAfV2h22x";
        String accessKeySecret = "UTY3JVumT7ajGVQxsmWTxawQC0yCtl";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "yyph-teoss";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);
        ossClient.shutdown();
    }
}
