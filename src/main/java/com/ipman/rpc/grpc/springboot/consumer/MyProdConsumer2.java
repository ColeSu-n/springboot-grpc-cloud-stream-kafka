package com.ipman.rpc.grpc.springboot.consumer;

import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig;
import org.apache.kafka.clients.consumer.Consumer;
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
import org.springframework.web.client.RestTemplate;

import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig.SendRouter;
import com.ipman.rpc.grpc.springboot.service.impl.GrpcClientServiceImpl2;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        for (int i = 0; i < payloads.size(); i++) {
            byte[] bytes = (byte[]) payloads.get(i);
            LOGGER.info("payload:{} from topic:{}, partitionId:{}, groupId:{}", new String(bytes), topics.get(i), partitionIds.get(i), groupId);
            // grpcClientService.sendMessage(new String(bytes));
            Map<String,String> hashMap = new HashMap<String,String>();
            hashMap.put("data",new String(bytes));
            for (SendRouter routers : kafkaRouterConfig.getSendRouters()) {
               if (routers.getTopic().contains(topics.get(i))) {
                   hashMap.put("endpoint",routers.getConsumerEndpoint().get(0));
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
}
