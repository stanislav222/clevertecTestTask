package by.clevertec.dao.impl;

import by.clevertec.dao.ProductDaoI;
import by.clevertec.dao.ProductReadingDataFactory;

public class ProductTxtReadingDF implements ProductReadingDataFactory {
    @Override
    public ProductDaoI createReader() {
        return new ProductFromFileDao();
    }
}
