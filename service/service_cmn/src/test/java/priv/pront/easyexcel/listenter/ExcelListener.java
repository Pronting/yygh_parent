package priv.pront.easyexcel.listenter;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import priv.pront.easyexcel.model.UserData;

import java.util.Map;

/**
 * @Description: 监听器
 * @Author: pront
 * @Time:2022-11-18 11:46
 */
public class ExcelListener extends AnalysisEventListener<UserData> {
    /**
     * 会输出第0行的内容
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息" + headMap);
    }

    /**
     * 一行一行的去读取Excel内容，从第二行读取
     * @param userData 一行数据返回的对象
     * @param analysisContext
     */
    @Override
    public void invoke(UserData userData, AnalysisContext analysisContext) {
        System.out.println(userData);
    }

    /**
     * 读取之后执行
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
