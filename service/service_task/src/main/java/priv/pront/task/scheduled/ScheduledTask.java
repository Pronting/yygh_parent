package priv.pront.task.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.pront.yyph.common.rabbit.constant.MqConstant;
import priv.pront.yyph.common.rabbit.service.RabbitService;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-04-12 10:39
 */
@Component
@EnableScheduling  // 开启定时任务操作
public class ScheduledTask {


    @Autowired
    private RabbitService rabbitService;
    //  在每天8点执行方法
//    !cron是一种表达式 设置执行间隔
    @Scheduled(cron = "0 0 8 * * ?")
    public void taskPatient() {
        rabbitService.sendMessage(MqConstant.EXCHANGE_DIRECT_TASK,MqConstant.ROUTING_TASK_8,"");
    }

}
