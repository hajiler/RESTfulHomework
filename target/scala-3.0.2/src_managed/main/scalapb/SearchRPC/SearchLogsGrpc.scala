package SearchRPC

object SearchLogsGrpc {
  val METHOD_SEARCH_BETWEEN: _root_.io.grpc.MethodDescriptor[SearchRPC.TimeRequest, SearchRPC.LogResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("SearchLogs", "SearchBetween"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[SearchRPC.TimeRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[SearchRPC.LogResponse])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(SearchRPC.SearchRPCProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("SearchLogs")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(SearchRPC.SearchRPCProto.javaDescriptor))
      .addMethod(METHOD_SEARCH_BETWEEN)
      .build()
  
  trait SearchLogs extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = SearchLogs
    def searchBetween(request: SearchRPC.TimeRequest): scala.concurrent.Future[SearchRPC.LogResponse]
  }
  
  object SearchLogs extends _root_.scalapb.grpc.ServiceCompanion[SearchLogs] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[SearchLogs] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = SearchRPC.SearchRPCProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = SearchRPC.SearchRPCProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: SearchLogs, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_SEARCH_BETWEEN,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[SearchRPC.TimeRequest, SearchRPC.LogResponse] {
          override def invoke(request: SearchRPC.TimeRequest, observer: _root_.io.grpc.stub.StreamObserver[SearchRPC.LogResponse]): _root_.scala.Unit =
            serviceImpl.searchBetween(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait SearchLogsBlockingClient {
    def serviceCompanion = SearchLogs
    def searchBetween(request: SearchRPC.TimeRequest): SearchRPC.LogResponse
  }
  
  class SearchLogsBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[SearchLogsBlockingStub](channel, options) with SearchLogsBlockingClient {
    override def searchBetween(request: SearchRPC.TimeRequest): SearchRPC.LogResponse = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_SEARCH_BETWEEN, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): SearchLogsBlockingStub = new SearchLogsBlockingStub(channel, options)
  }
  
  class SearchLogsStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[SearchLogsStub](channel, options) with SearchLogs {
    override def searchBetween(request: SearchRPC.TimeRequest): scala.concurrent.Future[SearchRPC.LogResponse] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_SEARCH_BETWEEN, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): SearchLogsStub = new SearchLogsStub(channel, options)
  }
  
  def bindService(serviceImpl: SearchLogs, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = SearchLogs.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): SearchLogsBlockingStub = new SearchLogsBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): SearchLogsStub = new SearchLogsStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = SearchRPC.SearchRPCProto.javaDescriptor.getServices().get(0)
  
}