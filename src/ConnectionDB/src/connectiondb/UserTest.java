



import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class UserTest {
	
	//making a user with known values for testing
	String firstName = "Yelena";
	String lastName = "Abc";
	String email = "email@email.com";
    String password = "password123";

    // Stubs for Bank and Bank Branches
    int branchID = 123;
    BankBranch branch = new BankBranch(1, "Dejardins_Sesame", "123 Sesame Street", branchID, "5141234567" );
    Bank bank = new Bank("Dejardins", 1);

	
	User testUser = new User(firstName, lastName, email, branch, bank, password);

	@Test
	void testGetFirstName() {
		//using the test user
		Assert.assertTrue(testUser.getFirstName() == firstName);
	}

	@Test
	void testGetLastName() {
		//using the test user
		Assert.assertTrue(testUser.getLastName() == lastName);
	}

	@Test
	void testGetEmail() {
		//using the test user
		Assert.assertTrue(testUser.getEmail() == email);
	}

	@Test
	void testGetBranch() {
		//using the test user
		Assert.assertTrue(testUser.getBranch().getBranchId() == branchID);
	}

	@Test
	void testSetFirstName() {
		String newName = "Andrew";
		//setting the new name
		testUser.setFirstName(newName);
		//it should now be changed
		Assert.assertTrue(testUser.getFirstName() == newName);
	}

	@Test
	void testSetLastName() {
		String newName = "Meow";
		//setting the new name
		testUser.setLastName(newName);
		//it should now be changed
		Assert.assertTrue(testUser.getLastName() == newName);
	}

	@Test
	void testSetEmail() {
		String newEmail = "newemail@email.com";
		//setting the new email
		testUser.setEmail(newEmail);
		//it should now be changed
		Assert.assertTrue(testUser.getEmail() == newEmail);
	}

	@Test
	void testSetBranch() {

		int newBranchID = 456;
        BankBranch newBranch = new BankBranch(1, "Desjardin_Ohio", "Ohio", newBranchID, "5141234567");
		//setting the new branch
		testUser.setBranch(newBranch);
		//it should now be changed
		Assert.assertTrue(testUser.getBranch() == newBranch);
	}

}
