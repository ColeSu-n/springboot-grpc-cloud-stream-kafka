package com.ipman.rpc.grpc.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableConfigurationProperties
@Component
@ConfigurationProperties(prefix = "msg-router")
public class KafkaRouterConfig {
    private List<ReceiveRouter> recieve_routers;
    private List<SendRouter> send_routers;

    public List<ReceiveRouter> getRecieve_routers() {
        return recieve_routers;
    }

    public void setRecieve_routers(List<ReceiveRouter> recieve_routers) {
        this.recieve_routers = recieve_routers;
    }

    public List<SendRouter> getSend_routers() {
        return send_routers;
    }

    public void setSend_routers(List<SendRouter> send_routers) {
        this.send_routers = send_routers;
    }

    // 内部类用于接收接收路由器（Receive Router）相关配置
    public static class ReceiveRouter {
        private String kafkaClusters;
        private List<String> topic;

        public String getKafkaClusters() {
            return kafkaClusters;
        }

        public void setKafkaClusters(String kafkaClusters) {
            this.kafkaClusters = kafkaClusters;
        }

        public List<String> getTopic() {
            return topic;
        }

        public void setTopic(List<String> topic) {
            this.topic = topic;
        }
    }

    // 内部类用于接收发送路由器（Send Router）相关配置
    public static class SendRouter {
        private String kafkaClusters;
        private List<String> topic;
        private List<String> consumer_endpoint;

        public String getKafkaClusters() {
            return kafkaClusters;
        }

        public void setKafkaClusters(String kafkaClusters) {
            this.kafkaClusters = kafkaClusters;
        }

        public List<String> getTopic() {
            return topic;
        }

        public void setTopic(List<String> topic) {
            this.topic = topic;
        }

        public List<String> getConsumer_endpoint() {
            return consumer_endpoint;
        }

        public void setConsumer_endpoint(List<String> consumer_endpoint) {
            this.consumer_endpoint = consumer_endpoint;
        }
    }
}