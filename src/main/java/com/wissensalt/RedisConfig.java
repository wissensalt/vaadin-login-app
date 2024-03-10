package com.wissensalt;

import io.lettuce.core.ClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
        "localhost", 6380);
    redisStandaloneConfiguration.setPassword("password");
    return redisStandaloneConfiguration;
  }

  @Bean
  public ClientOptions clientOptions() {
    return ClientOptions.builder()
        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
        .autoReconnect(true)
        .build();
  }

  @Bean
  public RedisConnectionFactory connectionFactory(
      RedisStandaloneConfiguration redisStandaloneConfiguration) {

    LettuceClientConfiguration configuration = LettuceClientConfiguration.builder()
        .clientOptions(clientOptions()).build();

    return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
