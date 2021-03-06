package com.example.handler;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;

import java.util.logging.Logger;

/**
 * @author YanZhao
 * @description: 定义降级规则
 * 注意：返回类型和参数要与调用者一致，且都需要添加异常类，并且方法的修饰符必须是public static
 * @date 2022年06月18日 5:47
 */

public class FallbackHandler {
    private static Logger logger = Logger.getLogger(FallbackHandler.class.toString());


    /**
     * 该函数的传参和返回值类型必须与资源点的传参一样，并且额外选填一个 Throwable 类型的参数用于接收对应的异常
     * @param throwable
     * @return
     */
    public static String fallbackHandlerForDegradeRule(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            logger.warning("接口[/degrade]访问失败，原因是：" + throwable.getMessage());
            return "接口[/degrade]访问失败，原因是：" + throwable.getMessage();
        } else if (throwable instanceof DegradeException) {
            DegradeException e = (DegradeException) throwable;
            DegradeRule rule = e.getRule();
            int time = rule.getTimeWindow();
            logger.warning("接口[/degrade]访问被降级，预计[" + time + "]秒内无法访问");
            return "接口[/degrade]访问被降级，预计[" + time + "]秒内无法访问";
        } else {
            return "";
        }
    }

}
