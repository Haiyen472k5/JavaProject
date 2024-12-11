package project.libraryassistant;

public class Login {
   private String account;
   private String password;
   private String username;
   private String image;

    public Login(String account, String password, String username, String image) {
        this.account = account;
        this.password = password;
        this.username = username;
        this.image = image;
    }
    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

}
