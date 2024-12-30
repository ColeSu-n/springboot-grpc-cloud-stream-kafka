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


    private boolean seekExecuted = true; // 标志变量，确保只执行一次 seek

    
    @Value("${enable_offset.kafka1.offset}")
    private Long offset1;
    @Value("${enable_offset.kafka2.offset}")
    private Long offset2;
    @Bean
    public ApplicationListener<ListenerContainerIdleEvent> idleListener1() {
        boolean resume = true;

        return event -> {

            Consumer<?,?> consumer = event.getConsumer();

            // TopicPartition topicPartition = new TopicPartition("test1", 0);
            // consumer.seek(topicPartition, startOffset);
            String groupId = consumer.groupMetadata().groupId();
            if ("test".equals(groupId)) {
            

        // 确保 seek 只调用一次
        if (!seekExecuted) {
            // 获取消费者分配的分区
            Set<TopicPartition> assignment = consumer.assignment();

            // 偏移量设置为 0 或任何你想要的值
            for (TopicPartition topicPartition : assignment) {
                // 设定偏移量
                consumer.seek(topicPartition, offset1);
            }

            // 标记 seek 已经执行过
            seekExecuted = true;

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

            // TopicPartition topicPartition = new TopicPartition("test1", 0);
            // consumer.seek(topicPartition, startOffset);
            String groupId = consumer.groupMetadata().groupId();
            if ("test2".equals(groupId)) {
            

        // 确保 seek 只调用一次
        if (!seekExecuted) {
            // 获取消费者分配的分区
            Set<TopicPartition> assignment = consumer.assignment();

            // 偏移量设置为 0 或任何你想要的值
            for (TopicPartition topicPartition : assignment) {
                // 设定偏移量
                consumer.seek(topicPartition, offset2);
            }

            // 标记 seek 已经执行过
            seekExecuted = true;

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