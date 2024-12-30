package com.ipman.rpc.grpc.springboot.service.impl;

import com.ipman.rpc.grpc.springboot.lib.GreeterGrpc;
import com.ipman.rpc.grpc.springboot.lib.GreeterOuterClass;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterGrpc;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterGrpc.NewGreeterBlockingStub;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply;
import com.ipman.rpc.grpc.springboot.service.IGrpcClientService;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by ipipman on 2020/12/15.
 *
 * @version V1.0
 * @Package com.ipman.rpc.grpc.springboot.service.impl
 * @Description: (用一句话描述该文件做什么)
 * @date 2020/12/15 6:29 下午
 */
@Service
public class GrpcClientServiceImpl2 implements IGrpcClientService {

    @net.devh.boot.grpc.client.inject.GrpcClient("other-grpc-server")
    private Channel serverChannel2;

    /**
     * 通过本地存protocol buffer存根序列化后调用gRPC服务端
     *
     * @param name
     * @return
     */
    @Override
    public String sendMessage(String name) {
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(serverChannel2);
        GreeterOuterClass.HelloReply response = stub.sayHello(GreeterOuterClass.HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }

    @Override
    public Map sendObject(Map mapData) {
        // 从mapData中获取endpointUrl，假设键名为"endpointUrl"，根据实际情况调整键名
        String endpointUrl = (String) mapData.get("endpoint");
        if (endpointUrl == null) {
            throw new IllegalArgumentException("endpointUrl not found in mapData");
        }
        // 根据获取到的endpointUrl创建Channel实例
        Channel sc = createChannel(endpointUrl);
        NewGreeterBlockingStub stub = NewGreeterGrpc.newBlockingStub(sc);
        NewHelloReply response = stub.sayHello(NewGreeterOuterClass.NewHelloRequest.newBuilder().putAllRequestData(mapData).build());
        return response.getRequestDataMap();
    }

    private Channel createChannel(String address) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(address)
               .usePlaintext() // 如果是非加密连接，使用这个，根据实际安全需求调整
               .build();
        return channel;
    }
}
