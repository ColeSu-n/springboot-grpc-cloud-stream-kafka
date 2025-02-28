package com.ipman.rpc.grpc.springboot.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig;
import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig.RecieveRouter;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.ipman.rpc.grpc.springboot.constants.GlobleConstants;
/**
 * Kafka生产者工厂类
 */
public class KafkaProducerFactory  {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerFactory.class);

    /**
     * 根据配置文件中的规则选择kafka集群
     * @return 根据配置文件中的规则选择kafka集群
     */
    private static String getRandomBootstrapServer(String topic) {
       Map<String,KafkaRouterConfig> beansOfKafkaRouterConfig = SpringUtil.getBeansOfType(KafkaRouterConfig.class);
       KafkaRouterConfig kafkaRouterConfig = beansOfKafkaRouterConfig.get(GlobleConstants.KAFKA_ROUTE_CONFIG);
        List<RecieveRouter> recieveRouters = kafkaRouterConfig.getRecieveRouters();
        String kafkaClusterBrokers=GlobleConstants.EMPTY_STRING;

        for (RecieveRouter r : recieveRouters) {
           if (r.getTopic().contains(topic)) {
            kafkaClusterBrokers=r.getKafkaClusters().get(0);
            if (r.getKafkaClusters().size()>0) {
                kafkaClusterBrokers=String.join(",", r.getKafkaClusters());
            }
            return kafkaClusterBrokers;
           };
        }
        return kafkaClusterBrokers;
    }

    /**
     * 发送消息到Kafka
     * @param topic 发送消息的主题
     * @param key 消息的Key
     * @param value 消息的内容
     */
    public static void sendMessage(String topic, String key, String value)throws Exception {
        String bootstrapServers = getRandomBootstrapServer( topic);
        if (StringUtils.isBlank(bootstrapServers)) {
            LOGGER.info("Kafka服务器地址为空,无法输入消息");
            throw new RuntimeException("Kafka服务器地址为空,无法输入消息");
        }
        // 配置Kafka生产者的属性
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);  // Kafka服务器地址
        properties.put("key.serializer", StringSerializer.class.getName());  // 消息Key的序列化方式
        properties.put("value.serializer", StringSerializer.class.getName());  // 消息Value的序列化方式
        properties.put("acks", GlobleConstants.MES_ACK_POLICY);  // 配置消息确认机制

        // 创建KafkaProducer对象
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            // 构造ProducerRecord（即生产者发送的消息）
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            // 发送消息，并提供回调函数
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        LOGGER.info("消息发送成功: " + metadata);
                    } else {
                        LOGGER.info("消息发送失败: " + exception.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            LOGGER.error("Kafka生产者发生异常: " + e.getMessage());
            throw e;
        }
    }
}
