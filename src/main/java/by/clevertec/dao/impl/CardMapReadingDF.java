package by.clevertec.dao.impl;

import by.clevertec.dao.CardReadingDataFactory;
import by.clevertec.dao.DiscountCardDaoI;

public class CardMapReadingDF implements CardReadingDataFactory {
    @Override
    public DiscountCardDaoI createReader() {
        return new DiscountCardFromMapDao();
    }
}
