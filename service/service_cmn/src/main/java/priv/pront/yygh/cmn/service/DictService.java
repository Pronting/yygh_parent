package priv.pront.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.cmn.Dict;
import priv.pront.yygh.model.hosp.HospitalSet;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 医院设置的接口
 * @Author: pront
 * @Time:2022-11-11 09:34
 */
public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void exportDictData(HttpServletResponse response);

    String getDictName(String dictCode, String value);
}
