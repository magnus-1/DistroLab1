package ui;

import shopcore.bo.BoOrder;

/**
 * Created by cj on 2016-10-01.
 */
public class OrderInfo {
    private boolean packed;
    private int orderID;
    private int userID;

    public OrderInfo(int orderID, int userID, boolean packed) {
        this.packed = packed;
        this.orderID = orderID;
        this.userID = userID;
    }

    public boolean isPacked() {
        return packed;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "packed=" + packed +
                ", orderID=" + orderID +
                ", userID=" + userID +
                '}';
    }
}
