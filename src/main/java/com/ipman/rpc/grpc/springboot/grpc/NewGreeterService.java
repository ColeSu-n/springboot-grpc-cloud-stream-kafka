package com.ipman.rpc.grpc.springboot.grpc;

import java.util.Map;

import com.ipman.rpc.grpc.springboot.lib.NewGreeterGrpc;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass;
import com.ipman.rpc.grpc.springboot.utils.KafkaProducerFactory;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import com.ipman.rpc.grpc.springboot.constants.GlobleConstants;
/**
 * Created by ipipman on 2020/12/15.
 *
 * @version V1.0
 * @Package com.ipman.rpc.grpc.springboot.server
 * @Description: (用一句话描述该文件做什么)
 * @date 2020/12/15 6:23 下午
 */
@Slf4j
@GrpcService
public class NewGreeterService extends NewGreeterGrpc.NewGreeterImplBase {

    /**
     * gRPC Server started, listening on address: 0.0.0.0, port: 8800
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void sayHello(NewGreeterOuterClass.NewHelloRequest request, StreamObserver<NewGreeterOuterClass.NewHelloReply> responseObserver) {
        // 获取请求中的map字段（request_data）
        String topicString=GlobleConstants.EMPTY_STRING;
        Map<String, String> requestDataMap = request.getRequestDataMap();
        System.out.println("Request Data Map:");
        for (Map.Entry<String, String> entry : requestDataMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (GlobleConstants.KAFKA_TOPIC_KEY.equals(key)) {
                topicString = value;
            }
            try {
                // 直接将Any类型的值转换为字符串并打印（不管其具体类型）
                String valueStr = value.toString();
                System.out.println("Key: " + key + ", Value: " + valueStr);
            } catch (Exception e) {
                System.err.println("Error converting Any value to string: " + e.getMessage());
            }
        }
        // 构建响应消息
        NewGreeterOuterClass.NewHelloReply reply = NewGreeterOuterClass.NewHelloReply.newBuilder().putAllRequestData(requestDataMap).build();

        // 通过 responseObserver 发送响应给客户端
        try {
            KafkaProducerFactory.sendMessage( topicString, "", request.toString());
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // TODO: handle exception
            responseObserver.onError(e);
        }
    }

}