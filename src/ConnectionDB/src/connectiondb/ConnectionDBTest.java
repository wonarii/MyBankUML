import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



class ConnectionDBTest {
	private ConnectionDB db;
	private String testTellerEmail = "test_teller@email.com";
	private String testEmail = "test_user@gmail.com";
	private String testEmail2 = "test_user2@gmail.com";
	private String testEmail3 = "test_user3@gmail.com";
	
	Bank bank = new Bank("Desjardins", 1);


	BankBranch branch = new BankBranch(1, "Desjardins_Montreal", "2457, Saint Mishelle, Montreal, QC, HY0 5B2", 1, "5143261010");
	
	Customer customer = new Customer("Janice", "Williams", "janice123@gmail.com", branch, bank, "somePassword321", "5143267777", "2001-06-13", 7000.0);
	
	BankTeller teller = new BankTeller("Teller1" , "Khan" , "tellerKhan707@gmail.com", branch, bank , "Teller123");
	
	@BeforeEach
	void setUp() {
		try {
			db = ConnectionDB.getDatabaseInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

	@Test
	void testCreateUser_ValidCustomer() throws Exception {

	    // Act
	    int result = db.createCustomer(customer);
	     
	            
	  

	    // ✔ Should return true (user created)
	    assertEquals(0,result);
	    System.out.println("Passed entry inserted into Database");
	   }
	
	
	@Test
	void testCreateTeller_ValidTeller() throws Exception {

	    // Act
	    int result = db.createBankTeller(teller);
	     
	            
	  

	    // ✔ Should return true (teller created)
	    assertEquals(0,result);
	    System.out.println("Passed: Teller account created and entry inserted into Database");
	   }





	@Test
	void testCreateUser_InvalidRole() throws Exception {
	    boolean result = db.createUser(
	            "John", "Doe", testEmail2, "Password123!",
	            500.0,
	            "manager",    // INVALID ROLE
	            1, 1,
	            "1990-01-01",
	            "123 Test St"
	    );
	
	    assertFalse(result, "User creation should fail for invalid role");
	
	    // DB should contain NO row
	    try (Connection conn = db.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	                 "SELECT * FROM account_list WHERE user_email = ?")) {
	        stmt.setString(1, testEmail2);
	        ResultSet rs = stmt.executeQuery();
	        assertFalse(rs.next(), "No user should be inserted for invalid role");
	    }
	}





	@Test
	void testCreateUser_Underage() throws Exception {
	    boolean result = db.createUser(
	            "John", "Doe", testEmail3, "Password123!",
	            300.0,
	            "user",
	            1, 1,
	            "2010-01-01",   // UNDERAGE (14 yrs old)
	            "123 Test St"
	    );
	
	    assertFalse(result, "User creation should fail for underage user");
	
	    try (Connection conn = db.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	                 "SELECT * FROM account_list WHERE user_email = ?")) {
	        stmt.setString(1, testEmail3);
	        ResultSet rs = stmt.executeQuery();
	        assertFalse(rs.next(), "No user should be inserted for underage user");
	    }
	}
	
	
	
	//Testing the searching functionality within Database
    @Test
    void testSearchCustomerId_Found() {
        Customer[] result = db.searchCustomerId(String.valueOf(1));

        assertNotNull(result);
        assertEquals(1, result.length);

        Customer user = result[0];
        System.out.println("\n"+"\n"+"\n");

        assertEquals("Joe", user.getFirstName());
        assertEquals("White", user.getLastName());
        assertEquals("joe123@gmail.com", user.getEmail());
        assertEquals(1000.0, user.getBalance());
        System.out.println("\n"+"\n"+"\n");
        System.out.println("Customer "+user.getFirstName()+" found");
    }
	
	
	

	
}


