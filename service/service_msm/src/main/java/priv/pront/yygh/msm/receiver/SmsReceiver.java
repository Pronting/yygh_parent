package priv.pront.yygh.msm.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.pront.yygh.msm.service.MsmService;
import priv.pront.yygh.vo.msm.MsmVo;
import priv.pront.yyph.common.rabbit.constant.MqConstant;


/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-21 14:41
 */
@Component
public class SmsReceiver {

    @Autowired
    private MsmService msmService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_DIRECT_MSM),
            key = {MqConstant.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
        msmService.send(msmVo);
    }
}
