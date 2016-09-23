package ui;

import java.util.Collection;

/**
 * Created by o_0 on 2016-09-23.
 */
public class ShoppingCart {
    private Collection<ItemInfo> items;

    public ShoppingCart(Collection<ItemInfo> items) {
        this.items = items;
    }

    public Collection<ItemInfo> getItems() {
        return items;
    }

    public void setItems(Collection<ItemInfo> items) {
        this.items = items;
    }
}
