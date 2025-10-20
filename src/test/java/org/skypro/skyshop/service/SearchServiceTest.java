package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private SimpleProduct appleProduct;
    private SimpleProduct bananaProduct;
    private Article fruitArticle;

    @BeforeEach
    void setUp() {
        appleProduct = new SimpleProduct(UUID.randomUUID(), \"Яблоки\", 100);
        bananaProduct = new SimpleProduct(UUID.randomUUID(), \"Бананы\", 80);
        fruitArticle = new Article(UUID.randomUUID(), 
            \"Как выбрать свежие фрукты\", 
            \"Свежие фрукты должны иметь яркий цвет...\");
    }

    @Test
    void search_WhenNoObjectsInStorage_ShouldReturnEmptyList() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Collections.emptyList());

        // Act
        Collection<SearchResult> results = searchService.search(\"яблоки\");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchableItems();
    }

    @Test
    void search_WhenObjectsExistButNoMatches_ShouldReturnEmptyList() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Arrays.asList(appleProduct, bananaProduct));

        // Act
        Collection<SearchResult> results = searchService.search(\"апельсины\");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchableItems();
    }

    @Test
    void search_WhenMatchingProductExists_ShouldReturnResults() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Arrays.asList(appleProduct, bananaProduct));

        // Act
        Collection<SearchResult> results = searchService.search(\"яблоки\");

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        
        SearchResult result = results.iterator().next();
        assertEquals(appleProduct.getId().toString(), result.getId());
        assertEquals(\"Яблоки\", result.getName());
        assertEquals(\"PRODUCT\", result.getContentType());
        
        verify(storageService, times(1)).getAllSearchableItems();
    }

    @Test
    void search_WhenMatchingArticleExists_ShouldReturnResults() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Arrays.asList(appleProduct, fruitArticle));

        // Act
        Collection<SearchResult> results = searchService.search(\"фрукты\");

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        
        SearchResult result = results.iterator().next();
        assertEquals(fruitArticle.getId().toString(), result.getId());
        assertEquals(\"Как выбрать свежие фрукты\", result.getName());
        assertEquals(\"ARTICLE\", result.getContentType());
        
        verify(storageService, times(1)).getAllSearchableItems();
    }

    @Test
    void search_WhenMultipleMatchesExist_ShouldReturnAllResults() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Arrays.asList(appleProduct, bananaProduct, fruitArticle));

        // Act
        Collection<SearchResult> results = searchService.search(\"продукт\");

        // Assert
        assertNotNull(results);
        assertEquals(3, results.size());
        verify(storageService, times(1)).getAllSearchableItems();
    }

    @Test
    void search_WhenPatternIsCaseInsensitive_ShouldReturnMatches() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Arrays.asList(appleProduct));

        // Act
        Collection<SearchResult> results = searchService.search(\"ЯБЛОКИ\");

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        verify(storageService, times(1)).getAllSearchableItems();
    }

    @Test
    void search_WhenPatternIsEmpty_ShouldReturnAllItems() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Arrays.asList(appleProduct, bananaProduct));

        // Act
        Collection<SearchResult> results = searchService.search(\"\");

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        verify(storageService, times(1)).getAllSearchableItems();
    }

    @Test
    void search_WhenPatternIsNull_ShouldReturnEmptyList() {
        // Arrange
        when(storageService.getAllSearchableItems()).thenReturn(Arrays.asList(appleProduct, bananaProduct));

        // Act
        Collection<SearchResult> results = searchService.search(null);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchableItems();
    }
}
