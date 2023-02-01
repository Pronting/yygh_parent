package priv.pront.yyph.oos.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-01 17:18
 */
public interface FileService {

    /**
     * 文件上传
     * @param file 文件对象
     * @return
     */
    String upload(MultipartFile file);
}
