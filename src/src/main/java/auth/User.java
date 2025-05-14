package auth;

public class User {
    private String username;
    private String password;
    private UserType userType;

    public enum UserType {
        SELLER, DELIVERY, PROVIDER
    }

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}