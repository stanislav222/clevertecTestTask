package by.clevertec.dao;

import by.clevertec.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductDao {

    private static final List<Product> products = List.of(
            Product.builder().id(1).productName("product 1").cost(new BigDecimal("17")).build(),
            Product.builder().id(2).productName("product 2").cost(new BigDecimal("8")).build(),
            Product.builder().id(3).productName("product 3").cost(new BigDecimal("4")).build(),
            Product.builder().id(4).productName("product 4").cost(new BigDecimal("6")).build()
    );

    public Product getProduct(int productId) {
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        return productMap.get(productId);
    }

    public List<Product> readAll() {
        return products;
    }
}
