import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AuthenticatorTest {
	
	//creating test values
	
	String firstName = "Ahmed";
	String lastName = "Khan";
	String email = "ahmed@gmail.com";
	Bank bank = new Bank("RBC", 2);
	String phone = "450-111-1234";
	String birthday = "1998-04-28";
	String password = "Mission123!";
	BankBranch branch = new BankBranch(2, "RBC_Montreal", "2946, PIX, Montreal, QC, H7T 4R5", 4, "8484028340");

	@Test
	void testGetCurrentUser() {
		//i will use sign up to create the user, then validateCredentials to log in
        Authenticator authenticator = Authenticator.getAuthenticatorInstance();
        authenticator.signUp(firstName, lastName, email, bank, phone, birthday, password, branch);

        authenticator.validateCredentials(email, password);

        //test if we log in with another user
        email = "charlie.brown@gmail.com";
        password = "Charlie123!";

        authenticator.validateCredentials(email, password);

        //now we test to see if current user is the one we logged in as
        try {
            Assert.assertTrue(Authenticator.getAuthenticatorInstance().getCurrentUser() == ConnectionDB.getDatabaseInstance().getUserMap());
        }
        catch(Exception e) {
            fail(e.getMessage());
        }
        //prints the user information
        System.out.println(authenticator.getCurrentUser());

	}

	@Test
	void testSignUp() {
		Authenticator authenticator = Authenticator.getAuthenticatorInstance();
		authenticator.signUp(firstName, lastName, email, bank, phone, birthday, password, branch);
		
		//now we must check if the new customer is in the database (check  is done manually)
		
	}

	@Test
	void testCreateBankTeller() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateCredentials() {
        try{
            Assert.assertTrue(Authenticator.getAuthenticatorInstance().validateCredentials(email, password));
        }
        catch(Exception e){
            fail("invalid credentials");
        }

	}

}
