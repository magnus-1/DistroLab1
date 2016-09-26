package ui;

/**
 * Created by o_0 on 2016-09-26.
 */
public class ProductInfo {
    private String productTitle;
    private String description;
    private int productId;
    private double price;

    public ProductInfo(String productTitle, String description, int productId, double price) {
        this.productTitle = productTitle;
        this.description = description;
        this.productId = productId;
        this.price = price;
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

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productTitle='" + productTitle + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productId=" + productId +
                '}';
    }
}
