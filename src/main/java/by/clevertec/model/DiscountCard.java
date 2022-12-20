package by.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCard {
    private int id;
    private String ownersEmail;
    private BigDecimal discount;

    public DiscountCard(String id, String ownersEmail, String discount) {
        this.id = Integer.parseInt(id);
        this.ownersEmail = ownersEmail;
        this.discount = new BigDecimal(discount);
    }
}
