package shopcore.dto;

/**
 * Created by o_0 on 2016-09-26.
 * A DTO (data transfer object) between the bo layer and presenter layer
 */
public class ProductInfo {
    private String productTitle;
    private String description;
    private String category;
    private int productId;
    private double price;
    private int quantity;

    public ProductInfo(String productTitle, String description,String category, int productId, double price, int quantity) {
        this.productTitle = productTitle;
        this.description = description;
        this.category = category;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }
    public ProductInfo(String productTitle, String description,String category, double price, int quantity) {
        this.productTitle = productTitle;
        this.description = description;
        this.category = category;
        this.price = price;
        this.productId = 0;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getDescription() {
        return description;
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productTitle='" + productTitle + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", productId=" + productId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
