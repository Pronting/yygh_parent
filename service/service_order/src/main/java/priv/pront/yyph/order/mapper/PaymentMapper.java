package priv.pront.yyph.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import priv.pront.yygh.model.order.PaymentInfo;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 20:13
 */
@Mapper
public interface PaymentMapper extends BaseMapper<PaymentInfo> {
}
