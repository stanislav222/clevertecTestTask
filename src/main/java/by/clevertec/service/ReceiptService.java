package by.clevertec.service;

import by.clevertec.dao.DiscountCardDao;
import by.clevertec.dao.ProductDao;
import by.clevertec.model.DiscountCard;
import by.clevertec.model.Product;
import by.clevertec.model.dto.AdvancedResponse;
import by.clevertec.util.Calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceiptService {
    public static final int percentPerItem = 10;
    //public static final int percentPerAmount = 20;
    private static final ProductDao productDao = new ProductDao();
    private static final DiscountCardDao discountCardDao = new DiscountCardDao();

    public Product getProduct(int productId) {
        return productDao.getProduct(productId);
    }

    public List<Product> generateListOfProduct() {
        return productDao.readAll();
    }

    public List<AdvancedResponse> generateListOfProductWithDiscount(HashMap<Integer, Integer> infoByProduct) {
        List<AdvancedResponse> productList = new ArrayList<>();
        infoByProduct.forEach((key, value) -> {
            Product product = getProduct(key);
            BigDecimal totalPricePerItem;
            if (value > 5) {
                totalPricePerItem = product.getCost().multiply(BigDecimal.valueOf(value));
                BigDecimal discountOnPerItem = Calculation.percentage(totalPricePerItem, percentPerItem);
                productList.add(convertToAdvancedResponse(product, value, discountOnPerItem));
            } else {
                totalPricePerItem = product.getCost().multiply(BigDecimal.valueOf(value));
                productList.add(convertToAdvancedResponse(product, value, totalPricePerItem));

            }
        });
        return productList;
    }

    public BigDecimal getTotalAmount(HashMap<Integer, Integer> infoByProducts, int idDiscountCard) {
        BigDecimal totalAmount = new BigDecimal("0.0");
        List<AdvancedResponse> advancedResponses = generateListOfProductWithDiscount(infoByProducts);
        DiscountCard discountCard = discountCardDao.getDiscountCard(idDiscountCard);
        for (AdvancedResponse advancedResponse : advancedResponses) {
            totalAmount = totalAmount.add(advancedResponse.getTotalPrice());
        }
        if (discountCard != null) {
            return Calculation.percentage(totalAmount, discountCard.getDiscount());
        }
        return totalAmount;
    }

    public AdvancedResponse convertToAdvancedResponse(Product product,
                                                      int quantity,
                                                      BigDecimal totalPricePerItem) {
        return AdvancedResponse.builder()
                .name(product.getProductName())
                .price(product.getCost())
                .quantity(quantity)
                .totalPrice(totalPricePerItem)
                .build();
    }
}
