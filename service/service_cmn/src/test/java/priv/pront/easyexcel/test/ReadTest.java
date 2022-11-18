package priv.pront.easyexcel.test;

import com.alibaba.excel.EasyExcel;
import priv.pront.easyexcel.listenter.ExcelListener;
import priv.pront.easyexcel.model.UserData;

/**
 * @Description: 读取测试
 * @Author: pront
 * @Time:2022-11-18 11:50
 */
public class ReadTest {

    public static void main(String[] args) {
//         读取文件的路径
        String fileName = "C:\\tmp\\EasyExcelTest\\01.xlsx";
//        调用方法实现读取操作
        EasyExcel.read(fileName, UserData.class,new ExcelListener()).sheet().doRead();

    }
}
