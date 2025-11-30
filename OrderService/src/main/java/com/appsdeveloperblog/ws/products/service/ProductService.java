package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.rest.CreateProductRestModel;

public interface ProductService {

    public String addProduct(CreateProductRestModel product) throws Exception;
}
