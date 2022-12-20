package by.clevertec.service;

import by.clevertec.dao.*;
import by.clevertec.dao.impl.CardTxtReadingDF;
import by.clevertec.dao.impl.DiscountCardFromMapDao;
import by.clevertec.dao.impl.ProductTxtReadingDF;
import by.clevertec.exception.SimpleException;
import by.clevertec.model.DiscountCard;
import by.clevertec.model.Product;
import by.clevertec.model.dto.ProductAmountDto;
import by.clevertec.model.dto.TotalAmountDto;
import by.clevertec.util.Calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ReceiptService {
    public static final String DISCOUNT_PERCENTAGE = "10.00";
    public static final BigDecimal percentPerItem = new BigDecimal(DISCOUNT_PERCENTAGE);
    private static final ProductReadingDataFactory readingDataFactory = new ProductTxtReadingDF();
    private static final ProductDaoI productDao = readingDataFactory.createReader();
    private static final CardReadingDataFactory cardReadingDataFactory = new CardTxtReadingDF();
    private static final DiscountCardDaoI discountCardDao = cardReadingDataFactory.createReader();

    public Product getProduct(int productId) {
        return Optional.ofNullable(productDao.getProduct(productId))
                .orElseThrow(() ->
                        new SimpleException(String.format("Product not found by id %s", productId)));
    }

    public List<ProductAmountDto> generateListOfProductWithDiscount(HashMap<Integer, Integer> infoByProduct) {
        List<ProductAmountDto> productList = new ArrayList<>();
        infoByProduct.forEach((key, value) -> {
            Product product = getProduct(key);
            BigDecimal totalPricePerItem;
            if (value > 5) {
                totalPricePerItem = product.getCost().multiply(BigDecimal.valueOf(value));
                BigDecimal discountOnPerItem = Calculation.percentage(totalPricePerItem, percentPerItem);
                productList.add(convertToAdvancedResponse(product, value,
                        totalPricePerItem.subtract(discountOnPerItem)));
            } else {
                totalPricePerItem = product.getCost().multiply(BigDecimal.valueOf(value));
                productList.add(convertToAdvancedResponse(product, value, totalPricePerItem));

            }
        });
        return productList;
    }

    public TotalAmountDto getTotalAmount(HashMap<Integer, Integer> infoByProducts, int idDiscountCard) {
        BigDecimal totalAmount = new BigDecimal("0.0");
        List<ProductAmountDto> advancedRespons = generateListOfProductWithDiscount(infoByProducts);
        DiscountCard discountCard = discountCardDao.getDiscountCard(idDiscountCard);
        for (ProductAmountDto productAmountDto : advancedRespons) {
            totalAmount = totalAmount.add(productAmountDto.getTotalPrice());
        }
        if (discountCard != null) {
            BigDecimal percentage = Calculation.percentage(totalAmount, discountCard.getDiscount());
            return TotalAmountDto.builder()
                    .subtotal(totalAmount)
                    .discountPercent(discountCard.getDiscount())
                    .discountAmount(percentage)
                    .invoiceTotal(totalAmount.subtract(percentage))
                    .build();
        }
        return TotalAmountDto.builder()
                .subtotal(totalAmount)
                .discountPercent(BigDecimal.ZERO)
                .discountAmount(BigDecimal.ZERO)
                .invoiceTotal(totalAmount)
                .build();
    }

    public ProductAmountDto convertToAdvancedResponse(Product product,
                                                      int quantity,
                                                      BigDecimal totalPricePerItem) {
        return ProductAmountDto.builder()
                .name(product.getProductName())
                .price(product.getCost())
                .quantity(quantity)
                .totalPrice(totalPricePerItem)
                .build();
    }
}
