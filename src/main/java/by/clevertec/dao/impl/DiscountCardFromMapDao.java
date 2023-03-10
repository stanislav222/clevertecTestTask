package by.clevertec.dao.impl;

import by.clevertec.dao.DiscountCardDaoI;
import by.clevertec.model.DiscountCard;
import by.clevertec.util.TxtGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountCardFromMapDao implements DiscountCardDaoI {

    private static final TxtGenerator GENERATOR = new TxtGenerator();
    private static final List<DiscountCard> discountCards = List.of(
            DiscountCard.builder()
                    .id(1)
                    .ownersEmail("test1@gmail.com")
                    .discount(new BigDecimal("20.00"))
                    .build(),
            DiscountCard.builder()
                    .id(2)
                    .ownersEmail("test2@gmail.com")
                    .discount(new BigDecimal("20.00"))
                    .build(),
            DiscountCard.builder()
                    .id(3)
                    .ownersEmail("test3@gmail.com")
                    .discount(new BigDecimal("20.00"))
                    .build()
    );

    @Override
    public DiscountCard getDiscountCard(int cardId) {
        Map<Integer, DiscountCard> discountCardMap = discountCards.stream()
                .collect(Collectors.toMap(DiscountCard::getId, Function.identity()));
        return discountCardMap.get(cardId);
    }
}
