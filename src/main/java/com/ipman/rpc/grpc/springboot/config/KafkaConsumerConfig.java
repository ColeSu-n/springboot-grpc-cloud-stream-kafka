package com.ipman.rpc.grpc.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

@RefreshScope
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "kafka-consumer")
@Component
public class KafkaConsumerConfig {

    private List<ConsumerConfig> consumers;

    public List<ConsumerConfig> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumerConfig> consumers) {
        this.consumers = consumers;
    }

    public static class ConsumerConfig {
        private String groupId;
        private boolean seekExecuted;
        private long offset;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public boolean isSeekExecuted() {
            return seekExecuted;
        }

        public void setSeekExecuted(boolean seekExecuted) {
            this.seekExecuted = seekExecuted;
        }

        public long getOffset() {
            return offset;
        }

        public void setOffset(long offset) {
            this.offset = offset;
        }
    }
}