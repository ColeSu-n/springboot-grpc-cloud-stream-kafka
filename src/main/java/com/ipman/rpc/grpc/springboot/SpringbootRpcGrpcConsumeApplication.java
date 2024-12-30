package com.ipman.rpc.grpc.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.ipman.rpc.grpc.springboot")
@SpringBootApplication
public class SpringbootRpcGrpcConsumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRpcGrpcConsumeApplication.class, args);
	}
}
