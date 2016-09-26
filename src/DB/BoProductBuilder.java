package DB;

import bo.BoProduct;

/**
 * Created by o_0 on 2016-09-26.
 */
public interface BoProductBuilder {
    BoProductBuilder productTitle(String title);
    BoProductBuilder description(String text);
    BoProductBuilder productId(int id);
    BoProductBuilder price(double price);
    BoProductBuilder clear();
    BoProduct build();

}
