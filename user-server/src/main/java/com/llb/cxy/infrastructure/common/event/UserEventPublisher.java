package com.llb.cxy.infrastructure.common.event;

import com.llb.cxy.domain.user.event.UserEvent;
import org.springframework.stereotype.Service;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.infrastructure.common.event
 * @Description: 事件发布者
 * @ClassName: EventPublisher
 * @date 2021-01-13 上午7:37
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class UserEventPublisher {
    public void publish(UserEvent event){
        //send to MQ
        //mq.send(event);
    }
}
