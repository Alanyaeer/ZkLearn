package com.learn.zookeeper.config;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import java.io.IOException;
 
@Configuration
public class ZookeeperConfig {
    @Value("${zookeeper.server}")
    private String server;
 
    @Value("${zookeeper.timeout}")
    private Integer timeout;

    @Bean
    public ZooKeeper zkClient() throws IOException {
        return new ZooKeeper(server, timeout, watchedEvent -> {});
    }
}