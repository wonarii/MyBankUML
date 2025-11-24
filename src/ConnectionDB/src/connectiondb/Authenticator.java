import javax.swing.*;

public class Authenticator {

    private static Authenticator authenticatorInstance;

    private Authenticator() {

    }

    // Make the Authenticator a singleton so that only one instance exists
    public static Authenticator getAuthenticatorInstance() {
        if (authenticatorInstance == null) {
            authenticatorInstance = new Authenticator();
        }
        return authenticatorInstance;
    }


    private int changePassword(ConnectionDB db){




        return 0;
    }




}
