package DB;

/**
 * Created by o_0 on 2016-09-22.
 */
public final class DBManager {
    private static volatile DBManager db = null;
    private DBManager(){

    }

    public ProductDAO getProductDAO() {
        return new ProductDAO(null);
    }
    // dubble check looking
    public static DBManager getInstance() {
        DBManager current = db;
        if (current == null) {

            synchronized (DBManager.class) {
                current = db;
                if (current == null) {
                    db = current = new DBManager();
                }
            }
        }
        return current;
    }

}
