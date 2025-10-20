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
        UUID appleId = UUID.randomUUID();
        UUID bananaId = UUID.randomUUID();
        UUID orangeId = UUID.randomUUID();
        UUID breadId = UUID.randomUUID();
        UUID milkId = UUID.randomUUID();

        availableProducts.put(appleId, new SimpleProduct(appleId, \"Яблоки\", 100));
        availableProducts.put(bananaId, new SimpleProduct(bananaId, \"Бананы\", 80));
        availableProducts.put(orangeId, new DiscountedProduct(orangeId, \"Апельсины\", 120, 20));
        availableProducts.put(breadId, new FixPriceProduct(breadId, \"Хлеб\"));
        availableProducts.put(milkId, new DiscountedProduct(milkId, \"Молоко\", 90, 10));

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
