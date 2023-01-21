package priv.pront.yygh.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.common.util.AuthContextHolder;
import priv.pront.yygh.model.user.Patient;
import priv.pront.yygh.user.service.PatientService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-01 22:44
 */
@Api(tags = "就诊人相关接口")
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {


    @Autowired
    private PatientService patientService;

    @ApiOperation("获取就诊人列表")
    @GetMapping("auth/findAll")
    public Result findAll(HttpServletRequest request) {
//        获取到当前登录用户的id
        Long userId = AuthContextHolder.getUserId(request);
        List<Patient> list = patientService.findAllUserId(userId);
        return Result.ok(list);
    }

    @ApiOperation("添加就诊人")
    @PostMapping("auth/save")
    public Result savePatient(@RequestBody Patient patient, HttpServletRequest request) {
//        获取当前登录用户的id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return Result.ok();
    }

    @ApiOperation("根据就诊人id获取就诊人信息")
    @GetMapping("auth/get/{id}")
    public Result getPatient(@PathVariable Long id) {
        Patient patient = patientService.getPatientId(id);
        return Result.ok(patient);
    }

    @ApiOperation("修改就诊人")
    @PutMapping("auth/update")
    public Result updatePatient(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return Result.ok();
    }

    @ApiOperation("删除就诊人")
    @DeleteMapping("auth/remove/{id}")
    public Result removeById(@PathVariable Long id) {
        patientService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据就诊人id获取就诊人信息")
    @GetMapping("inner/get/{id}")
    public Patient getPatientOrder(@PathVariable Long id) {
        return patientService.getPatientId(id);

    }
}
