package com.appsdeveloperblog.ws.products.service.cart;

import com.appsdeveloperblog.ws.products.rest.CreateOrderRestModel;

public interface CartService {
    public String addCart(String userId, String cartId, String productId, int quantity) throws Exception;
}
