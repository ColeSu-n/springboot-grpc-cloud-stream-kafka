package com.ipman.rpc.grpc.springboot.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.stereotype.Component;

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

    @Value("${offset.kafka1.enabled}")
    private boolean seekExecutedForKafka1 ; // 标志变量，确保只执行一次 seek
    @Value("${offset.kafka2.enabled}")
    private boolean seekExecutedForKafka2; // 标志变量，确保只执行一次 seek

    @Value("${offset.kafka1.offset}")
    private Long offsetForKafka1;
    @Value("${offset.kafka2.offset}")
    private Long offsetForKafka2;
    @Value("${spring.cloud.stream.bindings.my-prod-input.group}")
    private String groupId1;
    @Value("${spring.cloud.stream.bindings.my-prod-input2.group}")
    private String groupId2;
    @Bean
    public ApplicationListener<ListenerContainerIdleEvent> idleListener1() {
        boolean resume = true;

        return event -> {

            Consumer<?,?> consumer = event.getConsumer();
            String groupId = consumer.groupMetadata().groupId();
            if (groupId1.equals(groupId)) {

        // 确保 seek 只调用一次
        if (seekExecutedForKafka1) {
            // 获取消费者分配的分区
                Set<TopicPartition> assignment = consumer.assignment();

            // 偏移量设置为 0 或任何你想要的值
            for (TopicPartition topicPartition : assignment) {
                // 设定偏移量
                consumer.seek(topicPartition, offsetForKafka1);
            }

            // 标记 seek 已经执行过
            seekExecutedForKafka1 = false;

            LOGGER.info("Seek has been executed once.");
        }

            Set<TopicPartition> pausedSet = consumer.paused();

            if (resume &&!pausedSet.isEmpty()) {
                Map<String, List<TopicPartition>> topicPartitionGroupedByTopic = pausedSet.stream()
                       .collect(Collectors.groupingBy(TopicPartition::topic, Collectors.toList()));

                topicPartitionGroupedByTopic.forEach((topic, toResumeList) -> {
                    // for (TopicPartition topicPartition : toResumeList) {
                    //     // 设置每个要恢复消费的分区的偏移量为指定的startOffset
                    //     consumer.seek(topicPartition, startOffset);
                        
                    // }
                    consumer.resume(toResumeList);
                    LOGGER.info("Kafka1 - Resumed Consumer in group {} resume Consumer:{} switch to resumed.topic={}",groupId, toResumeList, topic);
                });
            }
            }
        };
    }


    @Bean
    public ApplicationListener<ListenerContainerIdleEvent> idleListener2() {
        boolean resume = true;

        return event -> {

            Consumer<?,?> consumer = event.getConsumer();
            String groupId = consumer.groupMetadata().groupId();
            if (groupId2.equals(groupId)) {

        // 确保 seek 只调用一次
        if (seekExecutedForKafka2) {
            // 获取消费者分配的分区
            Set<TopicPartition> assignment = consumer.assignment();

            // 偏移量设置为 0 或任何你想要的值
            for (TopicPartition topicPartition : assignment) {
                // 设定偏移量
                consumer.seek(topicPartition, offsetForKafka2);
            }

            // 标记 seek 已经执行过
            seekExecutedForKafka2 = false;

            LOGGER.info("Seek has been executed once.");
        }

            Set<TopicPartition> pausedSet = consumer.paused();

            if (resume &&!pausedSet.isEmpty()) {
                Map<String, List<TopicPartition>> topicPartitionGroupedByTopic = pausedSet.stream()
                       .collect(Collectors.groupingBy(TopicPartition::topic, Collectors.toList()));

                topicPartitionGroupedByTopic.forEach((topic, toResumeList) -> {
                    // for (TopicPartition topicPartition : toResumeList) {
                    //     // 设置每个要恢复消费的分区的偏移量为指定的startOffset
                    //     consumer.seek(topicPartition, startOffset);
                        
                    // }
                    consumer.resume(toResumeList);
                    LOGGER.info("Kafka2 - Resumed Consumer in group {} resume Consumer:{} switch to resumed.topic={}",groupId, toResumeList, topic);
                });
            }
            }
        };
    }
}