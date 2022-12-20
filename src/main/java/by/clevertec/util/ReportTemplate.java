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
    public static final String CASH_RECEIPT = "CASH RECEIPT";
    public static final String SUPERMARKET = "SUPERMARKET: 123";
    public static final String ADDRESS = "12, MILKYWAY GALAXY/ EARTH";
    public static final String TEL = "TEL: 123-456-7890";
    public static final String CASHIER = "CASHIER: №1520";
    public static final String DELIMITER = "--".repeat(25);
    public static final String TABLE_HEADERS = "QTY | DESCRIPTION | PRICE | TOTAL ";
    public static final String SUBTOTAL = "Subtotal:         ";
    public static final String DISCOUNT_PERCENT = "Discount percent: ";
    public static final String DISCOUNT_AMOUNT = "Discount amount:  ";
    public static final String INVOICE_TOTAL = "Invoice total:    ";

    public static String formatNumber(BigDecimal num) {
        DecimalFormat decimalFormat = new DecimalFormat("#######.##");
        return decimalFormat.format(num);
    }
    public String getHeaders() {
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        Instant now = Instant.now();
        LocalDate localDate = LocalDate.ofInstant(now, ZoneId.systemDefault());
        LocalTime localTime = LocalTime.ofInstant(now, ZoneId.systemDefault());
        String infoDataTime = String.format("DATE: %s, TIME: %s",
                dtfDate.format(localDate), dtfTime.format(localTime));
        return String.format(
                "" + CASH_RECEIPT+"\n" +
                "" + SUPERMARKET+"\n" +
                "" + ADDRESS+"\n" +
                "" + TEL+"\n" +
                "" + CASHIER + " | " + infoDataTime+"\n" +
                "" + DELIMITER +
                "\n");
    }

    public String getProductsPart(HashMap<Integer, Integer> products){
        List<ProductAmountDto> productAmountDtos = receiptService
                .generateListOfProductWithDiscount(products);
        StringBuilder productsMessage = new StringBuilder(String.format(
                "" + TABLE_HEADERS + "\n"
                + ""));
        for (ProductAmountDto i : productAmountDtos) {
            productsMessage.append(String.format("  " + i.getQuantity()
                    + " |   " + i.getName()
                    + " |    " + i.getPrice()
                    + " |  " + i.getTotalPrice() +"\n"
            ));
        }
        return productsMessage.toString();
    }

    public String getBasement(HashMap<Integer, Integer> products, int cardId) {
        TotalAmountDto totalAmount = receiptService.getTotalAmount(products, cardId);
        return String.format(
                ""+ DELIMITER +"\n"
                + SUBTOTAL + formatNumber(totalAmount.getSubtotal()) + "\n"
                + DISCOUNT_PERCENT + formatNumber(totalAmount.getDiscountPercent()) + "%%\n"
                + DISCOUNT_AMOUNT + formatNumber(totalAmount.getDiscountAmount())  + "\n"
                + INVOICE_TOTAL + formatNumber(totalAmount.getInvoiceTotal()) + "\n");
    }
}
