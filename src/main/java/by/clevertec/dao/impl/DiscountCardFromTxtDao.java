package by.clevertec.dao.impl;

import by.clevertec.dao.DiscountCardDaoI;
import by.clevertec.model.DiscountCard;
import by.clevertec.util.TxtGenerator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountCardFromTxtDao implements DiscountCardDaoI {
    private static final TxtGenerator GENERATOR = new TxtGenerator();

    @Override
    public DiscountCard getDiscountCard(int cardId) {
        List<DiscountCard> discountCards = GENERATOR.readCardsFromTxtFile();
        Map<Integer, DiscountCard> discountCardMap = discountCards.stream()
                .collect(Collectors.toMap(DiscountCard::getId, Function.identity()));
        return discountCardMap.get(cardId);
    }
}
