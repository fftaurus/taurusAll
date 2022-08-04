package com.taurus.zjbm.common.config;

import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/**
 * redis集群配置
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisClusterConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        LettuceConnectionFactory lettuceConnectionFactory = null;
        if (this.redisProperties.getCluster() == null) {
            final RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(this.redisProperties.getHost(), this.redisProperties.getPort());
            standaloneConfig.setPassword(this.redisProperties.getPassword());
            lettuceConnectionFactory = new LettuceConnectionFactory(standaloneConfig);
        } else {
            final RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(
                    this.redisProperties.getCluster().getNodes());
            redisClusterConfiguration.setPassword(this.redisProperties.getPassword());
            final ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                    .enableAllAdaptiveRefreshTriggers().adaptiveRefreshTriggersTimeout(Duration.ofSeconds(10))
                    .build();

            final ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                    .timeoutOptions(TimeoutOptions.enabled(Duration.ofSeconds(30)))
                    .topologyRefreshOptions(clusterTopologyRefreshOptions).build();

            final LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                    .clientOptions(clusterClientOptions).build();
            lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
        }
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory redisConnectionfactory) {

        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionfactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
