package com.llb.cxy.infrastructure.common.event;

import lombok.Data;

import java.util.Date;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.infrastructure.common.event
 * @Description: 事件实体对象
 * @ClassName: DomainEvent
 * @date 2021-01-13 上午7:36
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class DomainEvent {

    Long id;
    Date timestamp;
    String source;
    String data;
}