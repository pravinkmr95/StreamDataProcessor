package com.stream.data.processor.service;

import com.stream.data.processor.dto.ApiResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final Logger logger = LoggerFactory.getLogger(DataService.class);
    public boolean validatePayload(List<Integer> numbers) {
        String message = null;
        logger.info("Received Numbers in payload: {}", numbers);
        if (numbers == null || numbers.isEmpty()) {
            message = "Empty List provided in the payload";
            logger.warn(message);
            return false;
        }
        return true;
    }

    public List<Integer> generateNumbers(List<Integer> numbers) {
        List<Integer> filteredAndModifiedList =  numbers.stream()
                .filter(number -> number % 2 != 0)
                .map(number -> number * 2)
                .sorted()
                .toList();
        String message = "Filtered and modified Numbers generated gRPC service " + filteredAndModifiedList;
        logger.info(message);
        return filteredAndModifiedList;
    }
}
