package by.clevertec.service;

import by.clevertec.model.dto.AdvancedResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

class ReceiptServiceTest {


    public static BigDecimal percentage(BigDecimal base, int pct) {
        BigDecimal percentageAmount = base.multiply(BigDecimal.valueOf((double) pct / 100));
        return percentageAmount;
    }

    @Test
    void getProduct() {
        DecimalFormat f = new DecimalFormat("###.#####");
        System.out.println(percentage(new BigDecimal(f.format(17.00)), 50));
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
        List<AdvancedResponse> advancedResponses = receiptService.generateListOfProductWithDiscount(map);
        System.out.println(advancedResponses);
    }

    @Test
    void getTotalAmount() {
        ReceiptService receiptService = new ReceiptService();
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 6);
        //map.put(2, 6);
        int idCard = 2;
        BigDecimal totalAmount = receiptService.getTotalAmount(map, idCard);
        System.out.println(totalAmount);
    }
}