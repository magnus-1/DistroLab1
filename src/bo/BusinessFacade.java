package bo;

import DB.DatabasFacade;
import ui.ItemInfo;
import ui.ProductInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-22.
 */
public class BusinessFacade {
    static public Collection<ItemInfo> getInventory() {
        ArrayList<ItemInfo> itemInfos = new ArrayList<>();
        Collection<BoItem> currentInventory = DatabasFacade.getCurrentInventory(BoItem.getBuilder());
        for (BoItem boitem : currentInventory) {
            itemInfos.add(new ItemInfo(boitem.getName(),boitem.getLast()));
        }
        BoProduct.getBuilder();
        return itemInfos;
    }

    public static Collection<ProductInfo> productsForSale() {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder());
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(),p.getDescription(),p.getProductId(),p.getPrice()));
        }
        return productInfos;
    }

    static public void addToShoppingCart(ItemInfo info) {
    }
    static public Collection<ItemInfo> getShoppingCart() {
        return new ArrayList<ItemInfo>();
    }
}
