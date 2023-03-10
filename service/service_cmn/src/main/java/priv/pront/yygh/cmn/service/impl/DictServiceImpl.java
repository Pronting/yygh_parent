package priv.pront.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import priv.pront.yygh.cmn.mapper.DictMapper;
import priv.pront.yygh.cmn.service.DictService;
import priv.pront.yygh.model.cmn.Dict;
import priv.pront.yygh.vo.cmn.DictEeVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: HospitalService的实现类
 * @Author: pront
 * @Time:2022-11-11 09:36
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    /**
     * 根据数据id查询子数据列表
     *
     * @param id 父节点id
     * @return 子节点集合
     */
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
//        想list集合每一个dict对象中设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    @Override
    public void exportDictData(HttpServletResponse response) {
        try {
//        设置下载的相关内容
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = "dict";
            fileName = URLEncoder.encode("数据字典", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

//            查询数据库
            List<Dict> dictList = baseMapper.selectList(null);
//            dict --->DictEeVo
            List<DictEeVo> dictVoList = new ArrayList<>();
            for (Dict dict : dictList) {
                DictEeVo dictEeVo = new DictEeVo();
//                dictEeVo.setId(dict.getId());
                BeanUtils.copyProperties(dict, dictEeVo);
                dictVoList.add(dictEeVo);
            }

//            调用方法进行写的操作
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet().doWrite(dictVoList);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getDictName(String dictCode, String value) {
//        如果dictCode本身为空，直接根据value查询
        if (StringUtils.isEmpty(dictCode)) {
//            直接根据value查询
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        } else {
//            根据dictCode查询dict对象，得到dict的id值
            Dict codeDict = this.getDictByDictCode(dictCode);
            Long id = codeDict.getId();
//            根据id和value值进行查询
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", id).eq("value", value));
            return finalDict.getName();
        }
    }


    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict codeDict = baseMapper.selectOne(wrapper);
        return codeDict;
    }


    /**
     * 判断id下面是否有子节点
     *
     * @param id 父节点id
     * @return 是否有子节点
     */
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

//    ServiceImpl 已经 注入了 HospitalSetMapper

    @Override
    public List<Dict> findByDictCode(String dictCode) {
//        根据dicCode获取对应的id
        Dict dict = this.getDictByDictCode(dictCode);
//        根据id获取到子节点
        return this.findChildData(dict.getId());
    }
}
