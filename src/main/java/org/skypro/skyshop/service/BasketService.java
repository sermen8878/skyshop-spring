package org.skypro.skyshop.service;

import org.springframework.stereotype.Service;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.BasketItem;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.UserBasket;
import org.skypro.skyshop.exception.NoSuchProductException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        storageService.getProductById(id)
                .orElseThrow(() -> new NoSuchProductException(\"Товар с ID \" + id + \" не найден\"));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> basketItems = new ArrayList<>();
        
        productBasket.getProducts().forEach((id, count) -> {
            Product product = storageService.getProductById(id)
                    .orElseThrow(() -> new NoSuchProductException(\"Товар с ID \" + id + \" не найден в корзине\"));
            basketItems.add(new BasketItem(product, count));
        });

        return new UserBasket(basketItems);
    }
}
