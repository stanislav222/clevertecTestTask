package by.clevertec.dao.impl;

import by.clevertec.dao.CardReadingDataFactory;
import by.clevertec.dao.DiscountCardDaoI;

public class CardTxtReadingDF implements CardReadingDataFactory {
    @Override
    public DiscountCardDaoI createReader() {
        return new DiscountCardFromTxtDao();
    }
}
