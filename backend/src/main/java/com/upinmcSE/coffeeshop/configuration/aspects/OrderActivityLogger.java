package com.upinmcSE.coffeeshop.configuration.aspects;

import com.upinmcSE.coffeeshop.dto.response.OrderLineResponse;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

@Component
@Aspect
@Slf4j
public class OrderActivityLogger {

    @Pointcut("execution(* com.upinmcSE.coffeeshop.controller.OrderController.create(..))")
    public void createMethod(){}

    // Advice to execute after the create method returns successfully
    @AfterReturning(pointcut = "createMethod()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        ResponseEntity<?> response = (ResponseEntity<?>) result;
        OrderResponse orderResponse = (OrderResponse) response.getBody();

        // Extract necessary information
        String customerId = orderResponse.id();
        LocalDate createdDate = orderResponse.createdDate();

        // Log each order line
        for (OrderLineResponse orderLine : orderResponse.orderLines()) {
            String productId = orderLine.id();
            int amount = orderLine.amount();

            // Log the information
            log.info("Customer: {}, Product: {}, Amount: {}, Date: {}", customerId, productId, amount, createdDate);

            // Write the information to a file
            writeOrderInfoToFile(customerId, productId, amount, createdDate);
        }
    }

    private void writeOrderInfoToFile(String customerName, String productName, int amount, LocalDate createdDate) {
        try {
            String orderInfo = String.format("Customer: %s, Product: %s, Amount: %d, Date: %s%n", customerName, productName, amount, createdDate);

            // Create or append to the file
            Files.write(Paths.get("order.txt"),
                    orderInfo.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            log.info("Order information written to order_log.txt");
        } catch (IOException e) {
            log.error("Error writing order information to file", e);
        }
    }
}


