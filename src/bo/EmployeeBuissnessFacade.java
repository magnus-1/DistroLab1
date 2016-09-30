package bo;

import DB.DatabasFacade;
import ui.ProductInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cj on 2016-09-30.
 */
public class EmployeeBuissnessFacade {
    public static AuthUser loginUser(String user, String pass, String sessionId) {
        return null;
    }

    public static Collection<ProductInfo> getProducts(Collection<Integer> productIDs) {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder(), productIDs);
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(), p.getDescription(), p.getProductId(), p.getPrice(), p.getQuantity()));
        }
        System.out.println("productsInfos form getProducts: " + productInfos.toString());
        return productInfos;
    }

    static public void addProduct(ProductInfo productInfo) {
        DatabasFacade.addProduct(buildBoProduct(productInfo));
    }

    static public void deleteProduct(int productId) {
        DatabasFacade.deleteProduct(productId);
    }

    static public void updateProduct(ProductInfo productInfo) {
        DatabasFacade.updateProduct(buildBoProduct(productInfo));
    }

    static private BoProduct buildBoProduct(ProductInfo productInfo) {
        return BoProduct.getBuilder()
                .productId(productInfo.getProductId())
                .productTitle(productInfo.getProductTitle())
                .description(productInfo.getDescription())
                .price(productInfo.getPrice())
                .quantity(productInfo.getQuantity())
                .build();
    }

}
