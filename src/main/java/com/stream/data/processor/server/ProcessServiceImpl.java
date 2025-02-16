package com.stream.data.processor.server;

import com.stream.data.processor.ProcessDataRequest;
import com.stream.data.processor.ProcessDataResponse;
import com.stream.data.processor.ProcessServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class ProcessServiceImpl extends ProcessServiceGrpc.ProcessServiceImplBase{
    private final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
    @Override
    public StreamObserver<ProcessDataRequest> sendProcessedData(StreamObserver<ProcessDataResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(ProcessDataRequest request) {

                logger.info("number received  {}", request.getNumber());
                responseObserver.onNext(
                        ProcessDataResponse.newBuilder()
                                .setMessage("Number Processed : " + request.getNumber())
                                .build()
                );
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error found while streaming number {}", t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
