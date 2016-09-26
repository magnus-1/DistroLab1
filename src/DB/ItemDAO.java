package DB;

import bo.BoItem;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-26.
 */
public class ItemDAO {
    private DataSource dataSource;

    public ItemDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<BoItem> getItems(BoItemBuilder builder) {
        return null;
    }
}
