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
        initializeTestData();
    }

    private void initializeTestData() {
        // Добавляем тестовые продукты
        availableProducts.put(UUID.randomUUID(), new SimpleProduct(UUID.randomUUID(), \"Яблоки\", 100));
        availableProducts.put(UUID.randomUUID(), new SimpleProduct(UUID.randomUUID(), \"Бананы\", 80));
        availableProducts.put(UUID.randomUUID(), new DiscountedProduct(UUID.randomUUID(), \"Апельсины\", 120, 20));
        availableProducts.put(UUID.randomUUID(), new FixPriceProduct(UUID.randomUUID(), \"Хлеб\"));
        availableProducts.put(UUID.randomUUID(), new DiscountedProduct(UUID.randomUUID(), \"Молоко\", 90, 10));

        // Добавляем тестовые статьи
        availableArticles.put(UUID.randomUUID(), new Article(UUID.randomUUID(), 
            \"Как выбрать свежие фрукты\", 
            \"Свежие фрукты должны иметь яркий цвет и приятный аромат...\"));
        availableArticles.put(UUID.randomUUID(), new Article(UUID.randomUUID(),
            \"Польза органических продуктов\",
            \"Органические продукты содержат больше витаминов и минералов...\"));
    }

    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(availableProducts.values());
    }

    public Collection<Article> getAllArticles() {
        return Collections.unmodifiableCollection(availableArticles.values());
    }

    public Collection<Searchable> getAllSearchableItems() {
        List<Searchable> searchableItems = new ArrayList<>();
        searchableItems.addAll(availableProducts.values());
        searchableItems.addAll(availableArticles.values());
        return Collections.unmodifiableCollection(searchableItems);
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(availableProducts.get(id));
    }
}
