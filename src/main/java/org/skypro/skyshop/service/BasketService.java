package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException(\"Product not found with id: \" + id));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> basketItems = productBasket.getAllProducts().entrySet().stream()
                .map(entry -> {
                    Product product = storageService.getProductById(entry.getKey())
                            .orElseThrow(() -> new IllegalArgumentException(\"Product not found\"));
                    return new BasketItem(product, entry.getValue());
                })
                .collect(Collectors.toList());

        return new UserBasket(basketItems);
    }
}
