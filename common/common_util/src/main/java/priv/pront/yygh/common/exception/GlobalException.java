package priv.pront.yygh.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.pront.yygh.common.result.Result;


/**
 * @Description: 全局异常统一处理
 * @Author: pront
 * @Time:2022-11-12 15:01
 */
//
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class)
    public Result error(YyghException e) {
        e.printStackTrace();
        return Result.fail();
    }
}



