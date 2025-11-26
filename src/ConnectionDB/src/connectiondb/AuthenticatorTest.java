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
	void testGetAuthenticatorInstance() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentUser() {
		fail("Not yet implemented");
	}

	@Test
	void testSetCurrentUser() {
		fail("Not yet implemented");
	}

	@Test
	void testSignUp() {
		Authenticator authenticator = Authenticator.getAuthenticatorInstance();
		authenticator.signUp(firstName, lastName, email, bank, phone, birthday, password, branch);
		
		//now we must check if the new customer is in the database
		
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
