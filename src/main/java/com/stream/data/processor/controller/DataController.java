package com.stream.data.processor.controller;

import com.stream.data.processor.client.ProcessServiceClient;
import com.stream.data.processor.dto.ApiResponse;
import com.stream.data.processor.service.DataService;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/processData")
public class DataController {
    @Autowired
    private ProcessServiceClient processServiceClient;

    @Autowired
    private DataService dataService;

    @PostMapping
    public ResponseEntity<ApiResponse> processData(@RequestBody List<Integer> numbers) {
        if (!dataService.validatePayload(numbers)) {
            ApiResponse apiResponse = new ApiResponse(HttpResponseStatus.BAD_REQUEST.code(),
                    "Empty List provided in the payload");
            return ResponseEntity.badRequest().body(apiResponse);
        }
        List<Integer> filteredAndModifiedList = dataService.generateNumbers(numbers);

        processServiceClient.sendProcessedData(filteredAndModifiedList);
        return ResponseEntity.ok(new ApiResponse(HttpResponseStatus.OK.code(),
                "Filter and modified Numbers sent to gRPC service"));
    }

}
