package by.clevertec.dao.impl;

import by.clevertec.dao.ProductDaoI;
import by.clevertec.model.Product;
import by.clevertec.util.TxtGenerator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductFromFileDao implements ProductDaoI {
    private static final TxtGenerator GENERATOR = new TxtGenerator();

    @Override
    public Product getProduct(int productId) {
        List<Product> products = GENERATOR.readProductsFromTxtFile();
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        return productMap.get(productId);
    }
}
