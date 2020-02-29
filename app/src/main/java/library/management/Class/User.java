package library.management.Class;

public class User {
    String UserId, Name, Email, UserType;

    public User(String userId,String name, String email, String userType) {
        UserId = userId;
        Email = email;
        UserType = userType;
        Name = name;
    }

    public User(){

    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserType() {
        return UserType;
    }


}
