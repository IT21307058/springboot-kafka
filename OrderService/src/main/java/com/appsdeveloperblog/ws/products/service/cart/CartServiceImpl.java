package com.appsdeveloperblog.ws.products.service.cart;


import com.appsdeveloperblog.ws.core.CartItemAddedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
    KafkaTemplate<String, CartItemAddedEvent> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    public CartServiceImpl(KafkaTemplate<String, CartItemAddedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String addCart(String userId, String cartId, String productId, int quantity) throws Exception {
        CartItemAddedEvent event = new CartItemAddedEvent(
                cartId,
                userId,
                productId,
                quantity
        );

        SendResult<String, CartItemAddedEvent> result = kafkaTemplate.send("order-created-events-topic", cartId, event).get();

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

        logger.info("Retrunig cart created id");


        return cartId;
    }
}
