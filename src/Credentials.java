/**
 * Created by kseniamikhailovskaya on 6/25/16.
 */
public class Credentials {
    String email;
    String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
