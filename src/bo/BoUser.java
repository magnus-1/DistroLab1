package bo;

import DB.BoProductBuilder;
import DB.BoUserBuilder;

/**
 * Created by cj on 2016-09-29.
 */
public class BoUser {
    public static int CUSTOMER = 1;
    public static int EMPLOYEE = 2;
    public static int ADMIN = 3;

    private String email;
    private String password;
    private int userType;
    private int userID;



    private BoUser(BoBuilder builder){
        this.email = builder.email;
        this.password = builder.password;
        this.userType = builder.userType;
        this.userID = builder.userID;

    }


    public static BoUserBuilder<BoUser> getBuilder() {
        return new BoBuilder();
    }

    private static class BoBuilder implements BoUserBuilder<BoUser> {
        private String email = "";
        private String password = "";
        private int userType = CUSTOMER;
        private int userID = 0;

        public BoBuilder() {
        }
        @Override
        public BoUserBuilder<BoUser> userEmail(String email) {
            this.email = email;
            return this;
        }

        @Override
        public BoUserBuilder<BoUser> userPassword(String password) {
            this.password = password;
            return this;
        }

        @Override
        public BoUserBuilder<BoUser> userType(int typeID) {
            this.userType = typeID;
            return this;
        }

        @Override
        public BoUserBuilder<BoUser> userID(int userID) {
            this.userID = userID;
            return this;
        }

        @Override
        public BoUserBuilder<BoUser> clear() {
            return new BoBuilder();
        }

        @Override
        public BoUser build() {
            return new BoUser(this);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getUserType() {
        return userType;
    }

    public int getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "BoUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
