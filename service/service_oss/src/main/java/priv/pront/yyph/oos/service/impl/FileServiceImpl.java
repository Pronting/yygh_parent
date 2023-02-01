package priv.pront.yyph.oos.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import priv.pront.yyph.oos.service.FileService;
import priv.pront.yyph.oos.utils.ConstantOssPropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-01 17:18
 */

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {

        String endpoint = ConstantOssPropertiesUtil.END_POINT;
        String accessKeyId = ConstantOssPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantOssPropertiesUtil.SECRET;
        String bucketName = ConstantOssPropertiesUtil.BUCKET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        try {
            InputStream inputStream = file.getInputStream();
//            生成随机字符，使用uuid，添加到文件名称里面
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = file.getOriginalFilename();
            fileName = uuid + fileName;

//            按照当前的日期，创建文件夹
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            fileName = timeUrl + "/" + fileName;
//            调用方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();
//            返回上传之后文件的路径
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
