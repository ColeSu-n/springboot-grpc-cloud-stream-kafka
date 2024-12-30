package com.ipman.rpc.grpc.springboot.lib;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The greeter service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.1)",
    comments = "Source: newGreeter.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NewGreeterGrpc {

  private NewGreeterGrpc() {}

  public static final String SERVICE_NAME = "NewGreeter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest,
      com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply> getSayHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SayHello",
      requestType = com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest.class,
      responseType = com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest,
      com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply> getSayHelloMethod() {
    io.grpc.MethodDescriptor<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest, com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply> getSayHelloMethod;
    if ((getSayHelloMethod = NewGreeterGrpc.getSayHelloMethod) == null) {
      synchronized (NewGreeterGrpc.class) {
        if ((getSayHelloMethod = NewGreeterGrpc.getSayHelloMethod) == null) {
          NewGreeterGrpc.getSayHelloMethod = getSayHelloMethod =
              io.grpc.MethodDescriptor.<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest, com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply.getDefaultInstance()))
              .setSchemaDescriptor(new NewGreeterMethodDescriptorSupplier("SayHello"))
              .build();
        }
      }
    }
    return getSayHelloMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NewGreeterStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NewGreeterStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NewGreeterStub>() {
        @java.lang.Override
        public NewGreeterStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NewGreeterStub(channel, callOptions);
        }
      };
    return NewGreeterStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NewGreeterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NewGreeterBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NewGreeterBlockingStub>() {
        @java.lang.Override
        public NewGreeterBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NewGreeterBlockingStub(channel, callOptions);
        }
      };
    return NewGreeterBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NewGreeterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NewGreeterFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NewGreeterFutureStub>() {
        @java.lang.Override
        public NewGreeterFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NewGreeterFutureStub(channel, callOptions);
        }
      };
    return NewGreeterFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    default void sayHello(com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest request,
        io.grpc.stub.StreamObserver<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSayHelloMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service NewGreeter.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static abstract class NewGreeterImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NewGreeterGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service NewGreeter.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class NewGreeterStub
      extends io.grpc.stub.AbstractAsyncStub<NewGreeterStub> {
    private NewGreeterStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NewGreeterStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NewGreeterStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayHello(com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest request,
        io.grpc.stub.StreamObserver<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSayHelloMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service NewGreeter.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class NewGreeterBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NewGreeterBlockingStub> {
    private NewGreeterBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NewGreeterBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NewGreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply sayHello(com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSayHelloMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service NewGreeter.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class NewGreeterFutureStub
      extends io.grpc.stub.AbstractFutureStub<NewGreeterFutureStub> {
    private NewGreeterFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NewGreeterFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NewGreeterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply> sayHello(
        com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSayHelloMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY_HELLO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_HELLO:
          serviceImpl.sayHello((com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest) request,
              (io.grpc.stub.StreamObserver<com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSayHelloMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloRequest,
              com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.NewHelloReply>(
                service, METHODID_SAY_HELLO)))
        .build();
  }

  private static abstract class NewGreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NewGreeterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.ipman.rpc.grpc.springboot.lib.NewGreeterOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NewGreeter");
    }
  }

  private static final class NewGreeterFileDescriptorSupplier
      extends NewGreeterBaseDescriptorSupplier {
    NewGreeterFileDescriptorSupplier() {}
  }

  private static final class NewGreeterMethodDescriptorSupplier
      extends NewGreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    NewGreeterMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NewGreeterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NewGreeterFileDescriptorSupplier())
              .addMethod(getSayHelloMethod())
              .build();
        }
      }
    }
    return result;
  }
}
