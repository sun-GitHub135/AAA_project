package com.aaa.sun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/10 15:18
 * @Description
 *        @ConfiguratinProperties:默认只会从application中取读取属性值
 */
@Component
@PropertySource("classpath:properties/redis_cluster.properties")
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisClusterProperties {
    private String nodes;
    private Integer maxAttempts;
    private Integer commandTimeout;
}
