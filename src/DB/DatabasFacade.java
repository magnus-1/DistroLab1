package DB;

import bo.BoItem;
import bo.BoProduct;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-22.
 */
public class DatabasFacade {
    public static Collection<BoItem> getCurrentInventory(BoItemBuilder builder) {
        DBManager manger = DBManager.getInstance();
        ArrayList<BoItem> boItems = new ArrayList<>();
        System.out.println("Builder:" + builder);
        boItems.add(builder
                .firstName("magnus")
                .lastName("...")
                .price(12.22)
                .itemCount(2)
                .build());
        boItems.add(builder.clear()
                .firstName("Carl-johan")
                .lastName("...")
                .price(15.42)
                .itemCount(9)
                .build());
        return boItems;
    }

    public static Collection<BoProduct> getProducts(BoProductBuilder builder) {
        DBManager db = DBManager.getInstance();
        return db.getProductDAO().getProducts(builder);
    }
}
