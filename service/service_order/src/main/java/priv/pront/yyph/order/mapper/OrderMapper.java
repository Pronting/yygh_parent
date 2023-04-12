package priv.pront.yyph.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.vo.order.OrderCountQueryVo;
import priv.pront.yygh.vo.order.OrderCountVo;

import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-20 20:43
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {

    /**
     * 查询预约统计数据的方法
     * @param orderCountQueryVo 前端封装的对象
     * @return
     */
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
