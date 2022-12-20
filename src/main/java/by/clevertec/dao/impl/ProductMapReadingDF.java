package by.clevertec.dao.impl;

import by.clevertec.dao.ProductDaoI;
import by.clevertec.dao.ProductReadingDataFactory;

public class ProductMapReadingDF implements ProductReadingDataFactory {
    @Override
    public ProductDaoI createReader() {
        return new ProductFromMapDao();
    }
}
