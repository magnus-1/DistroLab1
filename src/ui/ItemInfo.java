package ui;

/**
 * Created by o_0 on 2016-09-22.
 */
public class ItemInfo {
    String name;
    String last;

    public ItemInfo(String name, String last) {
        this.name = name;
        this.last = last;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
