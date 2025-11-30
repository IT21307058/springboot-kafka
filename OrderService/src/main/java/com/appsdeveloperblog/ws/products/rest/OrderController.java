package com.appsdeveloperblog.ws.products.rest;

import com.appsdeveloperblog.ws.products.service.cart.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/cart")
public class OrderController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    CartService cartService;

    public OrderController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Object> addItemToCart(
            @PathVariable String cartId,
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam int quantity
    ) {
        try {
            cartId = cartService.addCart(cartId, userId, productId, quantity);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(e.getMessage(), "/cart", new Date()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cartId);
    }
}
