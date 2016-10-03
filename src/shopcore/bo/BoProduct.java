package shopcore.bo;

import shopcore.DB.BoProductBuilder;

/**
 * Created by o_0 on 2016-09-26.
 */
public class BoProduct {
    private String productTitle;
    private String description;
    private String category;
    private int productId;
    private double price;
    private int quantity;

    private BoProduct(BoBuilder builder) {
        this.productTitle = builder.productTitle;
        this.description = builder.description;
        this.category = builder.category;
        this.productId = builder.productId;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
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

    public static BoProductBuilder<BoProduct> getBuilder() {
        return new BoBuilder();
    }

    private static class BoBuilder implements BoProductBuilder<BoProduct> {
        private String productTitle = "";
        private String description = "";
        private String category = "lethal";
        private int productId = 0;
        private double price = 0.0;
        private int quantity = 0;

        public BoBuilder() {
        }

        @Override
        public BoBuilder productTitle(String title) {
            this.productTitle = title;
            return this;
        }

        @Override
        public BoBuilder description(String text) {
            this.description = text;
            return this;
        }

        @Override
        public BoProductBuilder<BoProduct> category(String category) {
            this.category = category;
            return this;
        }

        @Override
        public BoBuilder productId(int id) {
            this.productId = id;
            return this;
        }

        @Override
        public BoBuilder price(double price) {
            this.price = price;
            return this;
        }

        @Override
        public BoProductBuilder<BoProduct> quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public BoBuilder clear() {
            return new BoBuilder();
        }

        @Override
        public BoProduct build() {
            return new BoProduct(this);
        }
    }

    @Override
    public String toString() {
        return "BoProduct{" +
                "productTitle='" + productTitle + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", productId=" + productId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
