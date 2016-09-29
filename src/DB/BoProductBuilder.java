package DB;

import bo.BoProduct;

/**
 * Created by o_0 on 2016-09-26.
 */
public interface BoProductBuilder<T> {
    BoProductBuilder<T> productTitle(String title);
    BoProductBuilder<T> description(String text);
    BoProductBuilder<T> productId(int id);
    BoProductBuilder<T> price(double price);
    BoProductBuilder<T> clear();
    T build();
}
