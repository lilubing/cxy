package com.llb.cxy.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description: 自定义ID生成器配置 <br>
 * date: 2019/12/18 10:14 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Component
@ConfigurationProperties(prefix = "snowflake.id")
public class LLBIdGenProperties {
    /**
     * 工作ID (0~31)
     */
    private long workId = 0;
    /**
     * 数据中心ID (0~31)
     */
    private long centerId = 0;

    public long getWorkId() {
        return workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    public long getCenterId() {
        return centerId;
    }

    public void setCenterId(long centerId) {
        this.centerId = centerId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OpenIdGenProperties{");
        sb.append("workId=").append(workId);
        sb.append(", centerId=").append(centerId);
        sb.append('}');
        return sb.toString();
    }
}
