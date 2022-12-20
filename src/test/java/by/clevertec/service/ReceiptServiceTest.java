package by.clevertec.service;

import by.clevertec.exception.SimpleException;
import by.clevertec.model.Product;
import by.clevertec.model.dto.ProductAmountDto;
import by.clevertec.model.dto.TotalAmountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

class ReceiptServiceTest {

    private final ReceiptService receiptService = new ReceiptService();

    public Product productForTest = Product.builder()
            .id(1)
            .productName("product1")
            .cost(new BigDecimal("22.00"))
            .build();

    @Test
    void getProduct() {
        //when(receiptService.getProduct(1)).thenReturn(productForTest);
        Assertions.assertEquals(receiptService.getProduct(1), productForTest);
    }
    @Test
    void getProductWithException() {
        SimpleException thrown = Assertions.assertThrows(SimpleException.class, () -> {
            receiptService.getProduct(8);
        }, "Product not found by id 8");
        Assertions.assertEquals("Product not found by id 8", thrown.getMessage());
    }
    @Test
    void generateListOfProductWithDiscount() {
        HashMap<Integer, Integer> testMap = new HashMap<>();
        testMap.put(2, 2);
        List<ProductAmountDto> advancedResponse = receiptService.generateListOfProductWithDiscount(testMap);
        Assertions.assertEquals(2, advancedResponse.get(0).getQuantity());
        Assertions.assertEquals("product2", advancedResponse.get(0).getName());
        Assertions.assertEquals(new BigDecimal("2.00"), advancedResponse.get(0).getPrice());
        Assertions.assertEquals(new BigDecimal("4.00"), advancedResponse.get(0).getTotalPrice());
        //System.out.println(advancedResponse);
    }

    @Test
    void getTotalAmount() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        int idCard = 1;
        TotalAmountDto totalAmount = receiptService.getTotalAmount(map, idCard);
        Assertions.assertEquals(new BigDecimal("44.00"), totalAmount.getSubtotal());
        Assertions.assertEquals(new BigDecimal("20.00"), totalAmount.getDiscountPercent());
        Assertions.assertEquals(new BigDecimal("8.8000"), totalAmount.getDiscountAmount());
        Assertions.assertEquals(new BigDecimal("35.2000"), totalAmount.getInvoiceTotal());
        //System.out.println(totalAmount);
    }

    @Test
    void convertToAdvancedResponse() {
        ProductAmountDto productAmountDto = receiptService
                .convertToAdvancedResponse(productForTest, 1, new BigDecimal("35.2000"));
        assertThat(productAmountDto.getQuantity(), comparesEqualTo(1));
        assertThat(productAmountDto.getName(), comparesEqualTo("product1"));
        assertThat(productAmountDto.getPrice(), comparesEqualTo(new BigDecimal("22.0")));
        assertThat(productAmountDto.getTotalPrice(), comparesEqualTo(new BigDecimal("35.2000")));
    }
}