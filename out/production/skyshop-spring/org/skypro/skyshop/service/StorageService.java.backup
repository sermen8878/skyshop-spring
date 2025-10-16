package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.*;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> availableProducts;
    private final Map<UUID, Article> availableArticles;

    public StorageService() {
        this.availableProducts = new HashMap<>();
        this.availableArticles = new HashMap<>();
        fillWithTestData();
    }

    private void fillWithTestData() {
        // Добавляем тестовые продукты
        UUID product1Id = UUID.randomUUID();
        availableProducts.put(product1Id, new SimpleProduct(product1Id, "Laptop", 1000));
        
        UUID product2Id = UUID.randomUUID();
        availableProducts.put(product2Id, new DiscountedProduct(product2Id, "Smartphone", 500, 20));
        
        UUID product3Id = UUID.randomUUID();
        availableProducts.put(product3Id, new FixPriceProduct(product3Id, "USB Cable"));

        // Добавляем тестовые статьи
        UUID article1Id = UUID.randomUUID();
        availableArticles.put(article1Id, new Article(article1Id, 
            "How to choose a laptop", 
            "This article will help you choose the best laptop for your needs."));
        
        UUID article2Id = UUID.randomUUID();
        availableArticles.put(article2Id, new Article(article2Id,
            "Smartphone maintenance tips",
            "Learn how to properly maintain your smartphone for longer lifespan."));
    }

    public Collection<Product> getAllProducts() {
        return availableProducts.values();
    }

    public Collection<Article> getAllArticles() {
        return availableArticles.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(availableProducts.values());
        searchables.addAll(availableArticles.values());
        return searchables;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(availableProducts.get(id));
    }

    public Optional<Article> getArticleById(UUID id) {
        return Optional.ofNullable(availableArticles.get(id));
    }
}
