package shopcore.DB;

/**
 * Builder interface for BoProduct. This is used so that the DB layer
 * doesn't need to know how to build the BoProduct.
 */
public interface BoProductBuilder<T> {
    BoProductBuilder<T> productTitle(String title);
    BoProductBuilder<T> description(String text);
    BoProductBuilder<T> category(String category);
    BoProductBuilder<T> productId(int id);
    BoProductBuilder<T> price(double price);
    BoProductBuilder<T> quantity(int quantity);
    BoProductBuilder<T> clear();
    T build();
}
