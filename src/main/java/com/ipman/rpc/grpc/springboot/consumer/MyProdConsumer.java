package com.ipman.rpc.grpc.springboot.consumer;

import org.springframework.web.client.RestTemplate;

import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig;
import com.ipman.rpc.grpc.springboot.service.IGrpcClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ipman.rpc.grpc.springboot.constants.GlobleConstants;
/**
 * The type My consumer.
 *
 * @author codeba
 */
@Service
@EnableBinding(MyProdSink.class)
public class MyProdConsumer {

    @Autowired
    KafkaRouterConfig kafkaRouterConfig;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private IGrpcClientService grpcClientService;
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyProdConsumer.class);

    /**
     * 通用的消费处理方法
     */
    private void processConsume(
            List<Object> payloads,
            List<String> topics,
            List<Integer> partitionIds,
            String groupId,
            Acknowledgment acknowledgment
    ) {
        LOGGER.info("consume payloads size: {}", payloads.size());

        for (int i = 0; i < payloads.size(); i++) {
            byte[] bytes = (byte[]) payloads.get(i);
            LOGGER.info("payload:{} from topic:{}, partitionId:{}, groupId:{}", new String(bytes), topics.get(i), partitionIds.get(i), groupId);

            // 创建要发送的 Map
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put(GlobleConstants.REQUEST_DATA, new String(bytes));

            // 根据 topic 查找对应的 ConsumerEndpoint
            for (KafkaRouterConfig.SendRouter sendRouter : kafkaRouterConfig.getSendRouters()) {
                if (sendRouter.getTopic().contains(topics.get(i))) {
                    hashMap.put("endpoint", sendRouter.getConsumerEndpoint().get(0));
                    break;
                }
            }

            // 发送处理后的消息
            grpcClientService.sendObject(hashMap);
        }
        acknowledgment.acknowledge();
    }

    /**
     * 消费第一个输入通道的消息
     */
    @StreamListener(MyProdSink.INPUT)
    public void consume(
            @Payload List<Object> payloads,
            @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitionIds,
            @Header(KafkaHeaders.GROUP_ID) String groupId,
            @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment
    ) {
        processConsume(payloads, topics, partitionIds, groupId, acknowledgment);
    }

    /**
     * 消费第二个输入通道的消息
     */
    @StreamListener(MyProdSink.INPUT2)
    public void consume2(
            @Payload List<Object> payloads,
            @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitionIds,
            @Header(KafkaHeaders.GROUP_ID) String groupId,
            @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment
    ) {
        processConsume(payloads, topics, partitionIds, groupId, acknowledgment);
    }

    /**
     * 消费第三个输入通道的消息
     */
    @StreamListener(MyProdSink.INPUT3)
    public void consume3(
            @Payload List<Object> payloads,
            @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitionIds,
            @Header(KafkaHeaders.GROUP_ID) String groupId,
            @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment
    ) {
        processConsume(payloads, topics, partitionIds, groupId, acknowledgment);
    }
}
