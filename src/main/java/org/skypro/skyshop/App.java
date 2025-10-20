package org.skypro.skyshop;

import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class App implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Демонстрация работы магазина ===");
        
        // Создание продуктов
        Product product1 = new SimpleProduct(UUID.randomUUID(), "Яблоки", 100);
        Product product2 = new SimpleProduct(UUID.randomUUID(), "Бананы", 80);
        Product product3 = new DiscountedProduct(UUID.randomUUID(), "Апельсины", 120, 20);
        Product product4 = new FixPriceProduct(UUID.randomUUID(), "Хлеб");
        
        // Демонстрация работы корзины
        ProductBasket basket = new ProductBasket();
        
        // Добавление продуктов
        basket.addProduct(product1);
        basket.addProduct(product2);
        basket.addProduct(product3);
        basket.addProduct(product4);
        
        // Печать содержимого
        System.out.println("\n--- Содержимое корзины ---");
        basket.printBasket();
        
        // Поиск товаров
        System.out.println("\n--- Поиск товаров ---");
        System.out.println("Есть ли яблоки: " + basket.containsProduct("Яблоки"));
        System.out.println("Есть ли груши: " + basket.containsProduct("Груши"));
        
        // Получение стоимости
        System.out.println("\n--- Стоимость корзины ---");
        System.out.println("Общая стоимость: " + basket.getTotalPrice());
        
        // Очистка и проверка пустой корзины
        System.out.println("\n--- Очистка корзины ---");
        basket.clearBasket();
        basket.printBasket();
        System.out.println("Стоимость пустой корзины: " + basket.getTotalPrice());
        
        System.out.println("\n=== Демонстрация завершена ===");
    }
}
