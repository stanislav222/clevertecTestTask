package by.clevertec.dao;

import by.clevertec.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductDao {

    private static final List<Product> products = List.of(
            Product.builder().id(1L).productName("product 1").cost(new BigDecimal("1.00")).build(),
            Product.builder().id(2L).productName("product 2").cost(new BigDecimal("2.00")).build(),
            Product.builder().id(3L).productName("product 3").cost(new BigDecimal("3.00")).build(),
            Product.builder().id(4L).productName("product 4").cost(new BigDecimal("4.00")).build(),
            Product.builder().id(5L).productName("product 5").cost(new BigDecimal("5.00")).build(),
            Product.builder().id(6L).productName("product 6").cost(new BigDecimal("6.00")).build(),
            Product.builder().id(7L).productName("product 7").cost(new BigDecimal("7.00")).build()
    );


    public Product getProduct(long id) {
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        return productMap.get(id);
    }

    public List<Product> readAll() {
        return products;
    }
}
