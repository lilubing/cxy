package com.llb.cxy.domain.user.event;

import com.alibaba.fastjson.JSON;
import com.llb.cxy.core.id.Identities;
import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.infrastructure.common.event.DomainEvent;
import lombok.Data;

import java.util.Date;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.event
 * @Description: 用户事件
 * @ClassName: UserEvent
 * @date 2021-01-13 上午7:42
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class UserEvent extends DomainEvent {

    UserEventType userEventType;

    /**
     * 创建事件对象
     * @Author LiLuBing
     * @Date 2021-01-13 07:46
     * @Param userEventType
     * @param userInfo
     * @return {@link com.llb.cxy.domain.user.event.UserEvent}
     **/
    public static UserEvent create(UserEventType userEventType, UserInfo userInfo){
        UserEvent event = new UserEvent();
        event.setId(Identities.getId());
        event.setUserEventType(userEventType);
        event.setTimestamp(new Date());
        event.setData(JSON.toJSONString(userInfo));
        return event;
    }
}
