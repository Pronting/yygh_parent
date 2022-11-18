package priv.pront.easyexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description: EasyExcel测试
 * @Author: pront
 * @Time:2022-11-18 11:09
 */
@Data
public class UserData {

    @ExcelProperty(value = "用户编号",index=0)
    private int uid;

    @ExcelProperty(value = "用户名称",index = 1)
    private String username;
}
