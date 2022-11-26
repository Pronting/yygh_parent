package priv.pront.yygh.hosp.controller.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.common.exception.YyghException;
import priv.pront.yygh.common.helper.HttpRequestHelper;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.common.result.ResultCodeEnum;
import priv.pront.yygh.common.util.MD5;
import priv.pront.yygh.hosp.service.DepartmentService;
import priv.pront.yygh.hosp.service.HospitalService;
import priv.pront.yygh.hosp.service.HospitalSetService;
import priv.pront.yygh.model.hosp.Department;
import priv.pront.yygh.model.hosp.Hospital;
import priv.pront.yygh.vo.hosp.DepartmentQueryVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: 对外的若干方法
 * @Author: pront
 * @Time:2022-11-23 09:34
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    HospitalSetService hospitalSetService;

    @Autowired
    DepartmentService departmentService;


    @ApiOperation("上传医院接口")
    @PostMapping("saveHospital")
    public Result saveHospital(HttpServletRequest request) {
//        获取到传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
//        获取医院系统中传递过来的签名,签名进行了md5加密,获取的是加密后的数据
        String hospitalSign = (String) paramMap.get("sign");
//        根据传递过来的医院编号查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
//        将数据库查询出来的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
//        判断签名是否一致
        if (!hospitalSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
//        传输过程中“+” 转换为了" "，转换回来
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);
//        调用service方法
        hospitalService.save(paramMap);
        return Result.ok();
    }


    @ApiOperation("查询医院接口")
    @PostMapping("hospital/show")
    public Result getHospitalService(HttpServletRequest request) {
        //        获取到传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
//        获取医院编号
        //        获取医院系统中传递过来的签名,签名进行了md5加密,获取的是加密后的数据
        String hospitalSign = (String) paramMap.get("sign");
//        根据传递过来的医院编号查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
//        将数据库查询出来的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
//        判断签名是否一致
        if (!hospitalSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
//        调用方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }


    @ApiOperation("上传科室接口")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //        获取到传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hospitalSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMd5 = MD5.encrypt(signKey);
        if (!hospitalSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
//        调用service方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation("查询科室接口")
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
//        获取到传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String) paramMap.get("hoscode");
//        默认值
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));
//        TODO 签名校验

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
//      调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }


    @ApiOperation("删除科室接口")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {

//        获取到传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
//        医院编号和科室编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");
//        TODO 签名校验
        departmentService.remove(hoscode, depcode);
        return Result.ok();

    }
}
