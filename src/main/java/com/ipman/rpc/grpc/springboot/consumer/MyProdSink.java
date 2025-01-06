package com.ipman.rpc.grpc.springboot.consumer;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * The interface My sink.
 *
 * @author codeba
 */
public interface MyProdSink {
    /**
     * The constant INPUT.
     */
    String INPUT = "my-prod-input";

    /**
     * Input subscribable channel.
     *
     * @return the subscribable channel
     */
    @Input(INPUT)
    SubscribableChannel input();


        /**
     * The constant INPUT.
     */
    String INPUT2 = "my-prod-input2";

    /**
     * Input subscribable channel.
     *
     * @return the subscribable channel
     */
    @Input(INPUT2)
    SubscribableChannel input2();


    /**
     * The constant INPUT.
     */
    String INPUT3 = "my-prod-input3";

    /**
     * Input subscribable channel.
     *
     * @return the subscribable channel
     */
    @Input(INPUT3)
    SubscribableChannel input3();

}
