package shopcore.dto;

/**
 * Created by cj on 2016-09-29.
 * A DTO (data transfer object) between the bo layer and presenter layer
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
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", userID=" + userID +
                '}';
    }
}
