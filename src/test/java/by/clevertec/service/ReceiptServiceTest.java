package by.clevertec.service;

import by.clevertec.model.dto.ProductAmountDto;
import by.clevertec.model.dto.TotalAmountDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

class ReceiptServiceTest {


    public static BigDecimal percentage(BigDecimal base, int pct) {
        BigDecimal percentageAmount = base.multiply(BigDecimal.valueOf((double) pct / 100));
        return percentageAmount;
    }

    public static BigDecimal percentage2(BigDecimal base, BigDecimal pct){
        BigDecimal ONE_HUNDRED = new BigDecimal(100);
        BigDecimal var = pct.divide(ONE_HUNDRED, 2, RoundingMode.UP);
        return base.multiply(var);
    }

    @Test
    void getProduct() {
       DecimalFormat f = new DecimalFormat("###.#####");
       // System.out.println(percentage(new BigDecimal(f.format(17.00)), 50));
        System.out.println(percentage2(new BigDecimal(f.format(17.00)), new BigDecimal(f.format(10.0))));
    }

    @Test
    void generateListOfProduct() {
    }

    @Test
    void generateListOfProductWithDiscount() {
        ReceiptService receiptService = new ReceiptService();
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 6);
        map.put(2, 6);
        List<ProductAmountDto> advancedRespons = receiptService.generateListOfProductWithDiscount(map);
        System.out.println(advancedRespons);
    }

    @Test
    void getTotalAmount() {
        ReceiptService receiptService = new ReceiptService();
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 6);
        //map.put(2, 6);
        int idCard = 2;
        TotalAmountDto totalAmount = receiptService.getTotalAmount(map, idCard);
        System.out.println(totalAmount);
    }
}