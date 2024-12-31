package com.ipman.rpc.grpc.springboot.utils;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcUtil {
    public static Channel createChannel(String address) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(address)
               .usePlaintext() // 如果是非加密连接，使用这个，根据实际安全需求调整
               .build();
        return channel;
    }
}
