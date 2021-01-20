package com.llb.cxy.boot.config.security.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.boot.config.security.config
 * @Description: 网关白名单配置
 * @ClassName: Test
 * @date 2021-01-19 下午8:19
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
