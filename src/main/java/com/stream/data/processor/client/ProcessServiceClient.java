package com.stream.data.processor.client;

import com.stream.data.processor.ProcessDataRequest;
import com.stream.data.processor.ProcessDataResponse;
import com.stream.data.processor.ProcessServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessServiceClient {
    private final Logger logger = LoggerFactory.getLogger(ProcessServiceClient.class);
    @GrpcClient("dataProcessor-service")
    private final ProcessServiceGrpc.ProcessServiceStub processServiceStub;

    public ProcessServiceClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        processServiceStub = ProcessServiceGrpc.newStub(channel);
    }

    public void sendProcessedData(List<Integer> numbers) {
        StreamObserver<ProcessDataRequest> requestObserver = processServiceStub.sendProcessedData(
                new StreamObserver<>() {
                    @Override
                    public void onNext(ProcessDataResponse response) {
//                        System.out.println("....Coming here...");
                        logger.info("Server responded: {}", response.getMessage());
                    }

                    @Override
                    public void onError(Throwable t) {
//                        System.out.println(".....Here....");
                        logger.error("Error found: {}", t.getMessage() );
                    }

                    @Override
                    public void onCompleted() {
                        logger.info("Data streaming done");
                    }
                }
        );

        //Streaming numbers one by one
        numbers.forEach(number -> requestObserver.onNext(
                ProcessDataRequest.newBuilder().setNumber(number).build()
        ));
//        ProcessRequest processRequest = ProcessRequest.newBuilder().build();
//        requestObserver.onNext(processRequest);
        requestObserver.onCompleted();
    }
}
