package priv.pront.easyexcel.test;

import com.alibaba.excel.EasyExcel;
import priv.pront.easyexcel.model.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 测试EasyExcel写入
 * @Author: pront
 * @Time:2022-11-18 11:11
 */
public class WriteTest {

    public static void main(String[] args) {
//        构建一个数据list集合
        List<UserData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("lucy" + i);
            list.add(data);
        }

//         设置Excel文件路径和文件名称
        String fileName = "C:\\tmp\\EasyExcelTest\\01.xlsx";

//        调用方法实现写入操作
        EasyExcel.write(fileName, UserData.class).sheet("用户信息")
                .doWrite(list);


    }
}
