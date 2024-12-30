package com.ipman.rpc.grpc.springboot.consumer;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig;
import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig.SendRouter;
import com.ipman.rpc.grpc.springboot.service.IGrpcClientService;
import com.ipman.rpc.grpc.springboot.service.impl.GrpcClientServiceImpl2;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type My consumer.
 *
 * @author codeba
 */
@Service
@EnableBinding(MyProdSink.class)
public class MyProdConsumer2 {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private GrpcClientServiceImpl2 grpcClientService;
    @Autowired
    KafkaRouterConfig kafkaRouterConfig;
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyProdConsumer2.class);

    private final AtomicInteger counter = new AtomicInteger(0);

        /**
     * Consume.
     *
     * @param payloads       the payloads
     * @param topics         the topics
     * @param partitionIds   the partition ids
     * @param groupId        the group id
     * @param acknowledgment the acknowledgment
     */
    @StreamListener(MyProdSink.INPUT2)
    public void consume2(
            @Payload List<Object> payloads,
            @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitionIds,
            @Header(KafkaHeaders.GROUP_ID) String groupId,
            @Header(KafkaHeaders.CONSUMER) Consumer<?, ?> consumer,
            @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment
    ) {
        LOGGER.info("consume payloads size: {}", payloads.size());
        // pause consume
        // pause(topics, partitionIds, consumer, true);
        Set<TopicPartition> assignment = consumer.assignment();
        long offset = 0;
        for (TopicPartition topicPartition : assignment) {
            // 然后指定偏移量
            // consumer.seek(topicPartition, offset);
        }
        for (int i = 0; i < payloads.size(); i++) {
            byte[] bytes = (byte[]) payloads.get(i);
            LOGGER.info("payload:{} from topic:{}, partitionId:{}, groupId:{}", new String(bytes), topics.get(i), partitionIds.get(i), groupId);
            // grpcClientService.sendMessage(new String(bytes));
            Map<String,String> hashMap = new HashMap<String,String>();
            hashMap.put("data",new String(bytes));
            for (SendRouter send_routers : kafkaRouterConfig.getSend_routers()) {
               if (send_routers.getTopic().contains(topics.get(i))) {
                   hashMap.put("endpoint",send_routers.getConsumer_endpoint().get(0));
                   break;
               }
           }
            grpcClientService.sendObject(hashMap);
        }
        acknowledgment.acknowledge();
        // String url = "http://172.20.154.162:8081/example/api";
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // Map<String, Object> requestBody = new HashMap<>();
        // requestBody.put("msg", payloads);
        // HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody,headers);
        // ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        // if ( response.getStatusCode()==HttpStatus.OK) {
        //     // 手动提交消息消费确认
        //     acknowledgment.acknowledge();
        //     LOGGER.info("consumer message total:{}", counter.addAndGet(payloads.size()));
        // }
    }


    private Channel createChannel(String address) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(address)
               .usePlaintext() // 如果是非加密连接，使用这个，根据实际安全需求调整
               .build();
        return channel;
    }

}
