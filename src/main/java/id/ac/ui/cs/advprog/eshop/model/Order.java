package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Builder
@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;

    @Setter
    String status;

    // Constructor with 4 parameters
    public Order(String id, List<Product> products, Long orderTime, String author) {
    }

    // Constructor with 5 parameters (including status)
    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
    }
}