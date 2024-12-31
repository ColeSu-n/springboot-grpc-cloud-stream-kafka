package com.ipman.rpc.grpc.springboot.service.impl;

import com.ipman.rpc.grpc.springboot.lib.GreeterGrpc;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterGrpc;
import com.ipman.rpc.grpc.springboot.lib.GreeterOuterClass;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterGrpc.NewGreeterBlockingStub;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply;
import com.ipman.rpc.grpc.springboot.service.IGrpcClientService;
import com.ipman.rpc.grpc.springboot.utils.GrpcUtil;

import io.grpc.Channel;

import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Created by ipipman on 2020/12/15.
 *
 * @version V1.0
 * @Package com.ipman.rpc.grpc.springboot.service.impl
 * @Description: (用一句话描述该文件做什么)
 * @date 2020/12/15 6:29 下午
 */
@Service
public class GrpcClientServiceImpl implements IGrpcClientService {
    /**
     * 通过本地存protocol buffer存根序列化后调用gRPC服务端
     * 没用了，等会删
     * @param mapData
     * @return
     */
    @Override
    public Map sendObject(Map mapData) {
        String endpointUrl = (String) mapData.get("endpoint");
        if (endpointUrl == null) {
            throw new IllegalArgumentException("endpointUrl not found in mapData");
        }
        Channel sc = GrpcUtil.createChannel(endpointUrl);
        NewGreeterBlockingStub stub = NewGreeterGrpc.newBlockingStub(sc);
        NewHelloReply response = stub.sayHello(NewGreeterOuterClass.NewHelloRequest.newBuilder().putAllRequestData(mapData).build());
        return response.getRequestDataMap();
    }
}
