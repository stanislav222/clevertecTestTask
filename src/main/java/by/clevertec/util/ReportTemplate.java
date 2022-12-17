package by.clevertec.util;

import by.clevertec.model.dto.ProductAmountDto;
import by.clevertec.model.dto.TotalAmountDto;
import by.clevertec.service.ReceiptService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ReportTemplate {
    public static final ReceiptService receiptService = new ReceiptService();

    public static String formatNumber(BigDecimal num) {
        DecimalFormat decimalFormat = new DecimalFormat("#######.##");
        return decimalFormat.format(num);
    }
    public static void getHeaders() {
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        Instant now = Instant.now();
        LocalDate localDate = LocalDate.ofInstant(now, ZoneId.systemDefault());
        LocalTime localTime = LocalTime.ofInstant(now, ZoneId.systemDefault());
        String infoDataTime = String.format("DATE: %s, TIME: %s",
                dtfDate.format(localDate), dtfTime.format(localTime));
        System.out.println("CASH RECEIPT");
        System.out.println("SUPERMARKET: 123");
        System.out.println("12, MILKYWAY GALAXY/ EARTH");
        System.out.println("TEL: 123-456-7890");
        System.out.println("CASHIER: №1520 | " + infoDataTime);
        System.out.println("--".repeat(25));
    }

    public static void getProductsPart(HashMap<Integer, Integer> products){
        List<ProductAmountDto> productAmountDtos = receiptService.generateListOfProductWithDiscount(products);
        System.out.println("QTY | DESCRIPTION | PRICE | TOTAL ");
        productAmountDtos.forEach(i->
                System.out.println("  " + i.getQuantity()
                + " |   " + i.getName()
                + " |    " + i.getPrice()
                + " |   " + i.getTotalPrice()
        ));
    }

    public static void getBasement(HashMap<Integer, Integer> products, int cardId) {
        TotalAmountDto totalAmount = receiptService.getTotalAmount(products, cardId);
        System.out.println("--".repeat(25));
        String messageBasement =
                "Subtotal:         " + formatNumber(totalAmount.getSubtotal()) + "\n"
                + "Discount percent: " + formatNumber(totalAmount.getDiscountPercent()) + "%\n"
                + "Discount amount:  " + formatNumber(totalAmount.getDiscountAmount())  + "\n"
                + "Invoice total:    " + formatNumber(totalAmount.getInvoiceTotal()) + "\n";
        System.out.println(messageBasement);
    }
}
