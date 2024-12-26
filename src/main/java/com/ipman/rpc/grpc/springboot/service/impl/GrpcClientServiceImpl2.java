package com.ipman.rpc.grpc.springboot.service.impl;

import com.ipman.rpc.grpc.springboot.lib.GreeterGrpc;
import com.ipman.rpc.grpc.springboot.lib.GreeterOuterClass;
import com.ipman.rpc.grpc.springboot.service.IGrpcClientService;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
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
}
