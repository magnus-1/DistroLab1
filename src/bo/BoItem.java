package bo;

import DB.BoItemBuilder;

/**
 * Created by o_0 on 2016-09-23.
 */
public class BoItem {
    String name;
    String last;
    double price;
    int itemCount;

    private BoItem(BoBuilder builder) {
        this.name = builder.name;
        this.last = builder.last;
        this.price = builder.price;
        this.itemCount = builder.itemCount;
    }

    public BoItem(String name, String last, double price, int itemCount) {
        this.name = name;
        this.last = last;
        this.price = price;
        this.itemCount = itemCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
    public static BoItemBuilder getBuilder() { return new BoBuilder();}
    private static class BoBuilder implements BoItemBuilder {
        String name = "";
        String last = "";
        double price = 0.0;
        int itemCount = 0;

        @Override
        public BoBuilder firstName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public BoItemBuilder lastName(String last) {
            this.last = last;
            return this;
        }

        @Override
        public BoItemBuilder price(double price) {
            this.price = price;
            return this;
        }

        @Override
        public BoItemBuilder itemCount(int itemCount) {
            this.itemCount = itemCount;
            return this;
        }

        @Override
        public BoItem build() {
            return new BoItem(this);
        }

        @Override
        public BoItemBuilder clear() {
            return new BoBuilder();
        }
    }
}
