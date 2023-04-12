package priv.pront.yyph.order.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.pront.yyph.common.rabbit.constant.MqConstant;
import priv.pront.yyph.order.service.OrderService;

import java.io.IOException;

/**
 * @Description: 定时任务监听器
 * @Author: pront
 * @Time:2023-04-12 10:45
 */
@Component
public class OrderReceiver {

    @Autowired
    private OrderService orderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_TASK_8, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_DIRECT_TASK),
            key = {MqConstant.ROUTING_TASK_8}
    ))
    public void patientTips(Message message, Channel channel) throws IOException {
        orderService.patientTips();
    }

}
