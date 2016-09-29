package ui;

/**
 * Created by o_0 on 2016-09-26.
 */
public class ProductInfo {
    private String productTitle;
    private String description;
    private int productId;
    private double price;
    private int quantity;

    public ProductInfo(String productTitle, String description, int productId, double price, int quantity) {
        this.productTitle = productTitle;
        this.description = description;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }
    public ProductInfo(String productTitle, String description, double price, int quantity) {
        this.productTitle = productTitle;
        this.description = description;
        this.price = price;
        this.productId = 0;
        this.quantity = quantity;
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
                ", price=" + price +
                ", productId=" + productId +
                '}';
    }
}
