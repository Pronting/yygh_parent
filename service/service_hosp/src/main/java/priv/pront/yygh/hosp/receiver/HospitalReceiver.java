package priv.pront.yygh.hosp.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.pront.yygh.hosp.service.ScheduleService;
import priv.pront.yygh.model.hosp.Schedule;
import priv.pront.yygh.vo.msm.MsmVo;
import priv.pront.yygh.vo.order.OrderMqVo;
import priv.pront.yyph.common.rabbit.constant.MqConstant;
import priv.pront.yyph.common.rabbit.service.RabbitService;

import java.io.IOException;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-21 14:52
 */
@Component
public class HospitalReceiver {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RabbitService rabbitService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_DIRECT_ORDER),
            key = {MqConstant.ROUTING_ORDER}
    ))
    public void receiver(OrderMqVo orderMqVo, Message message, Channel channel) throws IOException {
        if (null != orderMqVo.getAvailableNumber()) {
            //下单成功更新预约数
            Schedule schedule = scheduleService.getScheduleById(orderMqVo.getScheduleId());
            schedule.setReservedNumber(orderMqVo.getReservedNumber());
            schedule.setAvailableNumber(orderMqVo.getAvailableNumber());
            scheduleService.update(schedule);

        } else {
            //取消预约更新预约数
            Schedule schedule = scheduleService.getScheduleById(orderMqVo.getScheduleId());
            int availableNumber = schedule.getAvailableNumber().intValue() + 1;
            schedule.setAvailableNumber(availableNumber);
            scheduleService.update(schedule);
        }
        //发送短信
        MsmVo msmVo = orderMqVo.getMsmVo();
        if(null != msmVo){
            rabbitService.sendMessage(MqConstant.EXCHANGE_DIRECT_MSM, MqConstant.ROUTING_MSM_ITEM, msmVo);
        }
    }

}
