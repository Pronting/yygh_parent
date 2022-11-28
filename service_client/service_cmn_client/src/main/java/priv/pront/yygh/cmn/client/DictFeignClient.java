package priv.pront.yygh.cmn.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description: 字典远程调用Feign接口
 * @Author: pront
 * @Time:2022-11-28 14:57
 */
@FeignClient(value = "service-cmn", path = "/admin/cmn/dict")
@Repository
public interface DictFeignClient {


    @ApiOperation("根据dictcode,value查询")
    @GetMapping("getName/{dicCode}/{value}")
    String getName(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);


    @ApiOperation("根据value查询")
    @GetMapping("getName/{value}")
    String getName(@PathVariable("value") String value);


}
