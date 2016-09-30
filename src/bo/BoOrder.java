package bo;

import DB.BoOrderBuilder;
import DB.BoUserBuilder;

/**
 * Created by cj on 2016-09-30.
 */
public class BoOrder {
    private boolean packed;
    private int orderID;
    private int userID;


    private BoOrder(BoBuilder builder){
        this.packed = builder.packed;
        this.orderID = builder.orderID;
        this.userID = builder.userID;

    }


    public static BoOrderBuilder<BoOrder> getBuilder() {
        return new BoBuilder();
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
        return "BoOrder{" +
                "packed=" + packed +
                ", orderID=" + orderID +
                ", userID=" + userID +
                '}';
    }




    private static class BoBuilder implements BoOrderBuilder<BoOrder> {
        private boolean packed = false;
        private int orderID = 0;
        private int userID = 0;


        @Override
        public BoOrderBuilder<BoOrder> orderID(int orderID) {
            this.orderID=orderID;
            return this;
        }

        @Override
        public BoOrderBuilder<BoOrder> userID(int userID) {
            this.userID = userID;
            return this;
        }

        @Override
        public BoOrderBuilder<BoOrder> packed(boolean packed) {
            this.packed = packed;
            return this;
        }

        @Override
        public BoOrderBuilder<BoOrder> clear() {
            return new BoBuilder();
        }

        @Override
        public BoOrder build() {
            return new BoOrder(this);
        }
    }


}
