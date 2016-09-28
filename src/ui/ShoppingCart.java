package ui;

import java.util.Collection;

/**
 * Created by o_0 on 2016-09-23.
 */
public class ShoppingCart {
    private Collection<ProductInfo> items;

    public ShoppingCart(Collection<ProductInfo> items) {
        this.items = items;
    }

    public Collection<ProductInfo> getItems() {
        return items;
    }

    public void setItems(Collection<ProductInfo> items) {
        this.items = items;
    }
}
