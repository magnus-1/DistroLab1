package DB;

import bo.BoItem;

/**
 * Created by o_0 on 2016-09-23.
 */
public interface BoItemBuilder {
    BoItemBuilder firstName(String name);
    BoItemBuilder lastName(String last);
    BoItemBuilder price(double price);
    BoItemBuilder itemCount(int itemCount);
    BoItem build();
    BoItemBuilder clear();
}
