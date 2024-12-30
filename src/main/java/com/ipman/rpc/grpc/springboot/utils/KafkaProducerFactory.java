package com.ipman.rpc.grpc.springboot.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig;
import com.ipman.rpc.grpc.springboot.config.KafkaRouterConfig.ReceiveRouter;

import cn.hutool.extra.spring.SpringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Arrays;

/**
 * Kafka生产者工厂类
 */
public class KafkaProducerFactory implements ApplicationContextAware {
    // Spring的ApplicationContext对象
    private static ApplicationContext applicationContext;
    // Kafka集群地址列表
    // private  static  List<String> KAFKA_BOOTSTRAP_SERVERS_LIST;
    public static  List<String> KAFKA_BOOTSTRAP_SERVERS_LIST =new ArrayList<String>(Arrays.asList(
            "172.20.154.160:9092",
            "172.20.154.162:9092"
    ));
    
    // 默认的ack机制
    private static final String DEFAULT_ACKS = "all";

    // 随机数生成器
    private static final Random RANDOM = new Random();

    /**
     * 随机选择一个Kafka服务器地址
     * @return 随机选择的Kafka服务器地址
     */
    private static String getRandomBootstrapServer(String topic) {
        // String appName = SpringUtil.getProperty("msg-router.recieve_routers");
       Map<String,KafkaRouterConfig> beansOfType = SpringUtil.getBeansOfType(KafkaRouterConfig.class);
       KafkaRouterConfig kafkaRouterConfig = beansOfType.get("kafkaRouterConfig");
         // 通过 Spring 获取 KafkaRouterConfig 实例
        // KafkaRouterConfig kafkaRouterConfig = applicationContext.getBean(KafkaRouterConfig.class);
        List<ReceiveRouter> recieve_routers = kafkaRouterConfig.getRecieve_routers();
        for (ReceiveRouter r : recieve_routers) {
           if (r.getTopic().contains(topic)) {
               return r.getKafkaClusters();
           };
        }
        int randomIndex = RANDOM.nextInt(KAFKA_BOOTSTRAP_SERVERS_LIST.size());
        return KAFKA_BOOTSTRAP_SERVERS_LIST.get(randomIndex);
    }

    /**
     * 发送消息到Kafka
     * @param topic 发送消息的主题
     * @param key 消息的Key
     * @param value 消息的内容
     */
    public static void sendMessage(String topic, String key, String value) {
        // 获取一个随机的Kafka服务器地址
        String bootstrapServers = getRandomBootstrapServer( topic);

        // 配置Kafka生产者的属性
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);  // Kafka服务器地址
        properties.put("key.serializer", StringSerializer.class.getName());  // 消息Key的序列化方式
        properties.put("value.serializer", StringSerializer.class.getName());  // 消息Value的序列化方式
        properties.put("acks", DEFAULT_ACKS);  // 配置消息确认机制

        // 创建KafkaProducer对象
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            // 构造ProducerRecord（即生产者发送的消息）
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

            // 发送消息，并提供回调函数
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("消息发送成功: " + metadata);
                    } else {
                        System.err.println("消息发送失败: " + exception.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Kafka生产者发生异常: " + e.getMessage());
        }
    }


        /**
     * 实现 ApplicationContextAware 接口的方法
     * @param applicationContext Spring的ApplicationContext对象
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        KafkaProducerFactory.applicationContext = applicationContext;
    }

    /**
     * 获取 Spring 管理的 Bean
     * 示例：从 Spring 容器中获取某个 Bean
     * @param beanName Bean的名称
     * @param beanClass Bean的类型
     * @param <T> Bean的类型
     * @return 返回对应的 Bean 实例
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        if (applicationContext != null) {
            return applicationContext.getBean(beanName, beanClass);
        }
        return null;
    }
}
