package com.ipman.rpc.grpc.springboot.service.impl;

import com.ipman.rpc.grpc.springboot.consumer.MyProdConsumer;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterGrpc;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterGrpc.NewGreeterBlockingStub;
import com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply;
import com.ipman.rpc.grpc.springboot.service.IGrpcClientService;
import com.ipman.rpc.grpc.springboot.utils.GrpcUtil;

import cn.hutool.extra.spring.SpringUtil;
import io.grpc.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
     private static final Logger LOGGER = LoggerFactory.getLogger(IGrpcClientService.class);
    /**
     * 通过本地存protocol buffer存根序列化后调用gRPC服务端
     * @param mapData
     * @return
     */
    @Override
    public Map sendObject(Map mapData) {
        String endpoint = (String)mapData.get("endpoint");
        Map<String,DiscoveryClient> beansOfTypeDiscoveryClient = SpringUtil.getBeansOfType(DiscoveryClient.class);
        DiscoveryClient discoveryClient = beansOfTypeDiscoveryClient.get("consulDiscoveryClient");
        if (!discoveryClient.getServices().contains(endpoint)) {
            LOGGER.info("not found consul server instance", new RuntimeException("not found consul server instance"));
            return new HashMap<>();
        }
        Map<String,Object> resp = new HashMap<String,Object>();
        for (ServiceInstance instances : discoveryClient.getInstances(endpoint)) {
            String instanceId = instances.getInstanceId();
            String[] split = instanceId.split("-");
            String grpcPort=split[split.length-1];
            Channel sc = GrpcUtil.createChannel(instances.getHost()+":"+grpcPort);
            NewGreeterBlockingStub stub = NewGreeterGrpc.newBlockingStub(sc);
            NewHelloReply response = stub.sayHello(NewGreeterOuterClass.NewHelloRequest.newBuilder().putAllRequestData(mapData).build());
            
            resp.put("instances.getHost()+\":\"+instances.getPort()", response.getRequestDataMap());
        }
        return resp;
    }
}
