package by.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCard {
    private int id;
    private String ownersEmail;
    private int discount;
}
