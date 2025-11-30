package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import com.appsdeveloperblog.ws.products.rest.CreateProductRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String addProduct(CreateProductRestModel product) throws Exception{
        String productId = UUID.randomUUID().toString();
        // TODO: store databse

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, product.getTitle(), product.getPrice(), product.getQuantity());
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent).get();

        logger.info("Partiton: " + result.getRecordMetadata().partition());
        logger.info("Topic: " + result.getRecordMetadata().topic());
        logger.info("Offset: " + result.getRecordMetadata().offset());
        // to make asynchrnous
//        future.whenComplete((result, exception) -> {
//            if (exception != null) {
//                logger.error("Failed to send message", exception.getMessage());
//            }else{
//                logger.info("Successfully sent message", result.getRecordMetadata());
//            }
//        });

        logger.info("Retrunig product created id");
        return productId;
    }
}
