package com.ipman.rpc.grpc.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableConfigurationProperties
@Component
@ConfigurationProperties(prefix = "msg-router")
public class KafkaRouterConfig {
    private List<RecieveRouter> recieveRouters;
    private List<SendRouter> sendRouters;

    // Getter and Setter

    public List<RecieveRouter> getRecieveRouters() {
        return recieveRouters;
    }

    public void setRecieveRouters(List<RecieveRouter> recieveRouters) {
        this.recieveRouters = recieveRouters;
    }

    public List<SendRouter> getSendRouters() {
        return sendRouters;
    }

    public void setSendRouters(List<SendRouter> sendRouters) {
        this.sendRouters = sendRouters;
    }

    // Inner classes for RecieveRouter and SendRouter
    public static class RecieveRouter {
        private List<String> kafkaClusters;
        private List<String> topic;

        // Getter and Setter
        public List<String> getKafkaClusters() {
            return kafkaClusters;
        }

        public void setKafkaClusters(List<String> kafkaClusters) {
            this.kafkaClusters = kafkaClusters;
        }

        public List<String> getTopic() {
            return topic;
        }

        public void setTopic(List<String> topic) {
            this.topic = topic;
        }
    }

    public static class SendRouter {
        private List<String> kafkaClusters;
        private List<String> topic;
        private List<String> consumerEndpoint;

        // Getter and Setter
        public List<String> getKafkaClusters() {
            return kafkaClusters;
        }

        public void setKafkaClusters(List<String> kafkaClusters) {
            this.kafkaClusters = kafkaClusters;
        }

        public List<String> getTopic() {
            return topic;
        }

        public void setTopic(List<String> topic) {
            this.topic = topic;
        }

        public List<String> getConsumerEndpoint() {
            return consumerEndpoint;
        }

        public void setConsumerEndpoint(List<String> consumerEndpoint) {
            this.consumerEndpoint = consumerEndpoint;
        }
    }
}