package ui;

/**
 * Created by cj on 2016-09-29.
 */
public class UserInfo {
    private String email;
    private String password;
    private int userType;
    private int userID;



    public UserInfo(String email,String password,int userType, int userID){
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.userID = userID;
    }
}
