package priv.pront.yyph.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import priv.pront.yygh.model.order.OrderInfo;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-20 20:43
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
}
