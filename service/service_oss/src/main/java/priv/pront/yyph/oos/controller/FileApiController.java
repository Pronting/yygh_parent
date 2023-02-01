package priv.pront.yyph.oos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import priv.pront.yygh.common.result.Result;
import priv.pront.yyph.oos.service.FileService;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-01 17:14
 */
@Api(tags = "有关文件相关的接口")
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    @Autowired
    private FileService fileService;

    @ApiOperation("上传文件到阿里云")
    @PostMapping("fileUpload")
    @CrossOrigin
    public Result fileUpload(MultipartFile file) {
//        获取上传文件
        String url = fileService.upload(file);
        return Result.ok(url);
    }

}
