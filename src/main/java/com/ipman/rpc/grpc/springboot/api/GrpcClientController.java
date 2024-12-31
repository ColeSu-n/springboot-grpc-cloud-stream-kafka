package com.ipman.rpc.grpc.springboot.api;

import com.ipman.rpc.grpc.springboot.service.IGrpcClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by ipipman on 2020/12/15.
 *
 * @version V1.0
 * @Package com.ipman.rpc.grpc.springboot.api
 * @Description: (用一句话描述该文件做什么)
 * @date 2020/12/15 6:34 下午
 */
@RestController
public class GrpcClientController {
    @Value("${test.name }")
    private String testName;
    @Autowired
    private IGrpcClientService grpcClientService;

    /**
     * Testing
     *
     * @param name
     * @return
     */
    @GetMapping("/")
    public Map printMessage(@RequestParam(defaultValue = "ipman") String name) {
     Map<String,String> hashMap = new HashMap<String,String>();
     hashMap.put("data",name);
        return grpcClientService.sendObject(hashMap);
    }
    
    @GetMapping("/testconfig")
    public String testConfig() {
     //测试配置文件热更新
     System.out.println("====================="+testName);
     return testName;
    }
}