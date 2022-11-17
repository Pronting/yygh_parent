package priv.pront.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.pront.yygh.cmn.mapper.DictMapper;
import priv.pront.yygh.cmn.service.DictService;
import priv.pront.yygh.model.cmn.Dict;

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


    /**
     * 判断id下面是否有子节点
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
}
