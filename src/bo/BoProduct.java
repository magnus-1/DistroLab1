package bo;

import DB.BoProductBuilder;
import DB.ProductInterface;

/**
 * Created by o_0 on 2016-09-26.
 */
public class BoProduct implements ProductInterface {
    private String productTitle;
    private String description;
    private int productId;
    private double price;

    private BoProduct(BoBuilder builder) {
        this.productTitle = builder.productTitle;
        this.description = builder.description;
        this.productId = builder.productId;
        this.price = builder.price;
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

    public static BoProductBuilder<BoProduct> getBuilder() {
        return new BoBuilder();
    }

    private static class BoBuilder implements BoProductBuilder<BoProduct> {
        private String productTitle ="";
        private String description = "";
        private int productId = 0;
        private double price= 0.0;

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
                ", productId=" + productId +
                ", price=" + price +
                '}';
    }
}
