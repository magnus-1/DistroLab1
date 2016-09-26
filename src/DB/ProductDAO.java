package DB;

import bo.BoProduct;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-26.
 */
public class ProductDAO {
    private DataSource dataSource;

    public ProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<BoProduct> getProducts(BoProductBuilder builder) {
        ArrayList<BoProduct> boProducts = new ArrayList<>();
        boProducts.add(builder
                .productTitle("magnus")
                .description("...")
                .price(12.22)
                .productId(42)
                .build());
        boProducts.add(builder.clear()
                .productTitle("Carl-johan")
                .description("...2")
                .price(124.22)
                .productId(422)
                .build());
        return boProducts;
    }


}
