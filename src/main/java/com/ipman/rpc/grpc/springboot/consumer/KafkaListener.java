package com.ipman.rpc.grpc.springboot.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.stereotype.Component;

import com.ipman.rpc.grpc.springboot.config.KafkaConsumerConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KafkaListener {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListener.class);

    @Autowired
    private KafkaConsumerConfig  kafkaConsumerConfig;
    /**
     * 创建一个通用的 idleListener
     */
    @Bean
    public ApplicationListener<ListenerContainerIdleEvent> idleListener() {
        return event -> {
            Consumer<?, ?> consumer = event.getConsumer();
            String groupId = consumer.groupMetadata().groupId();

            // 查找配置中对应的消费者配置
            KafkaConsumerConfig.ConsumerConfig consumerConfig = findConsumerConfig(groupId);
            if (consumerConfig != null) {
                boolean seekExecuted = consumerConfig.isSeekExecuted();
                long offset = consumerConfig.getOffset();

                // 如果 seek 未执行过，则执行 seek 操作
                if (seekExecuted) {
                    Set<TopicPartition> assignment = consumer.assignment();
                    for (TopicPartition topicPartition : assignment) {
                        consumer.seek(topicPartition, offset);
                    }

                    // 更新 seek 已执行标记
                    consumerConfig.setSeekExecuted(false);
                    LOGGER.info("Seek has been executed for group {}", groupId);
                }

                // 恢复暂停的分区消费
                Set<TopicPartition> pausedSet = consumer.paused();
                if (!pausedSet.isEmpty()) {
                    Map<String, List<TopicPartition>> topicPartitionGroupedByTopic = pausedSet.stream()
                            .collect(Collectors.groupingBy(TopicPartition::topic, Collectors.toList()));

                    topicPartitionGroupedByTopic.forEach((topic, toResumeList) -> {
                        consumer.resume(toResumeList);
                        LOGGER.info("Resumed Consumer in group {} for topic {}", groupId, topic);
                    });
                }
            }
        };
    }

    /**
     * 根据 groupId 查找对应的消费者配置
     */
    private KafkaConsumerConfig.ConsumerConfig findConsumerConfig(String groupId) {
        return kafkaConsumerConfig.getConsumers().stream()
                .filter(config -> config.getGroupId().equals(groupId))
                .findFirst()
                .orElse(null);
    }
}