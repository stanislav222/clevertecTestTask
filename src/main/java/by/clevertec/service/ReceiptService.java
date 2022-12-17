package by.clevertec.service;

import by.clevertec.dao.DiscountCardDao;
import by.clevertec.dao.ProductDao;
import by.clevertec.model.DiscountCard;
import by.clevertec.model.Product;
import by.clevertec.model.dto.ProductAmountDto;
import by.clevertec.model.dto.TotalAmountDto;
import by.clevertec.util.Calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceiptService {
    public static final BigDecimal percentPerItem = new BigDecimal("10.00");
    private static final ProductDao productDao = new ProductDao();
    private static final DiscountCardDao discountCardDao = new DiscountCardDao();

    public Product getProduct(int productId) {
        return productDao.getProduct(productId);
    }

    public List<Product> generateListOfProduct() {
        return productDao.readAll();
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
            //return totalAmount.subtract(percentage);
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
