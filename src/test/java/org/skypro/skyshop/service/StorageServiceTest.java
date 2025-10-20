package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StorageServiceTest {

    private final StorageService storageService = new StorageService();

    @Test
    void getAllProducts_ShouldReturnNonEmptyCollection() {
        // Act
        Collection<SimpleProduct> products = (Collection<SimpleProduct>) storageService.getAllProducts();

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void getAllArticles_ShouldReturnNonEmptyCollection() {
        // Act
        Collection<Article> articles = (Collection<Article>) storageService.getAllArticles();

        // Assert
        assertNotNull(articles);
        assertFalse(articles.isEmpty());
    }

    @Test
    void getAllSearchableItems_ShouldReturnCombinedCollection() {
        // Act
        Collection<Searchable> searchableItems = storageService.getAllSearchableItems();

        // Assert
        assertNotNull(searchableItems);
        assertFalse(searchableItems.isEmpty());
        assertTrue(searchableItems.size() >= storageService.getAllProducts().size() + storageService.getAllArticles().size());
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        Collection<SimpleProduct> products = (Collection<SimpleProduct>) storageService.getAllProducts();
        UUID existingProductId = products.iterator().next().getId();

        // Act
        Optional<SimpleProduct> product = (Optional<SimpleProduct>) storageService.getProductById(existingProductId);

        // Assert
        assertTrue(product.isPresent());
        assertEquals(existingProductId, product.get().getId());
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ShouldReturnEmptyOptional() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act
        Optional<SimpleProduct> product = (Optional<SimpleProduct>) storageService.getProductById(nonExistentId);

        // Assert
        assertFalse(product.isPresent());
    }

    @Test
    void getProductByIdOrThrow_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        Collection<SimpleProduct> products = (Collection<SimpleProduct>) storageService.getAllProducts();
        UUID existingProductId = products.iterator().next().getId();

        // Act
        SimpleProduct product = (SimpleProduct) storageService.getProductByIdOrThrow(existingProductId);

        // Assert
        assertNotNull(product);
        assertEquals(existingProductId, product.getId());
    }

    @Test
    void getProductByIdOrThrow_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act & Assert
        NoSuchProductException exception = assertThrows(
                NoSuchProductException.class,
                () -> storageService.getProductByIdOrThrow(nonExistentId)
        );

        assertTrue(exception.getMessage().contains(nonExistentId.toString()));
    }
}
