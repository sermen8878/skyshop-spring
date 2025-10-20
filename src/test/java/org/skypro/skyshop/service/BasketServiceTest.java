package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    private SimpleProduct testProduct;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        testProduct = new SimpleProduct(productId, \"Тестовый продукт\", 100);
    }

    @Test
    void addProduct_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(storageService.getProductByIdOrThrow(productId))
                .thenThrow(new NoSuchProductException(\"Товар не найден\"));

        // Act & Assert
        NoSuchProductException exception = assertThrows(
                NoSuchProductException.class,
                () -> basketService.addProduct(productId)
        );

        assertEquals(\"Не удалось добавить товар в корзину: Товар не найден\", exception.getMessage());
        verify(storageService, times(1)).getProductByIdOrThrow(productId);
        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void addProduct_WhenProductExists_ShouldCallBasketAddProduct() {
        // Arrange
        when(storageService.getProductByIdOrThrow(productId)).thenReturn(testProduct);

        // Act
        basketService.addProduct(productId);

        // Assert
        verify(storageService, times(1)).getProductByIdOrThrow(productId);
        verify(productBasket, times(1)).addProduct(productId);
    }

    @Test
    void getUserBasket_WhenBasketIsEmpty_ShouldReturnEmptyBasket() {
        // Arrange
        when(productBasket.getAllProducts()).thenReturn(Collections.emptyMap());

        // Act
        UserBasket userBasket = basketService.getUserBasket();

        // Assert
        assertNotNull(userBasket);
        assertTrue(userBasket.getItems().isEmpty());
        assertEquals(0, userBasket.getTotal());
        verify(productBasket, times(1)).getAllProducts();
        verify(storageService, never()).getProductByIdOrThrow(any());
    }

    @Test
    void getUserBasket_WhenBasketHasProducts_ShouldReturnCorrectBasket() {
        // Arrange
        Map<UUID, Integer> basketProducts = new HashMap<>();
        basketProducts.put(productId, 2);
        
        when(productBasket.getAllProducts()).thenReturn(basketProducts);
        when(storageService.getProductByIdOrThrow(productId)).thenReturn(testProduct);

        // Act
        UserBasket userBasket = basketService.getUserBasket();

        // Assert
        assertNotNull(userBasket);
        assertEquals(1, userBasket.getItems().size());
        assertEquals(200, userBasket.getTotal()); // 2 items * 100 each
        
        BasketItem basketItem = userBasket.getItems().get(0);
        assertEquals(testProduct, basketItem.getProduct());
        assertEquals(2, basketItem.getQuantity());
        assertEquals(200, basketItem.getTotalPrice());
        
        verify(productBasket, times(1)).getAllProducts();
        verify(storageService, times(1)).getProductByIdOrThrow(productId);
    }

    @Test
    void getUserBasket_WhenBasketHasMultipleProducts_ShouldReturnCorrectBasket() {
        // Arrange
        UUID productId2 = UUID.randomUUID();
        SimpleProduct testProduct2 = new SimpleProduct(productId2, \"Другой продукт\", 50);
        
        Map<UUID, Integer> basketProducts = new HashMap<>();
        basketProducts.put(productId, 3); // 3 * 100 = 300
        basketProducts.put(productId2, 2); // 2 * 50 = 100
        
        when(productBasket.getAllProducts()).thenReturn(basketProducts);
        when(storageService.getProductByIdOrThrow(productId)).thenReturn(testProduct);
        when(storageService.getProductByIdOrThrow(productId2)).thenReturn(testProduct2);

        // Act
        UserBasket userBasket = basketService.getUserBasket();

        // Assert
        assertNotNull(userBasket);
        assertEquals(2, userBasket.getItems().size());
        assertEquals(400, userBasket.getTotal()); // 300 + 100
        
        verify(productBasket, times(1)).getAllProducts();
        verify(storageService, times(1)).getProductByIdOrThrow(productId);
        verify(storageService, times(1)).getProductByIdOrThrow(productId2);
    }

    @Test
    void getUserBasket_WhenProductInBasketDoesNotExist_ShouldThrowException() {
        // Arrange
        Map<UUID, Integer> basketProducts = new HashMap<>();
        basketProducts.put(productId, 1);
        
        when(productBasket.getAllProducts()).thenReturn(basketProducts);
        when(storageService.getProductByIdOrThrow(productId))
                .thenThrow(new NoSuchProductException(\"Товар не найден\"));

        // Act & Assert
        NoSuchProductException exception = assertThrows(
                NoSuchProductException.class,
                () -> basketService.getUserBasket()
        );

        assertEquals(\"Товар в корзине не найден: \" + productId, exception.getMessage());
        verify(productBasket, times(1)).getAllProducts();
        verify(storageService, times(1)).getProductByIdOrThrow(productId);
    }

    @Test
    void getUserBasket_WhenBasketHasProductWithZeroQuantity_ShouldHandleCorrectly() {
        // Arrange
        Map<UUID, Integer> basketProducts = new HashMap<>();
        basketProducts.put(productId, 0);
        
        when(productBasket.getAllProducts()).thenReturn(basketProducts);
        when(storageService.getProductByIdOrThrow(productId)).thenReturn(testProduct);

        // Act
        UserBasket userBasket = basketService.getUserBasket();

        // Assert
        assertNotNull(userBasket);
        assertEquals(1, userBasket.getItems().size());
        assertEquals(0, userBasket.getTotal()); // 0 items * 100 = 0
        
        BasketItem basketItem = userBasket.getItems().get(0);
        assertEquals(0, basketItem.getQuantity());
        assertEquals(0, basketItem.getTotalPrice());
        
        verify(productBasket, times(1)).getAllProducts();
        verify(storageService, times(1)).getProductByIdOrThrow(productId);
    }
}
