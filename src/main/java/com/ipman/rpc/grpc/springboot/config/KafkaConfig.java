package com.ipman.rpc.grpc.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

@EnableConfigurationProperties
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {

    private List<String> clusters;

    public List<String> getClusters() {
        return clusters;
    }

    public void setClusters(List<String> clusters) {
        this.clusters = clusters;
    }
}
