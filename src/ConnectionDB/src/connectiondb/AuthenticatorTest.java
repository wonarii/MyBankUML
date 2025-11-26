import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthenticatorTest {
	
	//creating test values
	
	String firstName = "Ahmed";
	String lastName = "Khan";
	String email = "ahmed@gmail.com";
	Bank bank = new Bank("RBC", 123);
	String phone = "450-111-1234";
	String birthday = "1998-04-28";
	String password = "Secure@123";
	BankBranch branch = new BankBranch(123, "Dorion", "345 rue des Champignons", 456, "455-332-1342");

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
		fail("Not yet implemented");
	}

}
