package bo;

import DB.DBManager;
import DB.DatabasFacade;
import ui.ProductInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cj on 2016-09-29.
 */
public class AdminBuissnessFacade {
    //    static public Collection<ItemInfo> getInventory() {
//        ArrayList<ItemInfo> itemInfos = new ArrayList<>();
//        Collection<BoItem> currentInventory = DatabasFacade.getCurrentInventory(BoItem.getBuilder());
//        for (BoItem boitem : currentInventory) {
//            itemInfos.add(new ItemInfo(boitem.getName(),boitem.getLast()));
//        }
//        BoProduct.getBuilder();
//        return itemInfos;
//    }

    public static Collection<ProductInfo> getProducts() {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder());
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(),p.getDescription(),p.getProductId(),p.getPrice(),p.getQuantity()));
        }
        return productInfos;
    }

    public static void buyProducts(Collection<Integer> productIDs,String authToken) {

    }

    public static AuthUser loginUser(String user, String pass, String sessionId) {
        return null;
    }

    public static Collection<ProductInfo> getProducts(Collection<Integer> productIDs){
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder(),productIDs);
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(),p.getDescription(),p.getProductId(),p.getPrice(),p.getQuantity()));
        }
        System.out.println("productsInfos form getProducts: "+ productInfos.toString());
        return productInfos;
    }

    static public Double totalShoppingPrice(Collection<ProductInfo> cart) {
        double sum = 0.0;
        for (ProductInfo p: cart) {
            sum += p.getPrice();
        }
        return new Double(sum);
    }

    static public void addToShoppingCart(ProductInfo info) {
    }
    static public Collection<ProductInfo> getShoppingCart() {
        return new ArrayList<ProductInfo>();
    }

    static public void addProduct(ProductInfo productInfo){
        DBManager.getInstance().getProductDAO().insertProduct(buildBoProduct(productInfo));
    }
    static public void deleteProduct(int productId){
        DBManager.getInstance().getProductDAO().deleteProduct(productId);
    }
    static public void updateProduct(ProductInfo productInfo){
        DBManager.getInstance().getProductDAO().insertProduct(buildBoProduct(productInfo));
    }
    static public void addUser(UserInfo userInfo){

    }
    static public void deleteUser(int userID){

    }
    static public void updateUser(UserInfo userInfo){

    }


    static private BoProduct buildBoProduct(ProductInfo productInfo){
        return BoProduct.getBuilder()
                .productId(productInfo.getProductId())
                .productTitle(productInfo.getProductTitle())
                .description(productInfo.getDescription())
                .price(productInfo.getPrice())
                .quantity(productInfo.getQuantity())
                .build();
    }
}
